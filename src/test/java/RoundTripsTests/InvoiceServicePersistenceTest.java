package RoundTripsTests;

import de.rechnungflow.model.Client;
import de.rechnungflow.model.Invoice;
import de.rechnungflow.service.InvoiceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvoiceServicePersistenceTest {
    @TempDir
    Path tempDir;

    @Test
    void saveAndLoad_roundtrip_invoiceService() throws Exception{
        //GIVEN
        Path file = tempDir.resolve("invoices.json");

        InvoiceService invoiceService = new InvoiceService(file);

        Client client1 = new Client(1, "Frisches Brot GmbH", "Monica Zellincki", "monica@gmail.com", "123671263");
        Client client2 = new Client(2, "AmigoSecurityGmbH", "Edward Paßgang", "eddi@gmail.com", "1273712783");

        Invoice invoice1 = new Invoice(1, client1);
        Invoice invoice2 = new Invoice(2, client2);

        invoiceService.add(invoice1);
        invoiceService.add(invoice2);

        //WHEN save -> new instance -> load
        invoiceService.saveToFile();

        if (Files.exists(file)){
            System.out.println("Content: \n" + Files.readString(file));
        }

        InvoiceService invoiceService2 = new InvoiceService(file);

        //THEN
        assertEquals(2, invoiceService.getAll().size(), "The number of invoices have to be same");
        Invoice loaded1 = invoiceService2.findByNumber(1).orElseThrow();
        assertEquals(1, loaded1.getInvoiceNumber());
        assertEquals("Frisches Brot GmbH", loaded1.getCustomer().getCompanyName());

        Invoice loaded2 = invoiceService2.findByNumber(2).orElseThrow();
        assertEquals(2, loaded2.getInvoiceNumber());
        assertEquals("AmigoSecurityGmbH", loaded2.getCustomer().getCompanyName());

    }
}
