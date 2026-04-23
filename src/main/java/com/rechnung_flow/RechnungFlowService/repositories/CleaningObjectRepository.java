package com.rechnung_flow.RechnungFlowService.repositories;

import com.rechnung_flow.RechnungFlowService.model.enteties.CleaningObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CleaningObjectRepository extends JpaRepository<CleaningObject, Long> {
}
