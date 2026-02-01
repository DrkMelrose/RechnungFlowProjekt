package de.rechnungflow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rechnungflow.model.Customer;
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

public class InvoiceService {
    private static final Path FILE_PATH = Paths.get("data/invoices.json");
    private final List<Invoice> invoices = new ArrayList<>();
    private int nextInvoiceNumber = 1;
    private final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public Invoice createInvoice(Customer customer){
        Invoice invoice = new Invoice(nextInvoiceNumber, customer);
        nextInvoiceNumber++;
        invoices.add(invoice);
        //saveToFile();
        return invoice;
    }

    public Invoice findByNumber(int number){
        for (Invoice inv : invoices){
            if (inv.getInvoiceNumber() == number){
                return inv;
            }
        }
        return null;
    }

    public boolean markAsPaid (int number){
        Invoice inv = findByNumber(number);
        if (inv == null) return false;

        BigDecimal total = inv.getTotalAmount();
        inv.setPaidAmount(total);
        inv.setStatus(InvoiceStatus.PAID);
        saveToFile();

        return true;
    }

    public void loadFromFile(){
        //ObjectMapper mapper = new ObjectMapper();

        if (!Files.exists(FILE_PATH)){
            return;
        }

        try {
            List<Invoice> loaded = mapper.readValue(
                    FILE_PATH.toFile(),
                    new TypeReference<List<Invoice>>() {}
            );
            invoices.clear();
            invoices.addAll(loaded);

            nextInvoiceNumber = invoices.stream()
                    .mapToInt(Invoice::getInvoiceNumber)
                    .max()
                    .orElse(0) + 1;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load invoices", e);
        }


    }

    public void saveToFile(){
        //ObjectMapper mapper = new ObjectMapper();

        try{
            Path parent = FILE_PATH.getParent();
            if (parent != null){
                Files.createDirectories(parent);
            }

            Path tmp = FILE_PATH.resolveSibling(FILE_PATH.getFileName() + ".tmp");

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(tmp.toFile(), invoices);

            Files.move(tmp, FILE_PATH,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);

        } catch (IOException e) {
            System.err.println("WARNING: Failed to save invoice. Start from the beginning. The Problem: " + e.getMessage());
            invoices.clear();
            nextInvoiceNumber = 1;
        }
    }

    public List<Invoice> getAll(){
        return Collections.unmodifiableList(invoices);
    }

    public boolean isEmpty(){
        return invoices.isEmpty();
    }


}
