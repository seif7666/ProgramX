package com.application.ProgramX.service.responses.dialogs;

import com.application.ProgramX.service.responses.commands.DecisionCommand;

public class DecisionDialogue implements IDialogue{

    private DecisionCommand command;
    private String message;

    public DecisionDialogue(DecisionCommand command, String message){
        this.command= command;
        this.message= message;
    }

    /**
     * Run Dialogue and execute the command based on decision.
     */
    @Override
    public void executeDialogue(){
        //TODO
        boolean userAccepted= this.openDialogueAndGetUserMessage();
        if(userAccepted)
            this.command.executeOnAccept();
        else
            this.command.executeOnDecline();
    }

    /**
     * Opens dialogue and returns user's input
     * @return true if accepted.
     */
    private boolean openDialogueAndGetUserMessage(){
        //TODO
        return false;
    }
}
