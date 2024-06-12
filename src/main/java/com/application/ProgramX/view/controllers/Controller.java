package com.application.ProgramX.view.controllers;

import com.application.ProgramX.service.apis.ServicePool;
import com.application.ProgramX.service.message.MessageRetriever;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.Objects;
@Log
public abstract class Controller {
    public static final String CATEGORIES_FXML="/FXMLs/Categories.fxml";
    public static final String SUPPLIES_FXML="/FXMLs/Supplies.fxml";
    public static final String SUPPLY_DETAILS_FXML= "/FXMLs/SupplyDetails.fxml";
    private static final String TRADE_FXML = "/FXMLs/Trade.fxml";
    protected final MessageRetriever retriever;
    protected final ServicePool servicePool;

    protected Controller(ServicePool servicePool, MessageRetriever retriever){
        this.servicePool= servicePool;
        this.retriever= retriever;
    }


    public static void switchWindow(String fxmlPath, Controller controller, Event actionEvent) {
        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(Controller.class.getResource(fxmlPath)));
        loader.setControllerFactory(c -> controller);
        VBox vbox= loader.<VBox>load();
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene= new Scene(vbox);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addIntegerValidations(TextField field){
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            adjustValidInteger(field,oldValue,newValue);
        });
    }
    public static void addDoubleValidations(TextField field){
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            adjustValidDouble(field,oldValue,newValue);
        });
    }

    public static void adjustValidDouble(TextField observable, String oldValue, String newValue){
        try{
            if(newValue.isEmpty())
                return;
            Double.parseDouble(newValue);
        }catch (Exception e){
            observable.setText(oldValue);
        }
    }

    public static void adjustValidInteger(TextField observable, String oldValue, String newValue){
        try{
            if(newValue.isEmpty())
                return;
            Integer.parseInt(newValue);
        }catch (Exception e){
            observable.setText(oldValue);
        }
    }

    public void closeWindow(ActionEvent actionEvent){

    }
    public void openCategoriesWindow(ActionEvent action){
        switchWindow(CATEGORIES_FXML,new CategoryController(this.retriever, this.servicePool),action);
    }
    public void openSuppliesWindow(ActionEvent action){
        switchWindow(Controller.SUPPLIES_FXML,new SupplyController(this.servicePool, this.retriever),action);
    }
    public void openTradeWindowWindow(ActionEvent action){
        switchWindow(Controller.TRADE_FXML,new TradeController(this.servicePool, this.retriever),action);
    }

}
