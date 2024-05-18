package com.application.ProgramX.domain.trading;

import com.application.ProgramX.domain.DomainHelper;
import com.application.ProgramX.domain.Supply;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradedSupply {
    @EmbeddedId
    EmbeddedTradedSupply embeddedTradedSupply;
    Integer numberOfBags;
    Float quantity;
    Float totalPrice;

    public void setNumberOfBags(Integer numberOfBags) {
        DomainHelper.checkNotLessThan0AndThrowErrorInCase("NumberOfBags",numberOfBags);
        this.numberOfBags = numberOfBags;
    }

    public void setQuantity(Float quantity) {
        DomainHelper.checkNotLessThan0AndThrowErrorInCase("Quantity",quantity);
        this.quantity = quantity;
    }

    public void setTotalPrice(Float totalPrice) {
        DomainHelper.checkNotLessThan0AndThrowErrorInCase("TotalPrice",totalPrice);
        this.totalPrice = totalPrice;
    }

    @Embeddable
    static class EmbeddedTradedSupply{
        @ManyToOne(fetch = FetchType.EAGER)
        TradeOperation operation;
        @OneToOne(fetch = FetchType.LAZY)
        Supply supply;
    }
}
