import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private final String invoiceNumber;
    private final Customer customer;
    private InvoiceStatus status = InvoiceStatus.DRAFT;
    private final List<InvoiceItem> items = new ArrayList<>();
    private static final ZoneId BUSINESS_ZONE = ZoneId.of("Europe/Berlin");
    private final LocalDate issueDate = LocalDate.now(BUSINESS_ZONE);
    private LocalDate dueDate = issueDate.plusDays(14);

    private double paidAmount = 0.0;

    public Invoice(String invoiceNumber, Customer customer){
        this.invoiceNumber = invoiceNumber;
        this.customer = customer;
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

    public double getTotalAmount(){
        double total = 0;
        for (InvoiceItem  item : items){
            total += item.getTotal();
        }
        return total;
    }

    public void pay(double amount){
        ensureNotCancelled();
        ensureNotPaid();
        if (status == InvoiceStatus.DRAFT){
            throw new IllegalStateException("Cannot pay a DRAFT invoice. Send it first");
        }
        if (amount <= 0){
            throw new IllegalStateException("Payment amount must be positive");
        }

        paidAmount += amount;

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
    public boolean isFullyPaid(){
        return paidAmount >= getTotalAmount();
    }

    public double getPaidAmount(){
        return paidAmount;
    }

    public double getOpenAmount(){
        double open = getTotalAmount() - paidAmount;
        return Math.max(open, 0.0);
    }

    public double getOverpaidAmount(){
        double overpaid = paidAmount - getTotalAmount();
        return Math.max(overpaid, 0.0);
    }

    public LocalDate getDueDate(){
        return dueDate;
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
