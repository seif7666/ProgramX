package com.application.ProgramX.view.controllers;

import com.application.ProgramX.service.apis.ServicePool;
import com.application.ProgramX.service.dtos.SupplyCategoryDTO;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.service.responses.dialogs.ErrorDialogue;
import com.application.ProgramX.view.components.CategoryListCell;
import com.application.ProgramX.view.styles.ButtonStyles;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.java.Log;

import java.util.LinkedList;
import java.util.List;

@Log
public class CategoryController implements Observer {
    @FXML
    public TextField NewCategoryTextField;
    @FXML
    public Button CategoryButton;
    @FXML
    public ListView<SupplyCategoryDTO> CategoriesListView;
    @FXML
    public TextField SearchTextField;
    private final MessageRetriever retriever;
    private final ServicePool servicePool;

    public CategoryController(MessageRetriever retriever, ServicePool service) {
        this.retriever = retriever;
        this.servicePool = service;
    }

    public void initialize() {
        log.info("Called from Controller!");
        ButtonStyles.StyleNavigationButton(CategoryButton);
        setListStyle();
        fillCategoryList();
    }



    public void closeWindow(){

    }

    public void createCategory() {
        String categoryName= this.NewCategoryTextField.getText();
        if(categoryName.isEmpty() || categoryName.trim().isEmpty()){
            new ErrorDialogue(this.retriever.getMessage().getCategoryMessage().categoryNameMustNotBeEmpty()).executeDialogue();
            log.warning("Empty Message");
            return;
        }
        SupplyCategoryDTO dto= SupplyCategoryDTO.builder().CategoryName(categoryName).build();
        servicePool.getCategoryService().create(dto).runDialogue();
        fillCategoryList();
    }

    private void setListStyle() {
        this.CategoriesListView.setCellFactory(c->new CategoryListCell(servicePool.getCategoryService(), retriever,this));
    }

    private void fillCategoryList(){
        emptyList();
        List<SupplyCategoryDTO> categoryDTOList = this.servicePool.getCategoryService().getCategories();
        log.info(String.format("We have %d categories!",categoryDTOList.size()));
        this.CategoriesListView.getItems().addAll(categoryDTOList);
    }

    private void emptyList() {
        while(!this.CategoriesListView.getItems().isEmpty())
            this.CategoriesListView.getItems().removeFirst();
    }


    @Override
    public void update() {
        this.fillCategoryList();
    }

    public void applyFilter() {
        fillCategoryList();
        String text= SearchTextField.getText();
        System.out.println(text);
        if(text.isEmpty() || text.trim().isEmpty()){
            //Do Nothing
        }
        else{
            List<SupplyCategoryDTO>dtos= new LinkedList<>();
            for(SupplyCategoryDTO i : this.CategoriesListView.getItems())
                    if(i.getCategoryName().toLowerCase().contains(text.toLowerCase()))
                        dtos.add(i);
            emptyList();
            this.CategoriesListView.getItems().addAll(dtos);
        }
    }
}
