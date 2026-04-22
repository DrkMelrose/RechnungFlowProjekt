package com.rechnung_flow.RechnungFlowService.repositories;

import com.rechnung_flow.RechnungFlowService.model.enteties.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
