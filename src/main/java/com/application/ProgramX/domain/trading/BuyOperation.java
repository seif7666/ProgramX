package com.application.ProgramX.domain.trading;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@DiscriminatorValue("BUY")
public class BuyOperation  extends TradeOperation{

    @Override
    public boolean isSellingOperation() {
        return false;
    }
}
