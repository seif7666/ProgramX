package com.application.ProgramX.repository;

import com.application.ProgramX.domain.Supply;
import com.application.ProgramX.domain.SupplyCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<SupplyCategory,Long> {
}
