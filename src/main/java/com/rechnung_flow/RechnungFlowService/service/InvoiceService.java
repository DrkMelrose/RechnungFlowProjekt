package com.rechnung_flow.RechnungFlowService.service;

import com.rechnung_flow.RechnungFlowService.model.enteties.Client;
import com.rechnung_flow.RechnungFlowService.model.enteties.Invoice;
import com.rechnung_flow.RechnungFlowService.repositories.ClientRepository;
import com.rechnung_flow.RechnungFlowService.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, ClientRepository clientRepository){
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
    }

    public Invoice createInvoice(Client client){
        if (client == null){
            throw new IllegalArgumentException("Client must be not null");
        }
        String invoiceNumber = generateNextInvoiceNumber();

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceNumber);

        return invoiceRepository.save(invoice);
    }

    public Invoice save(Invoice invoice){
        return invoiceRepository.save(invoice);
    }

    private String generateNextInvoiceNumber(){
        String year = String.valueOf(LocalDate.now().getYear());
        String lastInvoiceNumber = invoiceRepository.findMaxInvoiceNumberForYear(year);

        int nextNumber = 1;
        if (lastInvoiceNumber != null){
            String numberPart = lastInvoiceNumber.substring(5);
            nextNumber = Integer.parseInt(numberPart) + 1;
        }
        return String.format("%s-%03d", year, nextNumber);
    }

    public Invoice findById(Long invoiceId){
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + invoiceId));
    }

    public List<Invoice> getAll(){
        return invoiceRepository.findAll();
    }

    public Invoice markAsPaid(Long invoiceId){
        Invoice invoice = findById(invoiceId);
        invoice.markAsPaid();
        return invoiceRepository.save(invoice);
    }

    public Invoice payPartially(Long invoiceId, BigDecimal amount){
        Invoice invoice = findById(invoiceId);
        invoice.pay(amount);
        return invoiceRepository.save(invoice);
    }

    public Invoice sendInvoice(Long invoiceId){
        Invoice invoice = findById(invoiceId);
        invoice.send();
        return invoiceRepository.save(invoice);
    }

    public Invoice cancelInvoice(Long invoiceId){
        Invoice invoice = findById(invoiceId);
        invoice.cancel();
        return invoiceRepository.save(invoice);
    }


}
