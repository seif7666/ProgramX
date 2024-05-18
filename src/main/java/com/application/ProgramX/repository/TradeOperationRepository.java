package com.application.ProgramX.repository;

import com.application.ProgramX.domain.trading.TradeOperation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOperationRepository extends CrudRepository<TradeOperation,String> {
}
