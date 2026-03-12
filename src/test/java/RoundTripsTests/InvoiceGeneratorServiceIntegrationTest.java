package RoundTripsTests;

import de.rechnungflow.model.*;
import de.rechnungflow.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InvoiceGeneratorServiceIntegrationTest {
    @TempDir
    Path tempDir;

    @Test
    public void saveAndLoad_roadtrip_invoiceGeneratorServiceIntegration(){
        //GIVEN
        Path file = tempDir.resolve("invoices.json");
        InvoiceService invoiceService = new InvoiceService(file);
        EmployeeService employeeService = new EmployeeService();
        ClientService clientService = new ClientService();
        CleaningObjectService cleaningObjectService = new CleaningObjectService();
        WorkLogService workLogService = new WorkLogService();
        InvoiceGeneratorService invoiceGeneratorService = new InvoiceGeneratorService(
                workLogService,
                cleaningObjectService,
                clientService,
                invoiceService
        );


        Employee emp1 = new Employee(1, "Mariia", "127771712", "mariiagrah@gmail.com");
        Client client = new Client(1, "BoratGmbH", "Borat Hulizade", "boratbrat@gmail.com", "12737717");
        CleaningObject obj1 = new CleaningObject(1, 1, "SoundStudio", "Kölner straße 125", new BigDecimal("30"), new BigDecimal("1500"), true);

        WorkLog wl1 = new WorkLog(1, 1, 1, LocalDate.of(2026, 3, 8), new BigDecimal("7"), "floor and windows cleaning", true);

        workLogService.add(wl1);
        cleaningObjectService.add(obj1);
        clientService.add(client);
        employeeService.add(emp1);

        Invoice created =
                invoiceGeneratorService.generateInvoiceForObject(
                        1,
                        LocalDate.of(2026, 03,01),
                        LocalDate.of(2026,03,31)
                );


        //WHEN

        //THEN
        assertNotNull(created);
        assertEquals(1, invoiceService.getAll().size(), "The invoice number must be same");

        InvoiceService reloaded = new InvoiceService(file);
        assertEquals(1, reloaded.getAll().size());

        Invoice loaded = reloaded.findByNumber(created.getInvoiceNumber()).orElseThrow();
        assertEquals("BoratGmbH", loaded.getCustomer().getCompanyName());
    }
}
