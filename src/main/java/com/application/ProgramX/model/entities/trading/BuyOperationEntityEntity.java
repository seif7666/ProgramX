package com.application.ProgramX.model.entities.trading;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("BUY")
public class BuyOperationEntityEntity extends TradeOperationEntity {

    @Override
    public boolean isSellingOperation() {
        return false;
    }
}
