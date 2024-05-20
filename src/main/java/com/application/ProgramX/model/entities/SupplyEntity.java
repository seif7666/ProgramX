package com.application.ProgramX.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SUPPLY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long SupplyID;
    @Column(nullable = false,unique = true)
    private String SupplyName;
    @ManyToOne(cascade=CascadeType.REMOVE)
    private SupplyCategoryEntity supplyCategory;
    @Column(nullable = false)
    private Integer numberOfBags=0;
    @Column(nullable = false)
    private Float quantity=0.0f;
    @Column(nullable = false)
    private Float pricePerBag=0.0f;
    @Column(nullable = false)
    private Float pricePerKilo=0.0f;
}
