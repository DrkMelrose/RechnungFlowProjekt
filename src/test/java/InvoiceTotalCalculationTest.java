import de.rechnungflow.model.Client;
import de.rechnungflow.model.Invoice;
import de.rechnungflow.model.InvoiceItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTotalCalculationTest {
    @Test
    void calculateTotalFromItems(){

        //GIVEN
        Client client = new Client("Amigo", "friend@gmail.com", "oskfosdk", "123");
        Invoice invoice = new Invoice(1, client);
        invoice.addItem(new InvoiceItem("Bread A", new BigDecimal(2), new BigDecimal("4.33")));
        invoice.addItem(new InvoiceItem("Bread B", new BigDecimal(3), new BigDecimal("2.5")));

        //WHEN
        BigDecimal expected = new BigDecimal("16.16");

        //THEN
        assertEquals(0, expected.compareTo(invoice.getTotalAmount()));
    }

}
