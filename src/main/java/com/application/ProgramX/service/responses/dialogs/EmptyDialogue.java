package com.application.ProgramX.service.responses.dialogs;

public class EmptyDialogue implements  IDialogue{

    @Override
    public boolean executeDialogue() {
        return true;
    }

    @Override
    public void executeImmediate() {
    }
}
