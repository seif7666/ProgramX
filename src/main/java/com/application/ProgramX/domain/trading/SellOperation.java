package com.application.ProgramX.domain.trading;

import com.application.ProgramX.domain.trading.TradeOperation;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@DiscriminatorValue("SELL")
public class SellOperation extends TradeOperation {
    @Override
    public boolean isSellingOperation() {
        return true;
    }
}
