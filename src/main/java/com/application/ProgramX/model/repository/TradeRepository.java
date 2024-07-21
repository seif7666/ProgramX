package com.application.ProgramX.model.repository;

import com.application.ProgramX.model.entities.trading.BuyOperationEntityEntity;
import com.application.ProgramX.model.entities.trading.TradeOperationEntity;
import com.application.ProgramX.model.entities.trading.TradedSupplyEntity;
import com.application.ProgramX.service.dtos.trading.TradedSupplyDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class TradeRepository {

    private final List<TradedSupplyEntity> tradedSupplies;
    private DAOPool pool;

    public TradeRepository(DAOPool pool,List<TradedSupplyEntity>entites){
        this.tradedSupplies=entites;
        this.pool=pool;
    }

    @Transactional
    public String trade(){
        TradeOperationEntity operation= new BuyOperationEntityEntity();
        this.pool.getTradeOperationRepository().save(operation);
        for(TradedSupplyEntity tradedSupplyEntity: this.tradedSupplies){
            tradedSupplyEntity.setOperation(operation);
            this.pool.getTradedSupplyRepository().save(tradedSupplyEntity);
            this.pool.getSupplyRepository().save(tradedSupplyEntity.getEmbeddedTradedSupply().getSupplyEntity());
        }
        return "";
    }


}
