import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvoiceService {
    private final List<Invoice> invoices = new ArrayList<>();
    private int nextInvoiceNumber = 1;

    public Invoice createInvoice(Customer customer){
        Invoice invoice = new Invoice(nextInvoiceNumber, customer);
        nextInvoiceNumber++;
        invoices.add(invoice);
        return invoice;
    }

    public Invoice getInvoiceNumber(int number){
        for (Invoice inv : invoices){
            if (inv.getIdentNumber() == number){
                return inv;
            }
        }
        return null;
    }

    public List<Invoice> getAll(){
        return Collections.unmodifiableList(invoices);
    }

    public boolean isEmpty(){
        return invoices.isEmpty();
    }


}
