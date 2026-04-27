package com.rechnung_flow.RechnungFlowService.controller;

import com.rechnung_flow.RechnungFlowService.model.enteties.WorkLog;
import com.rechnung_flow.RechnungFlowService.service.WorkLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/worklogs")
public class WorkLogController {
    private final WorkLogService workLogService;

    public WorkLogController(WorkLogService workLogService){
        this.workLogService = workLogService;
    }

    @GetMapping
    public List<WorkLog> getAllWorkLogs(){
        return workLogService.getAllWorkLogs();
    }

    @GetMapping("/{id}")
    public Optional<WorkLog> getWorkLogById(@PathVariable Long id){
        return workLogService.getWorkLogById(id);
    }

    @PostMapping
    public WorkLog createWorkLog(@RequestBody WorkLog workLog){
        return workLogService.createWorkLog(workLog);
    }
}
