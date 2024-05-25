package com.application.ProgramX.service.message;

import com.application.ProgramX.service.message.english.EnglishMessage;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public  class MessageRetriever {
    private Message message= new EnglishMessage();
}
