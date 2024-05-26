package com.application.ProgramX.view.components;

import com.application.ProgramX.service.apis.ServicePool;
import com.application.ProgramX.service.dtos.SupplyDTO;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.view.controllers.Controller;
import com.application.ProgramX.view.controllers.SupplyDetailsController;
import com.application.ProgramX.view.styles.Constants;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;

public class SupplyListCell<T> extends ListCell<SupplyDTO> {

    private SupplyDTO supplyDTO;
    private final ServicePool servicePool;
    private final MessageRetriever retriever;

    public SupplyListCell(ServicePool servicePool, MessageRetriever retriever) {
        this.servicePool = servicePool;
        this.retriever = retriever;
    }

    public void updateItem(SupplyDTO pos, boolean empty) {
        super.updateItem(pos, empty);
        if (pos == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.supplyDTO= pos;
            setCell();
        }
    }
    private void setCell(){
        BorderPane pane = new BorderPane();
        pane.setStyle(String.format("-fx-background-color:%s", Constants.BACKGROUND_COLOR));
        HBox leftHbox= new HBox();
        Label supplyNameLabel= new Label();
        Label categoryNameLabel= new Label();
        setLabelStyle(supplyNameLabel,this.supplyDTO.getSupplyName());
        setLabelStyle(categoryNameLabel,this.supplyDTO.getSupplyCategory().getCategoryName());
        pane.setOnMouseEntered(mouseEvent -> {
            supplyNameLabel.setTextFill(Color.DARKCYAN);
            categoryNameLabel.setTextFill(Color.DARKCYAN);
        });
        pane.setOnMouseExited(mouseEvent -> {
            supplyNameLabel.setTextFill(Color.BLACK);
            categoryNameLabel.setTextFill(Color.BLACK);
        }
        );
        leftHbox.getChildren().add(categoryNameLabel);
        leftHbox.getChildren().add(supplyNameLabel);
        leftHbox.setSpacing(30);
        pane.rightProperty().set(leftHbox);
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //Add new Window.
                Controller.switchWindow(Controller.SUPPLY_DETAILS_FXML,new SupplyDetailsController(servicePool,retriever,supplyDTO),mouseEvent);
            }
        });
        setGraphic(pane);
    }


    private void setLabelStyle(Label label, String supplyName) {
        label.setText(supplyName);
        label.setFont(new Font("Arial",19.0));
        label.setPadding(new Insets(10,10,10,10));
    }
}
