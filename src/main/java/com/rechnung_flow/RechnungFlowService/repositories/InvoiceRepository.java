package com.rechnung_flow.RechnungFlowService.repositories;

import com.rechnung_flow.RechnungFlowService.model.enteties.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("""
            SELECT MAX(i.invoiceNumber)
            FROM Invoice i
            WHERE i.invoiceNumber LIKE CONCAT(:year, '-%')
            """)
    String findMaxInvoiceNumberForYear(String year);
}
