package com.rechnung_flow.RechnungFlowService.service;

import com.rechnung_flow.RechnungFlowService.model.enteties.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
public class InvoiceGeneratorService {
    private final WorkLogService workLogService;
    private final CleaningObjectService cleaningObjectService;
    private final InvoiceService invoiceService;

    public InvoiceGeneratorService(WorkLogService workLogService,
                                   CleaningObjectService cleaningObjectService,
                                   InvoiceService invoiceService){
        this.workLogService = workLogService;
        this.cleaningObjectService = cleaningObjectService;
        this.invoiceService = invoiceService;
    }

    @Transactional
    public Invoice generateInvoiceForTheObject(Long objectId, LocalDate from, LocalDate to){
        CleaningObject object = cleaningObjectService.getCleaningObjectById(objectId)
                .orElseThrow(()->new IllegalArgumentException("Cleaning object not found"));

        Client client = object.getClient();

        if(client == null){
            throw new IllegalArgumentException("Cleaning object has no client");
        }

        List<WorkLog> logs = workLogService.findByObjectAndPeriod(object, from, to);

        if (logs.isEmpty()){
            throw new IllegalArgumentException("No work logs found for this period");
        }

        BigDecimal totalHours = logs.stream()
                .map(WorkLog::getHours)
                .filter(hours -> hours != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalHours.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Total hours must be greater than zero");
        }

        BigDecimal hourlyRate = object.getHourlyRate();
        Invoice invoice = invoiceService.createInvoice(client);
        invoice.setWorkLogs(logs);
        String description = String.format(
                "Cleaning service: %s (%s - %s)",
                object.getName(), from, to
        );
        InvoiceItem item = new InvoiceItem(description, totalHours, hourlyRate);
        invoice.addItem(item);

        return invoiceService.save(invoice);
    }
}
