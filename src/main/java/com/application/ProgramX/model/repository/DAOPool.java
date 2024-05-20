package com.application.ProgramX.model.repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class DAOPool {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplyRepository supplyRepository;
    @Autowired
    private TradeOperationRepository tradeOperationRepository;
    @Autowired
    private TradedSupplyRepository tradedSupplyRepository;

}
