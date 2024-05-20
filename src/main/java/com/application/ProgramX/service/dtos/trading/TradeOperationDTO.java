package com.application.ProgramX.service.dtos.trading;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class TradeOperationDTO {
    private String operationID;
    private Set<TradedSupplyDTO> tradedSupplies;
    public abstract boolean isSellingOperation();
}
