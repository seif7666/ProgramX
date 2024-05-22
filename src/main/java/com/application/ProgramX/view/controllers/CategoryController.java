package com.application.ProgramX.view.controllers;

import com.application.ProgramX.service.apis.ICategoryService;
import com.application.ProgramX.service.dtos.SupplyCategoryDTO;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.service.responses.dialogs.ErrorDialogue;
import com.application.ProgramX.view.components.CategoryListCell;
import com.application.ProgramX.view.styles.ButtonStyles;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Log
public class CategoryController implements Observer {
    @FXML
    public TextField NewCategoryTextField;
    @FXML
    public Button CategoryButton;
    @FXML
    public ListView<SupplyCategoryDTO> CategoriesListView;
    private final MessageRetriever retriever;
    private final ICategoryService service;

    public CategoryController(MessageRetriever retriever, ICategoryService service) {
        this.retriever = retriever;
        this.service = service;
    }

    public void initialize() {
        log.info("Called from Controller!");
        ButtonStyles.StyleNavigationButton(CategoryButton);
        setListStyle();
        fillCategoryList();
    }



    public void closeWindow(){

    }

    public void createCategory(ActionEvent actionEvent) {
        String categoryName= this.NewCategoryTextField.getText();
        if(categoryName.isEmpty() || categoryName.trim().isEmpty()){
            new ErrorDialogue(this.retriever.getMessage().categoryNameMustNotBeEmpty()).executeDialogue();
            log.warning("Empty Message");
            return;
        }
        SupplyCategoryDTO dto= SupplyCategoryDTO.builder().CategoryName(categoryName).build();
        service.create(dto).runDialogue();
        fillCategoryList();
    }

    private void setListStyle() {
        this.CategoriesListView.setCellFactory(c->new CategoryListCell(service, retriever,this));
    }

    private void fillCategoryList(){
        emptyList();
        List<SupplyCategoryDTO> categoryDTOList = this.service.getCategories();
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
}
