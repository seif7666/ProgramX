package com.application.ProgramX.model.repository;

import com.application.ProgramX.model.entities.trading.TradedSupplyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradedSupplyRepository extends CrudRepository<TradedSupplyEntity, TradedSupplyEntity.EmbeddedTradedSupply> {
}
