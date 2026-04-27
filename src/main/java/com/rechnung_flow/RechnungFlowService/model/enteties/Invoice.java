package com.rechnung_flow.RechnungFlowService.model.enteties;

import com.rechnung_flow.RechnungFlowService.model.enums.InvoiceStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Invoice {
    private static final ZoneId BUSINESS_ZONE = ZoneId.of("Europe/Berlin");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String invoiceNumber;

    @ManyToOne
    private Client client;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.DRAFT;

    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "invoice")
    private List<WorkLog> workLogs = new ArrayList<>();

    public Invoice(){

    }

    public Invoice(Client client){
        this.client = client;
        this.issueDate = LocalDate.now(BUSINESS_ZONE);
        this.dueDate = this.issueDate.plusDays(14);
    }

    @PrePersist
    public void prePersist(){
        if (issueDate == null) {
            issueDate = LocalDate.now(BUSINESS_ZONE);
        }
        if (dueDate == null){
            dueDate = issueDate.plusDays(14);
        }
        if (paidAmount == null){
            paidAmount = BigDecimal.ZERO;
        }
        if (status == null){
            status = InvoiceStatus.DRAFT;
        }
    }

    public void addWorkLog(WorkLog workLog){
        if (workLog == null){
            throw new IllegalArgumentException("WorkLog must not be null");
        }
        workLog.setInvoice(this);
        workLogs.add(workLog);
    }

    public void addItem(InvoiceItem item) {
        ensureNotCancelled();
        ensureNotPaid();
        ensureDraftOnly("Items can be added only in DRAFT status");

        if (item == null) {
            throw new IllegalArgumentException("Item must not be null");
        }

        item.setInvoice(this);
        items.add(item);
    }

    public void send() {
        ensureNotCancelled();
        ensureNotPaid();
        ensureDraftOnly("Only a DRAFT invoice can be sent");

        if (items.isEmpty()) {
            throw new IllegalStateException("Cannot send an invoice without items");
        }

        status = InvoiceStatus.SENT;
    }

    public BigDecimal getTotalAmount() {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return items.stream()
                .map(InvoiceItem::getTotal)
                .filter(total -> total != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getHoursInWorklogs() {
        if (workLogs == null || workLogs.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return workLogs.stream()
                .map(WorkLog::getHours)
                .filter(hours -> hours != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void pay(BigDecimal amount) {
        ensureNotCancelled();
        ensureNotPaid();

        if (status == InvoiceStatus.DRAFT) {
            throw new IllegalStateException("Cannot pay a DRAFT invoice. Send it first");
        }

        if (amount == null) {
            throw new IllegalArgumentException("The amount must not be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }

        BigDecimal total = getTotalAmount();

        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Invoice total must be positive");
        }

        BigDecimal newPaidAmount = getPaidAmount().add(amount);

        if (newPaidAmount.compareTo(total) >= 0) {
            paidAmount = total;
            status = InvoiceStatus.PAID;
        } else {
            paidAmount = newPaidAmount;
            status = InvoiceStatus.PARTIALLY_PAID;
        }
    }

    public void markAsPaid() {
        ensureNotCancelled();

        if (status == InvoiceStatus.PAID) {
            throw new IllegalStateException("Invoice is already paid");
        }

        BigDecimal total = getTotalAmount();

        if (items == null || items.isEmpty()) {
            throw new IllegalStateException("Invoice has no items");
        }

        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Total must be positive");
        }

        paidAmount = total;
        status = InvoiceStatus.PAID;
    }

    public void cancel() {
        ensureNotPaid();

        if (status == InvoiceStatus.CANCELLED) {
            return;
        }

        status = InvoiceStatus.CANCELLED;
    }

    public void updateOverdueStatus() {
        if (status == InvoiceStatus.SENT || status == InvoiceStatus.OVERDUE) {
            if (!isFullyPaid() && LocalDate.now(BUSINESS_ZONE).isAfter(dueDate)) {
                status = InvoiceStatus.OVERDUE;
            }
        }
    }

    public boolean isFullyPaid() {
        return getTotalAmount().compareTo(BigDecimal.ZERO) > 0
                && getPaidAmount().compareTo(getTotalAmount()) >= 0;
    }

    public BigDecimal getOpenAmount() {
        return getTotalAmount().subtract(getPaidAmount()).max(BigDecimal.ZERO);
    }

    public BigDecimal getOverpaidAmount() {
        return getPaidAmount().subtract(getTotalAmount()).max(BigDecimal.ZERO);
    }

    private void ensureNotCancelled() {
        if (status == InvoiceStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled invoice cannot be changed");
        }
    }

    private void ensureNotPaid() {
        if (status == InvoiceStatus.PAID) {
            throw new IllegalStateException("Paid invoice cannot be changed");
        }
    }

    private void ensureDraftOnly(String message) {
        if (status != InvoiceStatus.DRAFT) {
            throw new IllegalStateException(message + ". Current status: " + status);
        }
    }

    public Long getId() {
        return id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public List<WorkLog> getWorkLogs() {
        return workLogs;
    }

    public void setWorkLogs(List<WorkLog> workLogs) {
        this.workLogs = workLogs;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount == null ? BigDecimal.ZERO : paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        ensureNotCancelled();
        ensureNotPaid();

        if (dueDate == null) {
            throw new IllegalArgumentException("Due date must not be null");
        }

        this.dueDate = dueDate;
        updateOverdueStatus();
    }
}

