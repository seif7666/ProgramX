package com.application.ProgramX.domain.trading;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("BUY")
public class BuyOperation  extends TradeOperation{
}
