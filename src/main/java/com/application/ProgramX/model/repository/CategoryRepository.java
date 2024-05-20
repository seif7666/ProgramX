package com.application.ProgramX.model.repository;

import com.application.ProgramX.model.entities.SupplyCategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<SupplyCategoryEntity,Long> {
}
