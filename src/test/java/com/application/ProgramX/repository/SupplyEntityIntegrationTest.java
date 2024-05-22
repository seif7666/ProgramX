package com.application.ProgramX.repository;

import com.application.ProgramX.model.entities.SupplyEntity;
import com.application.ProgramX.model.entities.SupplyCategoryEntity;
import com.application.ProgramX.model.repository.DAOPool;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Log
public class SupplyEntityIntegrationTest {
    @Autowired
    private DAOPool daoPool;

    @Test
    public void checkThatAllSuppliesWithCertainCategoryIDWorks(){
        List<SupplyCategoryEntity> categories= Helper.generateCategories();
        this.daoPool.getCategoryRepository().saveAll(categories);
        List<SupplyEntity>supplies= Helper.createSupplies(categories);
        this.daoPool.getSupplyRepository().saveAll(supplies);
        assertThat(Helper.getSize(this.daoPool.getSupplyRepository().getSuppliesByCategory(categories.get(0).getCategoryID()))).isEqualTo(2);
        assertThat(Helper.getSize(this.daoPool.getSupplyRepository().getSuppliesByCategory(categories.get(1).getCategoryID()))).isEqualTo(1);
    }
    @Test
    public void checkThatGetNumberOfCategoriesWithNameWorksRight(){
        List<SupplyCategoryEntity> categories= Helper.generateCategories();
        SupplyCategoryEntity entity= categories.get(0);
        entity.setCategoryName("Test");
        this.daoPool.getCategoryRepository().save(entity);
        assertThat(this.daoPool.getCategoryRepository().getNumberOfCategoriesWithName("Test")).isEqualTo(1);
        this.daoPool.getCategoryRepository().deleteById(entity.getCategoryID());
        assertThat(this.daoPool.getCategoryRepository().getNumberOfCategoriesWithName("Test")).isEqualTo(0);
    }
    @Test
    public void testWeCanGetAllRequiredSuppliesFromCertainCategory(){

        List<SupplyCategoryEntity> categories= Helper.generateCategories();
        for(SupplyCategoryEntity category:categories) {
            daoPool.getCategoryRepository().save(category);
        }
        assertThat(daoPool.getCategoryRepository().count()).isEqualTo(3L);
        List<SupplyEntity> supplies= Helper.createSupplies(categories);
        for (SupplyEntity s : supplies)
            daoPool.getSupplyRepository().save(s);
        assertThat(daoPool.getCategoryRepository().findById(1l).get().getSupplies().size()).isEqualTo(2);
        assertThat(daoPool.getCategoryRepository().findById(2l).get().getSupplies().size()).isEqualTo(0);
        assertThat(daoPool.getCategoryRepository().findById(3l).get().getSupplies().size()).isEqualTo(0);
    }
    @Test
    public void checkThatUpdatingCategoryNameWillThrowConstraintError(){
        List<SupplyCategoryEntity> categories= Helper.generateCategories();
        daoPool.getCategoryRepository().saveAll(categories);
        categories.get(2).setCategoryName(categories.get(0).getCategoryName());
        assertThrows(JpaSystemException.class,
                ()->daoPool.getCategoryRepository().save(categories.get(2))
        )
        ;
    }

    @Test
    public void checkThatNewCategoryNameIsPropagatedToSupplies(){
        List<SupplyCategoryEntity> categories= Helper.generateCategories();
        daoPool.getCategoryRepository().saveAll(categories);
        List<SupplyEntity> supplies= Helper.createSupplies(categories);
        daoPool.getSupplyRepository().saveAll(supplies);
        categories.get(0).setCategoryName("NewName");
        daoPool.getCategoryRepository().save(categories.get(0));
        assertThat(supplies.get(0).getSupplyCategory().getCategoryName()).isEqualTo("NewName");
    }

    @Test
    public void checkThatSuppliesAreDeletedByDeletingCategory(){
        List<SupplyCategoryEntity> categories= Helper.generateCategories();
        daoPool.getCategoryRepository().saveAll(categories);
        List<SupplyEntity> supplies= Helper.createSupplies(categories);
        daoPool.getSupplyRepository().saveAll(supplies);
        daoPool.getCategoryRepository().deleteById(categories.get(0).getCategoryID());
        assertThat(daoPool.getCategoryRepository().findById(categories.get(0).getCategoryID())).isNotPresent();
        for(SupplyEntity s:daoPool.getSupplyRepository().findAll())
            log.info(s.toString());
        assertThat(daoPool.getSupplyRepository().count()).isEqualTo(0);
    }
}

