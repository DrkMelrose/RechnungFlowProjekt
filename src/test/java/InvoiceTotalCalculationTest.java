import de.rechnungflow.model.Customer;
import de.rechnungflow.model.Invoice;
import de.rechnungflow.model.InvoiceItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTotalCalculationTest {
    @Test
    void calculateTotalFromItems(){

        //GIVEN
        Customer customer = new Customer("Amigo", "friend@gmail.com");
        Invoice invoice = new Invoice(1, customer);
        invoice.addItem(new InvoiceItem("Bread A", 2, new BigDecimal("4.33")));
        invoice.addItem(new InvoiceItem("Bread B", 3, new BigDecimal("2.5")));

        //WHEN
        BigDecimal expected = new BigDecimal("16.16");

        //THEN
        assertEquals(0, expected.compareTo(invoice.getTotalAmount()));
    }

}
