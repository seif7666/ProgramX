package com.application.ProgramX.service.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Message {
    private CategoryMessage categoryMessage;
    private SupplyMessage supplyMessage;
}
