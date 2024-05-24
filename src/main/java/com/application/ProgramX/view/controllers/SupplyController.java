package com.application.ProgramX.view.controllers;

import com.application.ProgramX.service.apis.ServicePool;
import com.application.ProgramX.service.dtos.SupplyCategoryDTO;
import com.application.ProgramX.service.dtos.SupplyDTO;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.service.responses.dialogs.ErrorDialogue;
import com.application.ProgramX.view.components.SupplyListCell;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.List;

@Log
public class SupplyController extends Controller {
    @FXML
    public ListView<SupplyDTO> SuppliesListView;
    @FXML
    public ComboBox<SupplyCategoryDTO> SearchByCategoryCombo;
    @FXML
    public TextField SupplyNameTextField;
    @FXML
    public ComboBox<SupplyCategoryDTO> CreateCategoryCombo;
    @FXML
    public TextField NumberOfBagsTextField;
    @FXML
    public TextField QuantityTextField;
    @FXML
    public TextField PricePerBagTextField;
    @FXML
    public TextField PricePerKgField;

    public SupplyController(ServicePool servicePool, MessageRetriever retriever) {
        super(servicePool,retriever);
    }

    public void initialize(){
        setListStyle();
        updateList();
        List<SupplyCategoryDTO>dtos= this.servicePool.getSupplyService().getCategories();
        setByCategoryCombo(this.CreateCategoryCombo,dtos);
        setByCategoryCombo(this.SearchByCategoryCombo,dtos);
        addValidations();
    }
    private void addValidations(){
        super.addIntegerValidations(NumberOfBagsTextField);
        super.addDoubleValidations(QuantityTextField);
        super.addDoubleValidations(PricePerKgField);
        super.addDoubleValidations(PricePerBagTextField);
    }

    private void setListStyle() {
        this.SuppliesListView.setCellFactory(c->new SupplyListCell<SupplyDTO>(this.servicePool,this.retriever));
    }


    private void updateList() {
        emptyList();
        fillList(this.servicePool.getSupplyService().getSupplies());
    }



    private void setByCategoryCombo(ComboBox<SupplyCategoryDTO> combobox,List<SupplyCategoryDTO> categoryDTOList) {
        combobox.setItems(FXCollections.observableList(categoryDTOList));
        combobox.setConverter(new StringConverter<SupplyCategoryDTO>() {
            @Override
            public String toString(SupplyCategoryDTO supplyCategoryDTO) {
                return supplyCategoryDTO.toString();
            }

            @Override
            public SupplyCategoryDTO fromString(String s) {
                return null;
            }
        });


    }

    private void fillList(List<SupplyDTO> supplies) {
        for(SupplyDTO dto: supplies)
            this.SuppliesListView.getItems().add(dto);

    }
    private void emptyList() {
        List<SupplyDTO> supplies= this.SuppliesListView.getItems();
        while(!supplies.isEmpty())
            supplies.removeFirst();
    }


    public void closeWindow(ActionEvent actionEvent) {
    }

    public void createSupply(ActionEvent actionEvent) {
        try {
            checkEmptySpaceValidations();
            SupplyDTO supplyDTO= SupplyDTO.builder()
                    .supplyName(SupplyNameTextField.getText())
                    .supplyCategory(CreateCategoryCombo.getValue())
                    .numberOfBags(Integer.parseInt(NumberOfBagsTextField.getText()))
                    .quantity(Float.parseFloat(QuantityTextField.getText()))
                    .pricePerBag(Float.parseFloat(PricePerBagTextField.getText()))
                    .pricePerKilo(Float.parseFloat(PricePerKgField.getText()))
                    .build();
            this.servicePool.getSupplyService().create(supplyDTO).runDialogue();
            this.updateList();
        }catch (Exception e){
            new ErrorDialogue(e.getMessage()).executeDialogue();
        }
    }

    private void checkEmptySpaceValidations() {
        String[] values= new String[]{SupplyNameTextField.getText(),NumberOfBagsTextField.getText(),QuantityTextField.getText(),PricePerBagTextField.getText(),PricePerKgField.getText() };
        for(String val: values){
            if (val.isEmpty() || val.trim().isEmpty())
                throw new RuntimeException(this.retriever.getMessage().getSupplyMessage().someFieldsAreEmpty());
        }
        if(this.CreateCategoryCombo.getValue()== null)
            throw new RuntimeException(this.retriever.getMessage().getSupplyMessage().noCategoryWasSelected()) ;
    }

    public void openCategoriesWindow(ActionEvent actionEvent) throws IOException {
        switchWindow(CATEGORIES_FXML,new CategoryController(this.retriever, this.servicePool),actionEvent);
    }
}
