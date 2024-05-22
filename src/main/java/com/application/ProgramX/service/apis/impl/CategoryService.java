package com.application.ProgramX.service.apis.impl;

import com.application.ProgramX.model.entities.SupplyCategoryEntity;
import com.application.ProgramX.model.repository.CategoryRepository;
import com.application.ProgramX.service.apis.ICategoryService;
import com.application.ProgramX.service.dtos.SupplyCategoryDTO;
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
import java.util.function.Consumer;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository repository;
    private final MessageRetriever messageRetriever;
    private HashMap<Long,SupplyCategoryDTO> dtos;
    @Autowired
    public CategoryService(CategoryRepository repository, MessageRetriever messageRetriever) {
        this.repository = repository;
        this.messageRetriever = messageRetriever;
    }

    @Override
    public ServiceResponse create(SupplyCategoryDTO supplyCategory) {
        IDialogue dialogue;
        if (this.repository.getNumberOfCategoriesWithName(supplyCategory.getCategoryName()) != 0) {
            dialogue = new ErrorDialogue(messageRetriever.getMessage().categoryWithNameAlreadyExists(supplyCategory.getCategoryName()));
        } else {
            SupplyCategoryEntity categoryEntity = SupplyCategoryEntity.builder().CategoryName(supplyCategory.getCategoryName()).build();
            dialogue = new DecisionDialogue(new CreateCategoryCommand(categoryEntity, this.repository,dtos), this.messageRetriever.getMessage().sureToCreateCategory(), this.messageRetriever.getMessage().categoryCreatedSuccessfully());
        }
        return new ServiceResponse(dialogue);
    }

    @Override
    public ServiceResponse update(SupplyCategoryDTO supplyCategory) {
        IDialogue dialogue;
        if (this.repository.getNumberOfCategoriesWithName(supplyCategory.getCategoryName()) != 0) {
            dialogue = new ErrorDialogue(messageRetriever.getMessage().categoryWithNameAlreadyExists(supplyCategory.getCategoryName()));
        } else {
            SupplyCategoryEntity categoryEntity = SupplyCategoryEntity.builder().CategoryName(supplyCategory.getCategoryName()).CategoryID(supplyCategory.getCategoryID()).build();
            dialogue = new DecisionDialogue(new CreateCategoryCommand(categoryEntity, this.repository, dtos), this.messageRetriever.getMessage().sureToUpdateCategory(), this.messageRetriever.getMessage().categoryUpdatedSuccessfully());
        }
        return new ServiceResponse(dialogue);
    }

    @Override
    public ServiceResponse delete(SupplyCategoryDTO supplyCategoryDTO) {
        IDialogue dialogue = new DecisionDialogue(new DeleteCategoryCommand(supplyCategoryDTO.getCategoryID(), repository,dtos), this.messageRetriever.getMessage().sureToDeleteCategory(),this.messageRetriever.getMessage().categoryDeleted());
        return new ServiceResponse(dialogue);
    }


    @Override
    public List<SupplyCategoryDTO> getCategories() {
        if (dtos != null)
            return dtos.values().stream().toList();
        Iterable<SupplyCategoryEntity> entities = repository.findAll();
        dtos = new HashMap<>();
        entities.forEach(new Consumer<SupplyCategoryEntity>() {
            @Override
            public void accept(SupplyCategoryEntity supplyCategoryEntity) {
                dtos.put(supplyCategoryEntity.getCategoryID(),SupplyCategoryDTO.builder().CategoryID(supplyCategoryEntity.getCategoryID()).CategoryName(supplyCategoryEntity.getCategoryName()).build());
            }
        });
        return dtos.values().stream().toList();
    }

    private record CreateCategoryCommand(SupplyCategoryEntity entity,
                                         CategoryRepository categoryRepository, HashMap<Long,SupplyCategoryDTO> dtos) implements DecisionCommand {

        @Override
        public void executeOnAccept() {
            this.categoryRepository.save(entity);
            dtos.put(
                    entity.getCategoryID(),
                    SupplyCategoryDTO.builder().CategoryID(entity.getCategoryID()).CategoryName(entity.getCategoryName()).build()
                    );
        }

        @Override
        public void executeOnDecline() {
            //Do Nothing.
        }
    }

    private record DeleteCategoryCommand(Long id, CategoryRepository repository, HashMap<Long,SupplyCategoryDTO>dtos) implements DecisionCommand {
        @Override
        public void executeOnAccept() {
            this.repository.deleteById(id);
            dtos.remove(id);
        }

        @Override
        public void executeOnDecline() {
            //Do Nothing.
        }
    }

}
