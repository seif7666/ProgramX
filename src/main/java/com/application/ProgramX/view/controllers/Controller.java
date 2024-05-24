package com.application.ProgramX.view.controllers;

import com.application.ProgramX.service.apis.ServicePool;
import com.application.ProgramX.service.message.MessageRetriever;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class Controller {
    public static final String CATEGORIES_FXML="/FXMLs/Categories.fxml";
    protected static final String SUPPLIES_FXML="/FXMLs/Supplies.fxml";
    protected final MessageRetriever retriever;
    protected final ServicePool servicePool;

    protected Controller(ServicePool servicePool, MessageRetriever retriever){
        this.servicePool= servicePool;
        this.retriever= retriever;
    }


    protected void switchWindow(String fxmlPath, Controller controller, ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        loader.setControllerFactory(c -> controller);
        VBox vbox= loader.<VBox>load();
        Stage stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene= new Scene(vbox);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }


}
