package com.application.ProgramX.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "SUPPLY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long SupplyID;
    @Column(nullable = false,unique = true)
    private String SupplyName;
    @ManyToOne(cascade=CascadeType.REMOVE)
    private SupplyCategory supplyCategory;
    @Column(nullable = false)
    private Integer numberOfBags=0;
    @Column(nullable = false)
    private Float quantity=0.0f;
    @Column(nullable = false)
    private Float pricePerBag=0.0f;
    @Column(nullable = false)
    private Float pricePerKilo=0.0f;

    public void setNumberOfBags(Integer numberOfBags) {
        DomainHelper.checkNotLessThan0AndThrowErrorInCase("NumberOfBags",numberOfBags);
        this.numberOfBags = numberOfBags;
    }

    public void setQuantity(Float quantity) {
        DomainHelper.checkNotLessThan0AndThrowErrorInCase("Quantity",quantity);
        this.quantity = quantity;
    }

    public void setPricePerBag(Float pricePerBag) {
        DomainHelper.checkNotLessThan0AndThrowErrorInCase("PricePerBag",pricePerBag);
        this.pricePerBag = pricePerBag;
    }

    public void setPricePerKilo(Float pricePerKilo) {
        DomainHelper.checkNotLessThan0AndThrowErrorInCase("PricePerKilo",pricePerKilo);
        this.pricePerKilo = pricePerKilo;
    }

}
