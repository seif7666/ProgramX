package com.application.ProgramX.service.apis.impl;

import com.application.ProgramX.model.entities.SupplyCategoryEntity;
import com.application.ProgramX.model.entities.SupplyEntity;
import com.application.ProgramX.model.repository.CategoryRepository;
import com.application.ProgramX.model.repository.SupplyRepository;
import com.application.ProgramX.service.apis.ISupplyService;
import com.application.ProgramX.service.dtos.SupplyCategoryDTO;
import com.application.ProgramX.service.dtos.SupplyDTO;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.service.responses.ServiceResponse;
import com.application.ProgramX.service.responses.commands.DecisionCommand;
import com.application.ProgramX.service.responses.dialogs.DecisionDialogue;
import com.application.ProgramX.service.responses.dialogs.ErrorDialogue;
import com.application.ProgramX.service.responses.dialogs.IDialogue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class SupplyService implements ISupplyService {


    private final SupplyRepository supplyRepository;
    private final CategoryRepository categoryRepository;
    private final MessageRetriever messageRetriever;

    private HashMap<Long,SupplyDTO> suppliesDTOs;

    @Autowired
    public SupplyService(SupplyRepository supplyRepository, CategoryRepository categoryRepository, MessageRetriever messageRetriever) {
        this.supplyRepository = supplyRepository;
        this.categoryRepository = categoryRepository;
        this.messageRetriever = messageRetriever;
    }
    @Override
    public ServiceResponse create(SupplyDTO supply) {
        IDialogue dialogue;
        if(!this.isSupplyNameUnique(supply.getSupplyName())){
            dialogue= new ErrorDialogue(this.messageRetriever.getMessage().getSupplyMessage().supplyNameIsNotUnique(supply.getSupplyName()));
        }
        else{
            SupplyEntity entity = mapSupplyDTOToEntity(supply);
            DecisionCommand command= new CreateSupplyCommand(entity,this.supplyRepository, this.suppliesDTOs);
            dialogue=new DecisionDialogue(command,this.messageRetriever.getMessage().getSupplyMessage().sureYouWantToCreateSupply(),this.messageRetriever.getMessage().getSupplyMessage().createdSuccessfully());
        }
        return new ServiceResponse(dialogue);
    }



    @Override
    public ServiceResponse update(SupplyDTO supply) {
        IDialogue dialogue;
        if(!this.isSupplyNameUnique(supply.getSupplyName()) && !supply.getSupplyName().equals(suppliesDTOs.get(supply.getSupplyID()).getSupplyName())){
            dialogue= new ErrorDialogue(this.messageRetriever.getMessage().getSupplyMessage().supplyNameIsNotUnique(supply.getSupplyName()));
        }
        else{
            SupplyEntity entity = mapSupplyDTOToEntity(supply);
            DecisionCommand command= new CreateSupplyCommand(entity,this.supplyRepository,this.suppliesDTOs);
            dialogue=new DecisionDialogue(command,this.messageRetriever.getMessage().getSupplyMessage().sureYouWantToUpdateSupply(),this.messageRetriever.getMessage().getSupplyMessage().updateSuccessful());
        }
        return new ServiceResponse(dialogue);
    }

    @Override
    public ServiceResponse delete(SupplyDTO supply) {
        SupplyEntity entity= mapSupplyDTOToEntity(supply);
        DecisionCommand command= new DecisionCommand() {
            @Override
            public void executeOnAccept() {
                supplyRepository.delete(entity);
                suppliesDTOs.remove(entity.getSupplyID());
            }
            @Override
            public void executeOnDecline() {
                //Do Nothing!
            }
        };
        IDialogue dialogue= new DecisionDialogue(command,messageRetriever.getMessage().getSupplyMessage().sureYouWantToDelete(),messageRetriever.getMessage().getSupplyMessage().supplyDeleted());
        return new ServiceResponse(dialogue);
    }

    @Override
    public List<SupplyDTO> getSupplies() {
        if(this.suppliesDTOs!=null)
                return suppliesDTOs.values().stream().toList();
        this.suppliesDTOs= new HashMap<>();
        for (SupplyEntity entity : this.supplyRepository.findAll()) {
            SupplyDTO dto = mapEntityToDTO(entity);
            this.suppliesDTOs.put(dto.getSupplyID(), dto);
        }
        return this.suppliesDTOs.values().stream().toList();
    }
    @Override
    public List<SupplyCategoryDTO> getCategories() {
        List<SupplyCategoryDTO> categories= new LinkedList<>();
        for (SupplyCategoryEntity entity : this.categoryRepository.findAll()) {
            SupplyCategoryDTO dto = SupplyCategoryDTO.builder().CategoryID(entity.getCategoryID()).CategoryName(entity.getCategoryName()).build();
            categories.add(dto);
        }
        return categories;
    }

    @Override
    public List<SupplyDTO> getSuppliesByCategory(SupplyCategoryDTO categoryDTO) {
        List<SupplyDTO> supplyDTOS= new LinkedList<>();
        Iterable<SupplyEntity>entities=this.supplyRepository.getSuppliesByCategory(categoryDTO.getCategoryID()) ;
        for(SupplyEntity entity: entities)
            supplyDTOS.add(mapEntityToDTO(entity));
        return supplyDTOS;
    }

    @Override
    public ServiceResponse openBag(SupplyDTO supplyDTO) {
        IDialogue dialogue;
        if(supplyDTO.getNumberOfBags() == 0) //empty!
            dialogue= new ErrorDialogue(this.messageRetriever.getMessage().getSupplyMessage().noBagsToOpen());
        else{
            DecisionCommand command= new DecisionCommand() {
                @Override
                public void executeOnAccept() {
                    supplyDTO.setNumberOfBags(supplyDTO.getNumberOfBags()-1);
                    supplyDTO.setQuantity(supplyDTO.getQuantity()+25);
                    SupplyEntity entity= mapSupplyDTOToEntity(supplyDTO);
                    supplyRepository.save(entity);
                    suppliesDTOs.put(supplyDTO.getSupplyID(),supplyDTO);
                }
                @Override
                public void executeOnDecline() {

                }
            };
            dialogue=new DecisionDialogue(command,this.messageRetriever.getMessage().getSupplyMessage().sureYouWantToOpenBag(),this.messageRetriever.getMessage().getSupplyMessage().newBagwasOpened());
        }
        return new ServiceResponse(dialogue);
    }

    private boolean isSupplyNameUnique(String supplyName){
        return this.supplyRepository.getNumberOfSuppliesHavingSameName(supplyName)==0;
    }

    private static SupplyEntity mapSupplyDTOToEntity(SupplyDTO supply) {
        SupplyCategoryEntity categoryEntity= SupplyCategoryEntity.builder().CategoryName(supply.getSupplyCategory().getCategoryName()).CategoryID(supply.getSupplyCategory().getCategoryID()).build();

        return SupplyEntity.builder()
                .SupplyID(supply.getSupplyID())
                .SupplyName(supply.getSupplyName())
                .supplyCategory(categoryEntity)
                .pricePerBag(supply.getPricePerBag())
                .pricePerKilo(supply.getPricePerKilo())
                .numberOfBags(supply.getNumberOfBags())
                .quantity(supply.getQuantity())
                .build();
    }
    private static SupplyDTO mapEntityToDTO(SupplyEntity entity) {
        SupplyCategoryDTO categoryDTO= SupplyCategoryDTO.builder()
                .CategoryID(entity.getSupplyCategory().getCategoryID())
                .CategoryName(entity.getSupplyCategory().getCategoryName())
                .build();
        return SupplyDTO.builder()
                .supplyID(entity.getSupplyID())
                .supplyName(entity.getSupplyName())
                .supplyCategory(categoryDTO)
                .numberOfBags(entity.getNumberOfBags())
                .quantity(entity.getQuantity())
                .pricePerBag(entity.getPricePerBag())
                .pricePerKilo(entity.getPricePerKilo())
                .build();
    }


    private record CreateSupplyCommand(SupplyEntity entity, SupplyRepository repository, HashMap<Long,SupplyDTO> dtos) implements DecisionCommand{

        @Override
        public void executeOnAccept() {

            this.repository.save(entity);
            SupplyDTO dto= mapEntityToDTO(entity);
            dtos.put(dto.getSupplyID(),dto);
        }

        @Override
        public void executeOnDecline() {
            //Do Nothing.
        }
    }
}
