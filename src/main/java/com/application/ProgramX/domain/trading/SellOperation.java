package com.application.ProgramX.domain.trading;

import com.application.ProgramX.domain.trading.TradeOperation;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("SELL")
public class SellOperation extends TradeOperation {
}
