import de.rechnungflow.model.Client;
import de.rechnungflow.model.Invoice;
import de.rechnungflow.model.InvoiceItem;
import de.rechnungflow.model.InvoiceStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkAsPaidShouldSetPaidAmount {
    @Test
    void markAsPaid_shouldSetPaidAmountAndStatus(){

        Client client = new Client(1, "ABoba", "Vasya", "skoak@gmail.com", "123421343");
        Invoice inv =  new Invoice(1, client);

        InvoiceItem item = new InvoiceItem(
                "Cleaning",
                new BigDecimal("10"),
                new BigDecimal("20")
        );
        inv.addItem(item);

        inv.markAsPaid();

        assertEquals(InvoiceStatus.PAID, inv.getStatus());
        assertEquals(inv.getTotalAmount(), inv.getPaidAmount());
    }
}
