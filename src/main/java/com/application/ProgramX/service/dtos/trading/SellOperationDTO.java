package com.application.ProgramX.service.dtos.trading;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;


public class SellOperationDTO extends TradeOperationDTO {
    @Override
    public boolean isSellingOperation() {
        return true;
    }
}
