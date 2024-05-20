package com.application.ProgramX.service.responses.dialogs;

public class ErrorDialogue implements IDialogue{

    private String errorMessage;

    public ErrorDialogue(String errorMessage){
        this.errorMessage= errorMessage;
    }

    @Override
    public void executeDialogue() {
        //TODO
    }
}
