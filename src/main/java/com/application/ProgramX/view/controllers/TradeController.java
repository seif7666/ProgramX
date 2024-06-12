package com.application.ProgramX.view.controllers;

import com.application.ProgramX.service.apis.ServicePool;
import com.application.ProgramX.service.dtos.trading.TradedSupplyDTO;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.service.responses.dialogs.ErrorDialogue;
import com.application.ProgramX.view.components.TradedSupplyListCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import lombok.extern.java.Log;


@Log
public class TradeController extends Controller {

    @FXML
    public ListView<TradedSupplyDTO> ListView;
    @FXML
    public HBox LabelsHBox;

    public void initialize(){
        setListStyle();
        setLabelsStyle();
    }

    private void setLabelsStyle() {
        LabelsHBox.setSpacing(80);
        Font font = new Font("Serif",18);
        for (Node i : LabelsHBox.getChildren()) {
            Label label = (Label) i;
            label.setFont(font);
            label.setPadding(new Insets(10,5,10,5));
            label.setStyle("-fx-underline: true");
        }
    }

    private void setListStyle() {
        this.ListView.setCellFactory(c -> new TradedSupplyListCell<TradedSupplyDTO>(this.servicePool,this.retriever));
    }

    protected TradeController(ServicePool servicePool, MessageRetriever retriever) {
        super(servicePool, retriever);
    }

    public void handleAddingNewCell(KeyEvent keyEvent) {
        if (!keyEvent.getText().equals("+"))
            return;
        /*if(this.isSomeCellsEmpty()){
            new ErrorDialogue(super.retriever.getMessage().getTradedSupplyMessage().cantAddNewCellWhileSomeCellsAreEmpty()).executeDialogue();
            return;
        }*/
        ListView.getItems().add(new TradedSupplyDTO());


    }
}
