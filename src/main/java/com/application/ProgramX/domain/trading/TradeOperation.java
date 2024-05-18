package com.application.ProgramX.domain.trading;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="OPERATION_TYPE",
        discriminatorType = DiscriminatorType.STRING)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class TradeOperation {
    protected static boolean SELL_OPERATION=true;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String operationID;
    @Column(nullable = false)
    @OneToMany(fetch = FetchType.EAGER)
    private Set<TradedSupply> tradedSupplies;
}
