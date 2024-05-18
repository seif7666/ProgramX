package com.application.ProgramX.repository;

import com.application.ProgramX.domain.Supply;
import com.application.ProgramX.domain.SupplyCategory;
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
public class SupplyIntegrationTest {
    @Autowired
    private DAOPool daoPool;
    @Test
    public void testWeCanGetAllRequiredSuppliesFromCertainCategory(){

        List<SupplyCategory> categories= Helper.generateCategories();
        for(SupplyCategory category:categories) {
            daoPool.getCategoryRepository().save(category);
        }
        assertThat(daoPool.getCategoryRepository().count()).isEqualTo(3L);
        List<Supply> supplies= Helper.createSupplies(categories);
        for (Supply s : supplies)
            daoPool.getSupplyRepository().save(s);
        assertThat(daoPool.getCategoryRepository().findById(1l).get().getSupplies().size()).isEqualTo(2);
        assertThat(daoPool.getCategoryRepository().findById(2l).get().getSupplies().size()).isEqualTo(0);
        assertThat(daoPool.getCategoryRepository().findById(3l).get().getSupplies().size()).isEqualTo(0);
    }
    @Test
    public void checkThatUpdatingCategoryNameWillThrowConstraintError(){
        List<SupplyCategory> categories= Helper.generateCategories();
        daoPool.getCategoryRepository().saveAll(categories);
        categories.get(2).setCategoryName(categories.get(0).getCategoryName());
        assertThrows(JpaSystemException.class,
                ()->daoPool.getCategoryRepository().save(categories.get(2))
        )
        ;
    }

    @Test
    public void checkThatNewCategoryNameIsPropagatedToSupplies(){
        List<SupplyCategory> categories= Helper.generateCategories();
        daoPool.getCategoryRepository().saveAll(categories);
        List<Supply> supplies= Helper.createSupplies(categories);
        daoPool.getSupplyRepository().saveAll(supplies);
        categories.get(0).setCategoryName("NewName");
        daoPool.getCategoryRepository().save(categories.get(0));
        assertThat(supplies.get(0).getSupplyCategory().getCategoryName()).isEqualTo("NewName");
    }

    @Test
    public void checkThatSuppliesAreDeletedByDeletingCategory(){
        List<SupplyCategory> categories= Helper.generateCategories();
        daoPool.getCategoryRepository().saveAll(categories);
        List<Supply> supplies= Helper.createSupplies(categories);
        daoPool.getSupplyRepository().saveAll(supplies);
        daoPool.getCategoryRepository().deleteById(categories.get(0).getCategoryID());
        assertThat(daoPool.getCategoryRepository().findById(categories.get(0).getCategoryID())).isNotPresent();
        for(Supply s:daoPool.getSupplyRepository().findAll())
            log.info(s.toString());
        assertThat(daoPool.getSupplyRepository().count()).isEqualTo(0);
    }
}

