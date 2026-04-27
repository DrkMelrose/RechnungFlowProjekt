package com.rechnung_flow.RechnungFlowService.service;

import com.rechnung_flow.RechnungFlowService.model.enteties.CleaningObject;
import com.rechnung_flow.RechnungFlowService.model.enteties.WorkLog;
import com.rechnung_flow.RechnungFlowService.repositories.WorkLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkLogService {
    private final WorkLogRepository workLogRepository;

    public WorkLogService(WorkLogRepository workLogRepository){
        this.workLogRepository = workLogRepository;
    }

    public List<WorkLog> getAllWorkLogs(){
        return workLogRepository.findAll();
    }

    public Optional<WorkLog> getWorkLogById(Long id){
        return workLogRepository.findById(id);
    }

    public WorkLog createWorkLog(WorkLog workLog){
        return workLogRepository.save(workLog);
    }

    public List<WorkLog> findByObjectAndPeriod(CleaningObject object, LocalDate from, LocalDate to){
        return workLogRepository.findByObjectAndDateBetween(object, from, to);
    }
}
