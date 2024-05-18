package com.application.ProgramX.repository;

import com.application.ProgramX.domain.Supply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends CrudRepository<Supply,Long> {
}
