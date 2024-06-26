package com.application.ProgramX.model.entities.trading;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(name="TradeOperation")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="OPERATION_TYPE",
        discriminatorType = DiscriminatorType.STRING)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class TradeOperationEntity {
    protected static boolean SELL_OPERATION=true;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String operationID;
    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL)
    private Set<TradedSupplyEntity> tradedSupplies;

    public abstract boolean isSellingOperation();
}
