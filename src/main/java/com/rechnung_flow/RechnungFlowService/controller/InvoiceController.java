package com.rechnung_flow.RechnungFlowService.controller;

import com.rechnung_flow.RechnungFlowService.model.enteties.Client;
import com.rechnung_flow.RechnungFlowService.model.enteties.Invoice;
import com.rechnung_flow.RechnungFlowService.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<Invoice> getAll(){
        return invoiceService.getAll();
    }

    @GetMapping("/{id}")
    public Invoice getById(@PathVariable Long id){
        return invoiceService.findById(id);
    }

    @PostMapping
    public Invoice create(@RequestParam Client client){
        return invoiceService.createInvoice(client);
    }

    @PostMapping("/{id}/send")
    public Invoice send(@PathVariable Long id){
        return invoiceService.sendInvoice(id);
    }

    @PostMapping("/{id}/pay")
    public Invoice pay(@PathVariable Long id, @RequestParam BigDecimal amount){
        return invoiceService.payPartially(id, amount);
    }

    @PostMapping("/{id}/pay-full")
    public Invoice payFull(@PathVariable Long id){
        return invoiceService.markAsPaid(id);
    }

    @PostMapping("/{id}/cancel")
    public Invoice cancel(@PathVariable Long id){
        return invoiceService.cancelInvoice(id);
    }
}
