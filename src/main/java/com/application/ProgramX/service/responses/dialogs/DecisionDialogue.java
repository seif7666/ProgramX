package com.application.ProgramX.service.responses.dialogs;

import com.application.ProgramX.service.responses.commands.DecisionCommand;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DecisionDialogue implements IDialogue {

    private DecisionCommand command;
    private String message;
    private String acceptanceMessage;

    public DecisionDialogue(DecisionCommand command, String message, String acceptanceMessage) {
        this.command = command;
        this.message = message;
        this.acceptanceMessage = acceptanceMessage;
    }

    /**
     * Run Dialogue and execute the command based on decision.
     */
    @Override
    public boolean executeDialogue() {
        boolean userAccepted = this.openDialogueAndGetUserMessage();
        if (userAccepted) {
            this.command.executeOnAccept();
            this.executeOnAcceptanceDialogue();
            return true;
        } else
            this.command.executeOnDecline();
        return false;
    }

    private void executeOnAcceptanceDialogue() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,this.acceptanceMessage);
        alert.showAndWait();
    }

    /**
     * Opens dialogue and returns user's input
     *
     * @return true if accepted.
     */
    private boolean openDialogueAndGetUserMessage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, this.message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }


    @Override
    public void executeImmediate() {
        this.command.executeOnAccept();
    }
}
