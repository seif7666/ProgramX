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

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository repository;
    private final MessageRetriever messageRetriever;
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
            dialogue = new DecisionDialogue(new CreateCategoryCommand(categoryEntity, this.repository), this.messageRetriever.getMessage().sureToCreateCategory(), this.messageRetriever.getMessage().categoryCreatedSuccessfully());
        }
        return new ServiceResponse(dialogue);
    }

    @Override
    public ServiceResponse update(SupplyCategoryDTO supplyCategory) {
        IDialogue dialogue;
        if (this.repository.getNumberOfCategoriesWithName(supplyCategory.getCategoryName()) != 0) {
            dialogue = new ErrorDialogue(messageRetriever.getMessage().categoryWithNameAlreadyExists(supplyCategory.getCategoryName()));
        } else {
            SupplyCategoryEntity categoryEntity = SupplyCategoryEntity.builder().CategoryName(supplyCategory.getCategoryName()).build();
            dialogue = new DecisionDialogue(new CreateCategoryCommand(categoryEntity, this.repository), this.messageRetriever.getMessage().sureToUpdateCategory(), this.messageRetriever.getMessage().categoryUpdatedSuccessfully());
        }
        return new ServiceResponse(dialogue);
    }

    @Override
    public ServiceResponse delete(SupplyCategoryDTO supplyCategoryDTO) {
        IDialogue dialogue = new DecisionDialogue(new DeleteCategoryCommand(supplyCategoryDTO.getCategoryID(), repository), this.messageRetriever.getMessage().sureToDeleteCategory(),this.messageRetriever.getMessage().categoryDeleted());
        return new ServiceResponse(dialogue);
    }


    @Override
    public List<SupplyCategoryDTO> getCategories() {
        Iterable<SupplyCategoryEntity> entities = repository.findAll();
        List<SupplyCategoryDTO> dtos = new LinkedList<>();
        entities.forEach(new Consumer<SupplyCategoryEntity>() {
            @Override
            public void accept(SupplyCategoryEntity supplyCategoryEntity) {
                dtos.add(SupplyCategoryDTO.builder().CategoryID(supplyCategoryEntity.getCategoryID()).CategoryName(supplyCategoryEntity.getCategoryName()).build());
            }
        });
        return dtos;
    }

    private record CreateCategoryCommand(SupplyCategoryEntity entity,
                                         CategoryRepository categoryRepository) implements DecisionCommand {

        @Override
        public void executeOnAccept() {
            this.categoryRepository.save(entity);
        }

        @Override
        public void executeOnDecline() {
            //Do Nothing.
        }
    }

    private record DeleteCategoryCommand(Long id, CategoryRepository repository) implements DecisionCommand {
        @Override
        public void executeOnAccept() {
            this.repository.deleteById(id);

        }

        @Override
        public void executeOnDecline() {
            //Do Nothing.
        }
    }

}
