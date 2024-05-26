package com.application.ProgramX.view.controllers;

import com.application.ProgramX.service.apis.ServicePool;
import com.application.ProgramX.service.dtos.SupplyCategoryDTO;
import com.application.ProgramX.service.dtos.SupplyDTO;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.service.responses.ServiceResponse;
import com.application.ProgramX.service.responses.dialogs.DecisionDialogue;
import com.application.ProgramX.service.responses.dialogs.ErrorDialogue;
import com.application.ProgramX.service.responses.dialogs.IDialogue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.List;

public class SupplyDetailsController extends Controller {
    private SupplyDTO supplyDTO;
    @FXML
    public Label TitleLabel;
    @FXML
    public TextField SupplyNameTextField;
    @FXML
    public ComboBox<SupplyCategoryDTO> CategoryTextField;
    @FXML
    public TextField NumberOfBagsTextField;
    @FXML
    public TextField QuantityTextField;
    @FXML
    public TextField PricePerBagTextField;
    @FXML
    public TextField PricePerKgTextField;

    public SupplyDetailsController(ServicePool servicePool, MessageRetriever retriever, SupplyDTO supplyDTO) {
        super(servicePool,retriever);
        this.supplyDTO= supplyDTO;
    }

    public void initialize() {
        this.TitleLabel.setText("Supply Details");
        setFields();
        setConstraintsOnTextFields();
    }

    private void setConstraintsOnTextFields() {
        super.addIntegerValidations(NumberOfBagsTextField);
        super.addDoubleValidations(QuantityTextField);
        super.addDoubleValidations(PricePerBagTextField);
        super.addDoubleValidations(PricePerKgTextField);
    }

    private void setFields() {
        this.SupplyNameTextField.setText(this.supplyDTO.getSupplyName());
        this.NumberOfBagsTextField.setText(this.supplyDTO.getNumberOfBags()+"");
        this.QuantityTextField.setText(this.supplyDTO.getQuantity()+"");
        this.PricePerBagTextField.setText(this.supplyDTO.getPricePerBag()+"");
        this.PricePerKgTextField.setText(this.supplyDTO.getPricePerKilo()+"");

        List<SupplyCategoryDTO> categories= this.servicePool.getSupplyService().getCategories();
        for (SupplyCategoryDTO dto : categories)
                this.CategoryTextField.getItems().add(dto);
        this.CategoryTextField.setConverter(new StringConverter<SupplyCategoryDTO>() {
            @Override
            public String toString(SupplyCategoryDTO supplyCategoryDTO) {
                return supplyCategoryDTO.getCategoryName();
            }

            @Override
            public SupplyCategoryDTO fromString(String s) {
                return null;
            }
        });
        this.CategoryTextField.getSelectionModel().select(this.supplyDTO.getSupplyCategory());

    }

    public void restore(ActionEvent actionEvent) {
        setFields();
    }

    public void openBag(ActionEvent actionEvent) {
        ServiceResponse res=this.servicePool.getSupplyService().openBag(this.supplyDTO);
        res.runDialogue();
        if(res.isExecutedSuccessfully())
            setFields();
    }

    public void updateSupply(ActionEvent actionEvent) {
        try{
            checkValidation();
            SupplyDTO newDTO= SupplyDTO.builder()
                    .supplyID(this.supplyDTO.getSupplyID())
                    .supplyName(this.SupplyNameTextField.getText())
                    .numberOfBags(Integer.parseInt(this.NumberOfBagsTextField.getText()))
                    .quantity(Float.parseFloat(this.QuantityTextField.getText()))
                    .pricePerBag(Float.parseFloat(this.PricePerBagTextField.getText()))
                    .pricePerKilo(Float.parseFloat(this.PricePerKgTextField.getText()))
                    .supplyCategory(this.CategoryTextField.getValue())
                    .build();
            if(newDTO.equals(this.supplyDTO))
                throw new RuntimeException(this.retriever.getMessage().getSupplyMessage().nothingToUpdate());
            ServiceResponse response=this.servicePool.getSupplyService().update(newDTO);
            response.runDialogue();
            if(response.isExecutedSuccessfully()){
                this.supplyDTO= newDTO;
                setFields();
            }
        }catch (Exception e){
            new ErrorDialogue(e.getMessage()).executeDialogue();
        }
    }

    private void checkValidation() {
        String[] values= new String[]{SupplyNameTextField.getText(),NumberOfBagsTextField.getText(),QuantityTextField.getText(),PricePerBagTextField.getText(),PricePerKgTextField.getText() };
        for(String val: values){
            if (val.isEmpty() || val.trim().isEmpty())
                throw new RuntimeException(this.retriever.getMessage().getSupplyMessage().someFieldsAreEmpty());
        }
    }

    public void deleteSupply(ActionEvent actionEvent) {
        ServiceResponse response=this.servicePool.getSupplyService().delete(this.supplyDTO);
        response.runDialogue();
        if(response.isExecutedSuccessfully())
            switchToSuppliesWindow(actionEvent);
    }

    public void switchToSuppliesWindow(ActionEvent actionEvent)  {
        Controller.switchWindow(SUPPLIES_FXML,new SupplyController(this.servicePool,this.retriever),actionEvent);
    }
}
