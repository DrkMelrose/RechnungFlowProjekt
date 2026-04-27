package com.rechnung_flow.RechnungFlowService.repositories;

import com.rechnung_flow.RechnungFlowService.model.enteties.CleaningObject;
import com.rechnung_flow.RechnungFlowService.model.enteties.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    List<WorkLog> findByObjectAndDateBetween(
            CleaningObject object,
            LocalDate from,
            LocalDate to
    );
}
