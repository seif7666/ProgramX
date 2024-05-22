package com.application.ProgramX.service.message.english;

import com.application.ProgramX.service.message.Message;
import org.springframework.stereotype.Component;

@Component
public class EnglishMessage extends Message {

    public EnglishMessage(){
        super(new EnglishCategoryMessage(),new EnglishSupplyMessage());
    }
}
