package com.application.ProgramX.service.dtos.trading;

import com.application.ProgramX.service.dtos.SupplyDTO;
import com.application.ProgramX.service.exceptions.LessThanZeroException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradedSupplyDTO {
    TradeOperationDTO operation;
    SupplyDTO supplyEntity;
    Integer numberOfBags=0;
    Float quantity=0.0f;
    Float totalPrice=0.0f;

    private boolean newBagOpened=false;

    public void setNumberOfBags(Integer numberOfBags) {
        LessThanZeroException.throwIfLessThan0("Number of bags",numberOfBags);
        this.numberOfBags = numberOfBags;
        setTotalPrice();
    }

    public void setQuantity(Float quantity) {
        LessThanZeroException.throwIfLessThan0("Quantity",quantity);
        this.quantity = quantity;
        setTotalPrice();
    }

    public void setTotalPrice(Float totalPrice) {
        LessThanZeroException.throwIfLessThan0("TotalPrice",totalPrice);
        this.totalPrice = totalPrice;
    }

    private void setTotalPrice(){
        float price= this.numberOfBags*this.supplyEntity.getPricePerBag() + this.quantity*this.supplyEntity.getPricePerKilo();
        setTotalPrice(price);
    }
}
