package com.rechnung_flow.RechnungFlowService.repositories;

import com.rechnung_flow.RechnungFlowService.model.enteties.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
}
