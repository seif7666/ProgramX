package com.application.ProgramX.repository;

import com.application.ProgramX.domain.trading.TradedSupply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradedSupplyRepository extends CrudRepository<TradedSupply,TradedSupply.EmbeddedTradedSupply> {
}
