import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rechnungflow.model.Customer;
import de.rechnungflow.model.Invoice;
import de.rechnungflow.model.InvoiceItem;
import de.rechnungflow.model.InvoiceStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializeDeserializeInvoiceWithItemsTest {

    @Test
    void serializeDeserializeInvoiceWithItems() throws Exception {

        //GIVEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        Customer customer = new Customer("Oleg", "kkokos");
        Invoice invoice = new Invoice(7, customer);
        invoice.setPaidAmount(new BigDecimal("0.00"));
        invoice.addItem(new InvoiceItem("A", 2, new BigDecimal("7.25")));
        invoice.setStatus(InvoiceStatus.SENT);

        //WHEN
        String json = mapper.writeValueAsString(invoice);
        Invoice restored = mapper.readValue(json, Invoice.class);

        //THEN
        assertEquals(invoice.getInvoiceNumber(), restored.getInvoiceNumber());
        assertEquals(invoice.getStatus(), restored.getStatus());
        assertEquals(0, invoice.getPaidAmount().compareTo(restored.getPaidAmount()));
        assertEquals(invoice.getItems().size(), restored.getItems().size());

        InvoiceItem restoredItem = restored.getItems().get(0);
        assertEquals("A", restoredItem.getDescription());
        assertEquals(2, restoredItem.getQuantity());
        assertEquals(0, new BigDecimal("7.25").compareTo(restoredItem.getPrice()));
    }
}
