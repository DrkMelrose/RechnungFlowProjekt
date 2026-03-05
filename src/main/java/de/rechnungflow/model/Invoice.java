package de.rechnungflow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rechnungflow.service.WorkLogService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice {
    private int invoiceNumber;
    private Client client;
    private InvoiceStatus status = InvoiceStatus.DRAFT;
    private List<InvoiceItem> items = new ArrayList<>();
    private static final ZoneId BUSINESS_ZONE = ZoneId.of("Europe/Berlin");
    private LocalDate issueDate = LocalDate.now(BUSINESS_ZONE);
    private LocalDate dueDate = issueDate.plusDays(14);
    private List<WorkLog> workLogs = new ArrayList<>();

    private BigDecimal paidAmount = BigDecimal.ZERO;

    @JsonCreator
    public Invoice(
            @JsonProperty("invoiceNumber") int invoiceNumber,
            @JsonProperty("client") Client client
    ) {
        this.invoiceNumber = invoiceNumber;
        this.client = client;
    }

    public Client getCustomer(){
        return client;
    }

    public void setCustomer(Client client){
        this.client = client;
    }

    public int getInvoiceNumber(){
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber){
        this.invoiceNumber = invoiceNumber;
    }

    public void setWorkLogs(List<WorkLog> workLogs){
        this.workLogs = workLogs;
    }


    public void addItem(InvoiceItem item){
        ensureNotCancelled();
        ensureNotPaid();
        ensureDraftOnly("Items can be added only in DRAFT status");
        if (item == null){
            throw new IllegalArgumentException("Item must not be null");
        }
        items.add(item);
    }

    public List<InvoiceItem> getItems(){
        return items;
    }

    public void setInvoiceItem(List<InvoiceItem> items){
        this.items = items;
    }

    public void send(){
        ensureNotCancelled();
        ensureNotPaid();
        ensureDraftOnly("Only a DRAFT invoice can be sent");
        if(items.isEmpty()){
            throw new IllegalStateException("Cannot send an invoice without items");
        }
        status = InvoiceStatus.SENT;
    }

    public void updateOverdueStatus(){
        if (status == InvoiceStatus.SENT || status == InvoiceStatus.OVERDUE){
            if (!isFullyPaid() && LocalDate.now().isAfter(dueDate)){
                status = InvoiceStatus.OVERDUE;
            }
        }
    }
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public BigDecimal getTotalAmount(){
        if (items == null || items.isEmpty()) return BigDecimal.ZERO;

        BigDecimal total = BigDecimal.ZERO;
        for (InvoiceItem it : items){
            if (it == null) continue;

            BigDecimal line = it.getTotal();
            if (line != null) total = total.add(line);
        }
        return total;
    }

    public BigDecimal getHoursInWorklogs(){
        return workLogs.stream()
                .map(WorkLog :: getHours)
                .filter(h -> h != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean hasItems(){
        return items != null && !items.isEmpty();
    }

    public boolean isZeroAndEmpty(){
        return !hasItems() && getTotalAmount().compareTo(BigDecimal.ZERO) == 0;
    }

    public void pay(BigDecimal amount){
        ensureNotCancelled();
        ensureNotPaid();
        if (status == InvoiceStatus.DRAFT){
            throw new IllegalStateException("Cannot pay a DRAFT invoice. Send it first");
        }

        if (amount == null){
            throw new IllegalArgumentException("The amount must not be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalStateException("Payment amount must be positive");
        }

        paidAmount = paidAmount.add(amount);

        if (isFullyPaid()){
            status = InvoiceStatus.PAID;
        }
    }

    public void cancel(){
        ensureNotPaid();
        if (status == InvoiceStatus.CANCELLED){
            return;
        }
        status = InvoiceStatus.CANCELLED;
    }

    public InvoiceStatus getStatus(){
        return status;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public boolean isFullyPaid(){
        return paidAmount != null
         && getTotalAmount().compareTo(BigDecimal.ZERO) > 0
                && paidAmount.compareTo(getTotalAmount()) >= 0;
    }

    public BigDecimal getPaidAmount(){
        return paidAmount == null ? BigDecimal.ZERO : paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount){
        this.paidAmount = paidAmount;
    }

    public void setStatus(InvoiceStatus status){
        this.status = status;
    }



    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public BigDecimal getOpenAmount(){
        BigDecimal open = getTotalAmount().subtract(paidAmount);
        return open.max(BigDecimal.ZERO);
    }

    public BigDecimal getOverpaidAmount(){
        BigDecimal overpaid = paidAmount.subtract(getTotalAmount());
        return overpaid.max(BigDecimal.ZERO);
    }

    public LocalDate getDueDate(){
        return dueDate;
    }

    public void setLocalDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }

    public LocalDate getIssueDate(){
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate){
        this.issueDate = issueDate;
    }

    public void setDueDate(LocalDate dueDate){
        ensureNotCancelled();
        ensureNotPaid();
        if (dueDate == null){
            throw new IllegalArgumentException("Due Date must not be null");
        }
        this.dueDate = dueDate;
        updateOverdueStatus();
    }

    public void markAsPaid(){
        if (status == InvoiceStatus.PAID){
            throw new IllegalStateException("Invoice is already paid");
        }

        this.status = InvoiceStatus.PAID;
    }


    private void ensureNotCancelled(){
        if (status == InvoiceStatus.CANCELLED){
            throw new IllegalStateException("Cancelled invoice cannot be changed");
        }
    }

    private void ensureNotPaid(){
        if (status == InvoiceStatus.PAID){
            throw new IllegalStateException("Paid invoice cannot be changed");
        }
    }

    private void ensureDraftOnly(String message){
        if (status != InvoiceStatus.DRAFT){
            throw new IllegalStateException(message + ". Current status: " + status);
        }
    }
}
