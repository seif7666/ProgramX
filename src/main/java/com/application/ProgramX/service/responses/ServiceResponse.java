package com.application.ProgramX.service.responses;

import com.application.ProgramX.service.responses.dialogs.DecisionDialogue;
import com.application.ProgramX.service.responses.dialogs.IDialogue;

public class ServiceResponse {

    private final IDialogue dialogue;

    public ServiceResponse(IDialogue dialogue){
        this.dialogue= dialogue;
    }

    public void runDialogue(){
        this.dialogue.executeDialogue();
    }
}
