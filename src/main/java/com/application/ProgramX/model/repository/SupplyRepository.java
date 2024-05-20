package com.application.ProgramX.model.repository;

import com.application.ProgramX.model.entities.SupplyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends CrudRepository<SupplyEntity,Long> {
}
