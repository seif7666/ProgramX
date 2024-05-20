package com.application.ProgramX.model.repository;

import com.application.ProgramX.model.entities.SupplyCategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<SupplyCategoryEntity,Long> {
    @Query("SELECT COUNT(A) FROM SupplyCategoryEntity  A WHERE A.CategoryName=?1")
    public int getNumberOfCategoriesWithName(String name);
}
