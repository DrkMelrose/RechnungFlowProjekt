import de.rechnungflow.model.*;
import de.rechnungflow.service.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class InvoiceGeneratorServiceLinkingTest {
    @Test
    public void testCorrectLinking(){
        //GIVEN
        ClientService clientService = new ClientService();
        EmployeeService employeeService = new EmployeeService();
        CleaningObjectService cleaningObjectService = new CleaningObjectService();
        WorkLogService workLogService = new WorkLogService();
        InvoiceService invoiceService = new InvoiceService();

        InvoiceGeneratorService invoiceGeneratorService = new InvoiceGeneratorService(
                workLogService, cleaningObjectService, clientService, invoiceService
        );

        Employee emp = new Employee(1, "Maria", "384782", "maria@gmail.com");

        Client client1 = new Client(1,"Some GmbH", "Tomas", "hohoho@gmail.com", "32364782364");
        Client client2 = new Client(2, "Hello GmbH", "Andrew", "andrew@gmail.com", "234892324");

        clientService.add(client1);
        clientService.add(client2);

        CleaningObject obj1 = new CleaningObject(1, client1.getId(), "SomeGmbH", "Luisianen Straße 123", new BigDecimal("45"), new BigDecimal("1500"), true);
        CleaningObject obj2 = new CleaningObject(2, client2.getId(), "Hello GmbH", "Bonner Straße 321", new BigDecimal("20"), new BigDecimal("1000"), true);

        cleaningObjectService.add(obj1);
        cleaningObjectService.add(obj2);

        LocalDate from = LocalDate.of(2026, 3, 1);
        LocalDate to = LocalDate.of(2026, 3,31);

        //Worklogs only for the first object
        WorkLog a1 = new WorkLog(100, 1, obj1.getCleaningObjectId(), LocalDate.of(2026,3,2), new BigDecimal("8"), "cleaning", true);
        WorkLog a2 = new WorkLog(101, 1, obj1.getCleaningObjectId(), LocalDate.of(2026, 3, 5), new BigDecimal("5"), "cleaning", true);

        //Worklog for the second object
        WorkLog b1 = new WorkLog(200, 1, obj2.getCleaningObjectId(), LocalDate.of(2026, 3, 2), new BigDecimal("9"), "cleaning", true);

        workLogService.add(a1);
        workLogService.add(a2);
        workLogService.add(b1);

        //When
        Invoice invoice = invoiceGeneratorService.generateInvoiceForObject(obj1.getCleaningObjectId(), from, to);

        //Then
        assertNotNull(invoice, "Invoice must not be 0");

        assertNotNull(invoice.getCustomer(), "Client in invoice must not be 0");
        assertEquals(client1.getId(), invoice.getCustomer().getId(), "Invoice must belong to the client, which about obj1.ClientId is linked");

        List<WorkLog> logsInInvoice = invoice.getWorkLogs();
        assertNotNull(logsInInvoice, "Worklogs in invoice must not be 0");
        assertFalse(logsInInvoice.isEmpty(), "Worklogs in invoice must not be empty");

        assertTrue(
                logsInInvoice.stream().allMatch(wl->wl.getObjectId() == obj1.getCleaningObjectId()),
                "All worklogs have to match to obj1.getObjectId()"
        );

        assertFalse(
                logsInInvoice.stream().anyMatch(wl-> wl.getObjectId() == obj2.getCleaningObjectId()),
                "No Worklogs from Obj2 may appear in the invoice for Obj1"
        );

        assertEquals(2, logsInInvoice.size(), "It has to be 2 Logs from Obj1");
        assertTrue(logsInInvoice.stream().anyMatch(wl -> wl.getEmployeeId() == 1));
    }
}
