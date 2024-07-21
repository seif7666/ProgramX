package com.application.ProgramX.view.components;

import com.application.ProgramX.service.apis.ServicePool;
import com.application.ProgramX.service.dtos.SupplyCategoryDTO;
import com.application.ProgramX.service.dtos.SupplyDTO;
import com.application.ProgramX.service.dtos.trading.TradedSupplyDTO;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.service.responses.ServiceResponse;
import com.application.ProgramX.service.responses.dialogs.ErrorDialogue;
import com.application.ProgramX.view.controllers.Controller;
import com.application.ProgramX.view.controllers.TradeController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.extern.java.Log;
import org.javatuples.Pair;

import java.util.List;
@Log
public class TradedSupplyListCell<T> extends ListCell<TradedSupplyDTO> {

    private TradedSupplyDTO tradedSupply;
    private  SupplyDTO supplyDTO;
    private final ServicePool servicePool;
    private final MessageRetriever retriever;
    private final TradeController controller;

    public TradedSupplyListCell(ServicePool servicePool, MessageRetriever retriever, TradeController controller) {
        super();
        this.servicePool = servicePool;
        this.retriever = retriever;
        this.controller= controller;

    }

    @Override
    protected void updateItem(TradedSupplyDTO tradedSupplyDTO, boolean b) {
        super.updateItem(tradedSupplyDTO, b);
        if (tradedSupplyDTO == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.tradedSupply= tradedSupplyDTO;
            setCell();
        }
    }

    private void setCell() {
        HBox hBox= new HBox();
        setHBoxStyle(hBox);
        ComboBox<SupplyCategoryDTO> categoryDTOComboBox=new ComboBox<SupplyCategoryDTO>();
        ComboBox<SupplyDTO> supplyDTOComboBox= new ComboBox<>();
        Label pricePerBagLabel= new Label("0.00");
        Label pricePerKiloLabel= new Label("0.00");
        setCategoryAndSupplyCombos(categoryDTOComboBox,supplyDTOComboBox,pricePerBagLabel, pricePerKiloLabel);
        hBox.getChildren().add(categoryDTOComboBox);
        hBox.getChildren().add(supplyDTOComboBox);
        TextField totalPrice= new TextField();
        TextField numberOfBagsField= new TextField();
        TextField quantityField= new TextField();
        setTextFields(totalPrice, numberOfBagsField, quantityField);
        hBox.getChildren().add(numberOfBagsField);
        hBox.getChildren().add(quantityField);
        hBox.getChildren().add(pricePerBagLabel);
        hBox.getChildren().add(pricePerKiloLabel);
        hBox.getChildren().add(totalPrice);
        setGraphic(hBox);
    }

    private void setTextFields(TextField totalPrice, TextField numberOfBagsField, TextField quantityField) {
        totalPrice.setText(0.00+"");
        Controller.addDoubleValidations(totalPrice);
        Controller.addIntegerValidations(numberOfBagsField);
        numberOfBagsField.setText("0");
        Controller.addDoubleValidations(quantityField);
        quantityField.setText("0");
        totalPrice.setOnKeyTyped(c->tradedSupply.setTotalPrice(Float.parseFloat(totalPrice.getText())));
        quantityField.setOnKeyTyped(c->{
            float number= Float.parseFloat(quantityField.getText());
            ServiceResponse response= this.servicePool.getTradedSupplyService().setQuantity(new Pair<>(tradedSupply,supplyDTO),number);
            response.runDialogue();
            if(response.isExecutedSuccessfully())
                this.updatePriceLabel(totalPrice);
            else
                quantityField.setText(tradedSupply.getQuantity()+"");
        });
        numberOfBagsField.setOnKeyTyped(c->{
            int number= Integer.parseInt(numberOfBagsField.getText());
            ServiceResponse response= this.servicePool.getTradedSupplyService().setNumberOfBags(new Pair<>(tradedSupply,supplyDTO),number);
            response.runDialogue();
            if(response.isExecutedSuccessfully())
                this.updatePriceLabel(totalPrice);
            else {
                numberOfBagsField.setText(tradedSupply.getNumberOfBags() + "");
            }
        });

    }

    private void setCategoryAndSupplyCombos(ComboBox<SupplyCategoryDTO> categoryDTOComboBox, ComboBox<SupplyDTO> supplyDTOComboBox, Label pricePerBagLabel, Label pricePerKiloLabel) {
        categoryDTOComboBox.setPromptText("Pick a Category");
        supplyDTOComboBox.setPromptText("Pick a Supply");
        setCategories(categoryDTOComboBox);
        supplyDTOComboBox.setConverter(new StringConverter<SupplyDTO>() {
            @Override
            public String toString(SupplyDTO supplyDTO) {
                return supplyDTO==null?null:supplyDTO.getSupplyName();
            }

            @Override
            public SupplyDTO fromString(String s) {
                return null;
            }
        });
        setEventHandlerForComboBoxes(categoryDTOComboBox,supplyDTOComboBox);
        setEventHandlerForSupply(supplyDTOComboBox,pricePerBagLabel,pricePerKiloLabel);
    }

    private void setEventHandlerForSupply(ComboBox<SupplyDTO> supplyDTOComboBox, Label pricePerBagLabel, Label pricePerKiloLabel) {
        Callback<SupplyDTO,?> callback= (Callback<SupplyDTO, Object>) supply -> {
            tradedSupply.setSupplyEntity(supply.clone());

            supplyDTO= supply;
            return null;
        };
        supplyDTOComboBox.setOnAction(new LabelSetter(supplyDTOComboBox,pricePerBagLabel,pricePerKiloLabel,callback,this));
    }

    private void setEventHandlerForComboBoxes(ComboBox<SupplyCategoryDTO> categoryDTOComboBox, ComboBox<SupplyDTO> supplyDTOComboBox) {
        categoryDTOComboBox.setOnAction(new SupplySetter(this.servicePool,categoryDTOComboBox,supplyDTOComboBox));
    }

    private void setCategories(ComboBox<SupplyCategoryDTO> categoryDTOComboBox) {
        categoryDTOComboBox.setConverter(new StringConverter<SupplyCategoryDTO>() {
            @Override
            public String toString(SupplyCategoryDTO categoryDTO) {
                return categoryDTO!=null?categoryDTO.getCategoryName(): null;
            }

            @Override
            public SupplyCategoryDTO fromString(String s) {
                return null;
            }
        });
        List<SupplyCategoryDTO>categories= this.servicePool.getCategoryService().getCategories();
        for(SupplyCategoryDTO dto : categories) {
            categoryDTOComboBox.getItems().add(dto);
        }
    }


    private void setHBoxStyle(HBox hBox) {
        hBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        hBox.setSpacing(50);
    }

    private void updatePriceLabel(TextField field){
        double total= this.tradedSupply.getTotalPrice();
        log.info("Text is "+total);
        field.setText(total+"");
    }

    private boolean supplyAlreadyThere(SupplyDTO dto) {
        log.info("Callled");
        for(TradedSupplyDTO i : this.controller.getTradedSupplies()) {
            System.out.println(i);
            if (i.getSupplyEntity() != null && i.getSupplyEntity().equals(dto))
                return true;
        }
        return false;
    }

    private record SupplySetter  (ServicePool servicePool,ComboBox<SupplyCategoryDTO> categoryDTOComboBox, ComboBox<SupplyDTO> supplyDTOComboBox) implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            SupplyCategoryDTO selectedCategory= categoryDTOComboBox.getValue();
            List<SupplyDTO> supplies= servicePool.getSupplyService().getSuppliesByCategory(selectedCategory);
            while(!supplyDTOComboBox.getItems().isEmpty())
                supplyDTOComboBox.getItems().removeFirst();
            for (SupplyDTO dto : supplies)
                supplyDTOComboBox.getItems().add(dto);
        }
    }
    private record LabelSetter(ComboBox<SupplyDTO> supplyDTOComboBox, Label pricePerBagLabel,
                               Label pricePerKiloLabel, Callback<SupplyDTO,?> callback, TradedSupplyListCell<?> controller) implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            SupplyDTO dto= supplyDTOComboBox.getValue();
            if(dto==null)
                return;
            if(controller.supplyAlreadyThere(dto)){
                new ErrorDialogue(controller.retriever.getMessage().getTradedSupplyMessage().supplyAlreadyChosenBefore()).executeDialogue();
                supplyDTOComboBox().getSelectionModel().clearSelection();
                return;
            }
            callback.call(dto);
            pricePerBagLabel.setText(dto.getPricePerBag()+"$");
            pricePerKiloLabel.setText(dto.getPricePerKilo()+"$");
        }


    }
}
