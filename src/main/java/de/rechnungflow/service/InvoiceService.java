package de.rechnungflow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rechnungflow.model.Client;
import de.rechnungflow.model.Invoice;
import de.rechnungflow.model.InvoiceStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InvoiceService {
    private static final Path FILE_PATH = Paths.get("data/invoices.json");
    private final List<Invoice> invoices = new ArrayList<>();
    private int nextInvoiceNumber = 1;
    private final Path filePath;
    private final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public Invoice createInvoice(Client client){
        Invoice invoice = new Invoice(nextInvoiceNumber, client);
        nextInvoiceNumber++;
        invoices.add(invoice);
        return invoice;
    }

    public InvoiceService (Path filePath){
        this.filePath = filePath;
        loadFromFile();
    }

    public InvoiceService(){
        this(Paths.get("data/invoices.json"));
    }

    public Optional<Invoice> findByNumber(int number){
        return invoices.stream()
                .filter(inv -> inv.getInvoiceNumber() == number)
                .findFirst();
    }

    public boolean markAsPaid(int number) {
        Invoice inv = findByNumber(number)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: #" + number));

        if (inv.getStatus() == InvoiceStatus.PAID) return false;
        if (inv.getStatus() == InvoiceStatus.CANCELLED) return false;

        BigDecimal total = inv.getTotalAmount();
        boolean hasItems = inv.getItems() != null && !inv.getItems().isEmpty();

        if (!hasItems && total.compareTo(BigDecimal.ZERO) == 0) return false;
        if (total.compareTo(BigDecimal.ZERO) <= 0) return false;

        inv.setPaidAmount(total);
        inv.setStatus(InvoiceStatus.PAID);
        saveToFile();
        return true;
    }



    public void loadFromFile(){
        //ObjectMapper mapper = new ObjectMapper();

        if (!Files.exists(filePath)){
            return;
        }

        try {
            List<Invoice> loaded = mapper.readValue(
                    filePath.toFile(),
                    new TypeReference<List<Invoice>>() {}
            );
            invoices.clear();
            invoices.addAll(loaded);

            nextInvoiceNumber = invoices.stream()
                    .mapToInt(Invoice::getInvoiceNumber)
                    .max()
                    .orElse(0) + 1;

        } catch (IOException e) {
            System.err.println("WARNING: Failed to save invoice. Start from the beginning. The Problem: " + e.getMessage());
            invoices.clear();
            nextInvoiceNumber = 1;
        }


    }

    public void saveToFile(){
        //ObjectMapper mapper = new ObjectMapper();

        try{
            Path parent = filePath.getParent();
            if (parent != null){
                Files.createDirectories(parent);
            }

            Path tmp = filePath.resolveSibling(filePath.getFileName() + ".tmp");

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(tmp.toFile(), invoices);

            Files.move(tmp, filePath,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);

        } catch (IOException e) {
            System.err.println("WARNING: Failed to save invoice. Start from the beginning. The Problem: " + e.getMessage());
            invoices.clear();
            nextInvoiceNumber = 1;
        }
    }

    public boolean payPartially(int number, BigDecimal amount){
        Invoice inv = findByNumber(number)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: #" + number));
        if (!canPayPartially(inv, amount)){
            return false;
        }

        BigDecimal total = inv.getTotalAmount();
        BigDecimal newPaid = inv.getPaidAmount().add(amount);

        boolean fullyPaid = newPaid.compareTo(total) >= 0;
        inv.setPaidAmount(fullyPaid ? total : newPaid);
        inv.setStatus(fullyPaid ? InvoiceStatus.PAID : InvoiceStatus.PARTIALLY_PAID);

        saveToFile();
        return true;
    }

    private boolean canPayPartially(Invoice inv, BigDecimal amount){
        if (inv == null) return false;
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) return false;

        InvoiceStatus st = inv.getStatus();
        if (st == InvoiceStatus.CANCELLED || st == InvoiceStatus.PAID) return false;

        return inv.getTotalAmount().compareTo(BigDecimal.ZERO) > 0;
    }

    public List<Invoice> getAll(){
        return Collections.unmodifiableList(invoices);
    }

    public boolean isEmpty(){
        return invoices.isEmpty();
    }

    public void add(Invoice invoice){
        invoices.add(invoice);
    }


}
