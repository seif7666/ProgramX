package com.application.ProgramX.model.repository;

import com.application.ProgramX.model.entities.trading.TradeOperationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOperationRepository extends CrudRepository<TradeOperationEntity,String> {
}
