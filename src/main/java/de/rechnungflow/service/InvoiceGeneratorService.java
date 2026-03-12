package de.rechnungflow.service;

import de.rechnungflow.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InvoiceGeneratorService {

    private final WorkLogService workLogService;
    private final CleaningObjectService cleaningObjectService;
    private final ClientService clientService;
    private final InvoiceService invoiceService;

    public InvoiceGeneratorService(
            WorkLogService workLogService,
            CleaningObjectService cleaningObjectService,
            ClientService clientService,
            InvoiceService invoiceService){
        this.workLogService = workLogService;
        this.cleaningObjectService = cleaningObjectService;
        this.clientService = clientService;
        this.invoiceService = invoiceService;
    }


    public Invoice generateInvoiceForObject(int objectId, LocalDate from, LocalDate to){

        CleaningObject object = cleaningObjectService.findCleaningObjectById(objectId).orElseThrow();
        if (object == null){
            return null;
        }

        Client client = clientService.findClientById(object.getClientId());
        if (client == null){
            return null;
        }

        List<WorkLog> logs = workLogService.findByObjectAndPeriod(objectId, from, to);

        BigDecimal totalHours = logs.stream()
                .map(WorkLog::getHours)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalHours.compareTo(BigDecimal.ZERO) <= 0){
            return null;
        }

        BigDecimal hourlyRate = new BigDecimal(String.valueOf(object.getHourlyRate()));

        Invoice invoice = invoiceService.createInvoice(client);

        invoice.setWorkLogs(logs);

        String description = String.format(
                "Cleaning service: %s (%s - %s)",
                object.getName(), from, to
        );

        InvoiceItem item = new InvoiceItem(description, totalHours, hourlyRate);

        invoice.addItem(item);

        invoiceService.saveToFile();

        return invoice;
    }


}
