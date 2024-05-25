package com.application.ProgramX.service.responses;

import com.application.ProgramX.service.responses.dialogs.DecisionDialogue;
import com.application.ProgramX.service.responses.dialogs.IDialogue;
import lombok.Getter;

public class ServiceResponse {

    private final IDialogue dialogue;
    @Getter
    private boolean executedSuccessfully=false;

    public ServiceResponse(IDialogue dialogue){
        this.dialogue= dialogue;
    }

    public void runDialogue(){
        this.executedSuccessfully=this.dialogue.executeDialogue();
    }

    public void runImmediately(){
        this.dialogue.executeImmediate();

    }
}
