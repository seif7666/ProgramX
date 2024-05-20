package com.application.ProgramX.model.entities.trading;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("SELL")
public class SellOperationEntityEntity extends TradeOperationEntity {
    @Override
    public boolean isSellingOperation() {
        return true;
    }
}
