import de.rechnungflow.model.Client;
import de.rechnungflow.model.Invoice;
import de.rechnungflow.model.InvoiceItem;
import de.rechnungflow.model.InvoiceStatus;
import de.rechnungflow.service.InvoiceService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarkAsPaidSetsPaidAmountAndStatusTest {

    @Test
    void markAsPaidSetsPaidAmountToTotalAndStatusPaid(){

        //GIVEN
        InvoiceService service = new InvoiceService();
        Client client = new Client("Gosha", "123123", "koko", "123");
        Invoice invoice = service.createInvoice(client);
        invoice.addItem(new InvoiceItem("Ball", new BigDecimal("2"), new BigDecimal("79.12")));

        //WHEN
        int number = invoice.getInvoiceNumber();
        boolean ok = service.markAsPaid(number);
        assertTrue(ok);
        Invoice updated = service.findByNumber(number).orElseThrow();

        //THEN
        assertEquals(0, updated.getTotalAmount().compareTo(updated.getPaidAmount()));
        assertEquals(InvoiceStatus.PAID, updated.getStatus());

    }
}
