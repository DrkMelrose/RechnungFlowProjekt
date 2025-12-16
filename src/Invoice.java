import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private String invoiceNumber;
    private Customer customer;
    private InvoiceStatus status = InvoiceStatus.DRAFT;
    private List<InvoiceItem> items = new ArrayList<>();
    private double paidAmount = 0.0;

    public Invoice(String invoiceNumber, Customer customer){
        this.invoiceNumber = invoiceNumber;
        this.customer = customer;
    }

    public void addItem(InvoiceItem item){
        items.add(item);
    }

    public double getTotalAmount(){
        double total = 0;
        for (InvoiceItem  item : items){
            total += item.getTotal();
        }
        return total;
    }

    public void pay(double amount){
        if (amount <= 0) throw new IllegalArgumentException("The payment must be positive");
        paidAmount += amount;

        if (paidAmount >= getTotalAmount()){
            status = InvoiceStatus.PAID;
        }
        if (status == InvoiceStatus.CANCELLED) throw new IllegalStateException("Cancelled invoice cannot be paid");
    }

    public void cancel(){
        if (status == InvoiceStatus.PAID) throw new IllegalStateException("Paid invoice cannot be cancelled");
        status = InvoiceStatus.CANCELLED;
    }

    public InvoiceStatus getStatus(){
        return status;
    }
}
