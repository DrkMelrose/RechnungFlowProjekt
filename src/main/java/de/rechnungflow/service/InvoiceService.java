package de.rechnungflow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rechnungflow.model.Customer;
import de.rechnungflow.model.Invoice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvoiceService {
    private static final Path FILE_PATH = Paths.get("data/invoices.json");
    private final List<Invoice> invoices = new ArrayList<>();
    private int nextInvoiceNumber = 1;
    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

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

        inv.markAsPaid();
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

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(FILE_PATH.toFile(), invoices);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save invoices", e);
        }
    }

    public List<Invoice> getAll(){
        return Collections.unmodifiableList(invoices);
    }

    public boolean isEmpty(){
        return invoices.isEmpty();
    }


}
