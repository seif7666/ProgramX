package com.application.ProgramX.services;

import com.application.ProgramX.model.entities.SupplyCategoryEntity;
import com.application.ProgramX.model.entities.SupplyEntity;
import com.application.ProgramX.model.repository.DAOPool;
import com.application.ProgramX.repository.Helper;
import com.application.ProgramX.service.apis.ServicePool;
import com.application.ProgramX.service.dtos.SupplyCategoryDTO;
import com.application.ProgramX.service.dtos.SupplyDTO;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Log
public class SupplyServiceIntegrationTest {
    @Autowired
    private ServicePool pool;
    @Autowired
    private DAOPool daoPool;
    @BeforeEach
    public void fillDB(){
        List<SupplyCategoryEntity> categories= Helper.generateCategories();
        List<SupplyEntity> supplyEntities= Helper.createSupplies(categories);
        this.daoPool.getCategoryRepository().saveAll(categories);
        this.daoPool.getSupplyRepository().saveAll(supplyEntities);
    }
    @Test
    public void checkThatMultipleCreationsAreValid(){
        SupplyCategoryDTO categoryDTO= this.pool.getSupplyService().getCategories().get(0);
        int currentSize= this.pool.getSupplyService().getSupplies().size();
        SupplyDTO dto1= SupplyDTO.builder()
                .supplyCategory(categoryDTO)
                .supplyName("Test1")
                .numberOfBags(1)
                .quantity(5f)
                .pricePerBag(5f)
                .pricePerKilo(10f)
                .build();
        this.pool.getSupplyService().create(dto1).runImmediately();
        assertThat(this.pool.getSupplyService().getSupplies().size()).isEqualTo(currentSize+1);

        SupplyDTO dto2= SupplyDTO.builder()
                .supplyCategory(categoryDTO)
                .supplyName("Test2")
                .numberOfBags(1)
                .quantity(5f)
                .pricePerBag(5f)
                .pricePerKilo(10f)
                .build();
        this.pool.getSupplyService().create(dto2).runImmediately();
        assertThat(this.pool.getSupplyService().getSupplies().size()).isEqualTo(currentSize+2);

    }
}
