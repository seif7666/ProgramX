package com.application.ProgramX.model.entities.trading;

import com.application.ProgramX.model.entities.SupplyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TradedSupply")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradedSupplyEntity {
    @EmbeddedId
    EmbeddedTradedSupply embeddedTradedSupply=new EmbeddedTradedSupply();
    Integer numberOfBags=0;
    Float quantity=0.0f;
    Float totalPrice=0.0f;

    public void setOperation(TradeOperationEntity operation){
        this.embeddedTradedSupply.setOperation(operation);
    }
    public void setSupply(SupplyEntity supplyEntity){
        this.embeddedTradedSupply.setSupplyEntity(supplyEntity);
    }



    @Embeddable
    @Builder
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class EmbeddedTradedSupply{
        @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        TradeOperationEntity operation;
        @ManyToOne(fetch = FetchType.LAZY)
        SupplyEntity supplyEntity;
    }
}
