package com.rechnung_flow.RechnungFlowService.service;

import com.rechnung_flow.RechnungFlowService.model.enteties.CleaningObject;
import com.rechnung_flow.RechnungFlowService.repositories.CleaningObjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CleaningObjectService {
    private final CleaningObjectRepository cleaningObjectRepository;

    private CleaningObjectService(CleaningObjectRepository cleaningObjectRepository){
        this.cleaningObjectRepository = cleaningObjectRepository;
    }

    public List<CleaningObject> getAllCleaningObjects(){
        return cleaningObjectRepository.findAll();
    }

    public Optional<CleaningObject> getCleaningObjectById(Long id){
        return cleaningObjectRepository.findById(id);
    }

    public CleaningObject createCleaningObject(CleaningObject cleaningObject){
        return cleaningObjectRepository.save(cleaningObject);
    }
}
