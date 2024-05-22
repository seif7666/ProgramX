package com.application.ProgramX.model.repository;

import com.application.ProgramX.model.entities.SupplyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends CrudRepository<SupplyEntity,Long> {

    @Query("SELECT a FROM SupplyEntity a WHERE a.supplyCategory.CategoryID= ?1 ")
    Iterable<SupplyEntity> getSuppliesByCategory(Long categoryID);
    @Query("SELECT COUNT(a) FROM SupplyEntity a WHERE a.SupplyName= ?1 ")
    int getNumberOfSuppliesHavingSameName(String supplyName);
}
