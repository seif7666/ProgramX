package com.application.ProgramX.service.responses.dialogs;

import javafx.scene.control.Alert;

public class ErrorDialogue implements IDialogue{

    private final String errorMessage;

    public ErrorDialogue(String errorMessage){
        this.errorMessage= errorMessage;
    }

    @Override
    public void executeDialogue() {
        //TODO
        Alert alert = new Alert(Alert.AlertType.ERROR, this.errorMessage);
        alert.showAndWait();
    }
}
