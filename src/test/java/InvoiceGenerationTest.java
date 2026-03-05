import de.rechnungflow.model.*;
import de.rechnungflow.service.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InvoiceGenerationTest {
    @Test
    public void calculateHoursFromWorkLogs(){
        //GIVEN
        WorkLogService workLogService = new WorkLogService();
        CleaningObjectService cleaningObjectService = new CleaningObjectService();
        ClientService clientService = new ClientService();
        InvoiceService invoiceService = new InvoiceService();

        InvoiceGeneratorService invoiceGeneratorService = new InvoiceGeneratorService(
                workLogService,cleaningObjectService, clientService, invoiceService);


        Client client = new Client(1, "Hello GmbH", "Edwin", "edwin@gmail.com", "4912377171");
        Employee employee = new Employee(1, "Jason", "+4915228186662", "jason@gmail.com");
        CleaningObject obj = new CleaningObject(1, 1, "Hello GmbH", "12312 Bielefeld, Göttingen Straße 11",
                new BigDecimal(45), new BigDecimal(1500), true);
        LocalDate dateOfCleaning = LocalDate.of(2026,3,1);
        LocalDate dateOfCleaning2 = LocalDate.of(2026,3,2);
        LocalDate dateOfCleaning3 = LocalDate.of(2026,3,3);
        String description = "Cleaning";
        Boolean approve = true;

        clientService.add(client);
        cleaningObjectService.add(obj);

        WorkLog workLog = new WorkLog(1, 1, 1, dateOfCleaning,
                new BigDecimal("8"),
                description,
                approve);

        WorkLog workLog2 = new WorkLog(1, 1, 1, dateOfCleaning2,
                new BigDecimal("10"),
                description,
                approve);

        WorkLog workLog3 = new WorkLog(1, 1, 1, dateOfCleaning3,
                new BigDecimal("7"),
                description,
                approve);

        workLogService.add(workLog);
        workLogService.add(workLog2);
        workLogService.add(workLog3);

        //WHEN

        Invoice invoice = invoiceGeneratorService.generateInvoiceForObject(
                1,
                LocalDate.of(2026,3,1),
                LocalDate.of(2026, 3, 3)
        );

        //THEN
        assertNotNull(invoice);
        assertEquals(new BigDecimal("25"), invoice.getHoursInWorklogs());

    }

}
