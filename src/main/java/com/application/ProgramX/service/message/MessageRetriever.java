package com.application.ProgramX.service.message;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public  class MessageRetriever {
    private Message message= new EnglishMessage();
}
