package com.rechnung_flow.RechnungFlowService.controller;

import com.rechnung_flow.RechnungFlowService.model.enteties.CleaningObject;
import com.rechnung_flow.RechnungFlowService.service.CleaningObjectService;
import jakarta.persistence.GeneratedValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cleaningobjects")
public class CleaningObjectController {
    private final CleaningObjectService cleaningObjectService;

    public CleaningObjectController(CleaningObjectService cleaningObjectService){
        this.cleaningObjectService = cleaningObjectService;
    }

    @GetMapping
    public List<CleaningObject> getAllCleaningObjects(){
        return cleaningObjectService.getAllCleaningObjects();
    }

    @GetMapping("/{id}")
    public Optional<CleaningObject> getCleaningObjectById(@PathVariable Long id){
        return cleaningObjectService.getCleaningObjectById(id);
    }

    @PostMapping
    public CleaningObject createCleaningObject(@RequestBody CleaningObject cleaningObject){
        return cleaningObjectService.createCleaningObject(cleaningObject);
    }
}
