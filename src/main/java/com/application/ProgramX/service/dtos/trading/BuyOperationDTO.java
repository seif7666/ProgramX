package com.application.ProgramX.service.dtos.trading;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;


public class BuyOperationDTO extends TradeOperationDTO {

    @Override
    public boolean isSellingOperation() {
        return false;
    }
}
