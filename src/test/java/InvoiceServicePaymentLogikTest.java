import de.rechnungflow.model.*;
import de.rechnungflow.service.InvoiceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceServicePaymentLogikTest {

    private Invoice createInvoiceWithTotalFromWorkLogs(){
        Client client = new Client(1, "VasyaGmbH", "Vasya Pupkin", "vasiv@gmail.com", "12312471");

        InvoiceItem invoiceItem1 = new InvoiceItem("windows cleaning", new BigDecimal("7"), new BigDecimal("25"));
        InvoiceItem invoiceItem2 = new InvoiceItem("floor cleaning", new BigDecimal("8"), new BigDecimal("15"));

        //WorkLog wl1 = new WorkLog(1, 1, 1, LocalDate.of(2026, 03, 10), new BigDecimal("7"), "windows cleaning", true);
        //WorkLog wl2 = new WorkLog(2, 1,1, LocalDate.of(2026, 03, 12), new BigDecimal("8"), "floor cleaning", true);

        Invoice invoice = new Invoice(1, client);
        invoice.setInvoiceItem(List.of(invoiceItem1, invoiceItem2));

        return invoice;
    }

    @TempDir
    Path tempDir;

    @Test
    void payPartially_shouldCapPaidAmountToTotal_andSetStatusPaid_whenNewPaidReachesOrExceedsTotal() {
        // GIVEN
        Path file = tempDir.resolve("invoices.json");
        InvoiceService invoiceService = new InvoiceService(file);

        Invoice invoice = createInvoiceWithTotalFromWorkLogs();
        invoice.setPaidAmount(new BigDecimal("20"));
        invoice.setStatus(InvoiceStatus.SENT);

        invoiceService.add(invoice);

        //WHEN
        boolean result = invoiceService.payPartially(1, new BigDecimal("999"));

        //THEN
        //assertTrue(result);
        assertTrue(invoice.getTotalAmount().compareTo(BigDecimal.ZERO) > 0);
        assertEquals(invoice.getTotalAmount(), invoice.getPaidAmount());
        assertEquals(InvoiceStatus.PAID, invoice.getStatus());
    }

    @Test
    void payPartially_shouldReturnFalse_whenAmountIsZeroOrNegative(){
        //GIVEN
        Path file = tempDir.resolve("invoice.json");
        InvoiceService invoiceService = new InvoiceService(file);

        Invoice invoice = createInvoiceWithTotalFromWorkLogs();
        invoiceService.add(invoice);

        //WHEN
        boolean resultZero = invoiceService.payPartially(1, BigDecimal.ZERO);
        boolean resultNegative = invoiceService.payPartially(1, new BigDecimal("-10"));

        //THEN
        assertFalse(resultZero);
        assertFalse(resultNegative);
        assertEquals(BigDecimal.ZERO, invoice.getPaidAmount());
    }

    @Test
    void payPartially_shouldReturnFalse_whenInvoicesIsCancelled(){
        //GIVEN
        Path file = tempDir.resolve("invoice.json");
        InvoiceService invoiceService = new InvoiceService(file);

        Invoice invoice = createInvoiceWithTotalFromWorkLogs();
        invoiceService.add(invoice);

        //WHEN
        invoice.setStatus(InvoiceStatus.CANCELLED);

        //THEN
        assertEquals(InvoiceStatus.CANCELLED, invoice.getStatus());
        assertFalse(invoiceService.payPartially(1, new BigDecimal("100")));
        assertEquals(BigDecimal.ZERO, invoice.getPaidAmount());
    }

    @Test
    void markAsPaid_shouldSetPaidAmountToTotal(){
        //GIVEN
        Path file = tempDir.resolve("invoices.json");
        InvoiceService invoiceService = new InvoiceService(file);

        Invoice invoice = createInvoiceWithTotalFromWorkLogs();
        invoice.setPaidAmount(new BigDecimal("5"));
        invoice.setStatus(InvoiceStatus.SENT);
        invoiceService.add(invoice);

        //WHEN
        boolean result = invoiceService.markAsPaid(1);

        //THEN
        assertTrue(result);
        assertEquals(invoice.getTotalAmount(), invoice.getPaidAmount());
        assertEquals(InvoiceStatus.PAID, invoice.getStatus());
    }


}
