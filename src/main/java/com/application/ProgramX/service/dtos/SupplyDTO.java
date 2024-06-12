package com.application.ProgramX.service.dtos;

import com.application.ProgramX.service.exceptions.LessThanZeroException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyDTO implements Cloneable {
    private Long supplyID;
    private String supplyName;
    private SupplyCategoryDTO supplyCategory;
    private Integer numberOfBags=0;
    private Float quantity=0.0f;
    private Float pricePerBag=0.0f;
    private Float pricePerKilo=0.0f;

    public void setNumberOfBags(Integer numberOfBags) {
        LessThanZeroException.throwIfLessThan0("Number of bags",numberOfBags);
        this.numberOfBags = numberOfBags;
    }

    public void setQuantity(Float quantity) {
        LessThanZeroException.throwIfLessThan0("Quantity",quantity);
        this.quantity = quantity;
    }

    public void setPricePerBag(Float pricePerBag) {
        LessThanZeroException.throwIfLessThan0("PricePerBag",pricePerBag);
        this.pricePerBag = pricePerBag;
    }

    public void setPricePerKilo(Float pricePerKilo) {
        LessThanZeroException.throwIfLessThan0("PricePerKilo",pricePerKilo);
        this.pricePerKilo = pricePerKilo;
    }


    @Override
    public SupplyDTO clone() {
        try {
            SupplyDTO clone = (SupplyDTO) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
