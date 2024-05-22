package com.application.ProgramX.view.controllers;

import com.application.ProgramX.service.apis.ICategoryService;
import com.application.ProgramX.service.dtos.SupplyCategoryDTO;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.service.responses.dialogs.ErrorDialogue;
import com.application.ProgramX.view.styles.ButtonStyles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
@Log
public class CategoryController {
    @FXML
    public TextField NewCategoryTextField;
    @FXML
    Button CategoryButton;
    private final MessageRetriever retriever;
    private final ICategoryService service;

    public CategoryController(MessageRetriever retriever, ICategoryService service) {
        this.retriever = retriever;
        this.service = service;
    }

    public void initialize() {
        log.info("Called from Controller!");
        ButtonStyles.StyleNavigationButton(CategoryButton);
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
    }
}
