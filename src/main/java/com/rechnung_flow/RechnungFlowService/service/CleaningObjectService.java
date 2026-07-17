package com.rechnung_flow.RechnungFlowService.service;

import com.rechnung_flow.RechnungFlowService.model.enteties.CleaningObject;
import com.rechnung_flow.RechnungFlowService.model.enteties.Client;
import com.rechnung_flow.RechnungFlowService.repositories.CleaningObjectRepository;
import com.rechnung_flow.RechnungFlowService.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CleaningObjectService {
    private final CleaningObjectRepository cleaningObjectRepository;
    private final ClientRepository clientRepository;

    private CleaningObjectService(CleaningObjectRepository cleaningObjectRepository, ClientRepository clientRepository){
        this.cleaningObjectRepository = cleaningObjectRepository;
        this.clientRepository = clientRepository;
    }

    public CleaningObject createCleaningObject(CleaningObject cleaningObject){
        System.out.println("CleaningObject: " + cleaningObject);
        System.out.println("Client: " + cleaningObject.getClient());

        if (cleaningObject.getClient() != null){
            System.out.println(
                    "Client ID: " + cleaningObject.getClient().getId()
            );
        }

        Long clientId = cleaningObject.getClient().getId();

        Client client = clientRepository.findById(clientId)
                .orElseThrow(()-> new RuntimeException("Client not found"));

        cleaningObject.setClient(client);

        return cleaningObjectRepository.save(cleaningObject);
    }

    public List<CleaningObject> getAllCleaningObjects(){
        return cleaningObjectRepository.findAll();
    }

    public Optional<CleaningObject> getCleaningObjectById(Long id){
        return cleaningObjectRepository.findById(id);
    }


    public void deleteCleaningObject(Long id){
        cleaningObjectRepository.deleteById(id);
    }

    public CleaningObject updateCleaningObject(Long id, CleaningObject updatedCleaningObject){
        CleaningObject cleaningObject = cleaningObjectRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Client object not found"));

        cleaningObject.setClient(updatedCleaningObject.getClient());
        cleaningObject.setName(updatedCleaningObject.getName());
        cleaningObject.setAddress(updatedCleaningObject.getAddress());
        cleaningObject.setHourlyRate(updatedCleaningObject.getHourlyRate());
        cleaningObject.setFixedMonthlyPrice(updatedCleaningObject.getFixedMonthlyPrice());
        cleaningObject.setActive(updatedCleaningObject.getActive());

        return cleaningObjectRepository.save(cleaningObject);
    }
}
