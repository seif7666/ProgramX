package com.application.ProgramX.repository;

import com.application.ProgramX.model.entities.SupplyEntity;
import com.application.ProgramX.model.entities.SupplyCategoryEntity;
import com.application.ProgramX.model.repository.DAOPool;
import com.application.ProgramX.model.entities.trading.BuyOperationEntityEntity;
import com.application.ProgramX.model.entities.trading.TradedSupplyEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TradeOperationEntityTest {

    @Autowired
    private DAOPool daoPool;

    private List<SupplyEntity> supplies;

    @BeforeEach
    public void createSuppliesAndCategories(){
        List<SupplyCategoryEntity> categories= Helper.generateCategories();
        daoPool.getCategoryRepository().saveAll(categories);
        supplies= Helper.createSupplies(categories);
        daoPool.getSupplyRepository().saveAll(supplies);
    }
    @Test
    public void checkThatNumbersAreCorrect(){
        BuyOperationEntityEntity operation=new BuyOperationEntityEntity();
        TradedSupplyEntity ts= new TradedSupplyEntity();
        ts.setSupply(supplies.get(0));
        ts.setOperation(operation);
        ts.setQuantity(15.0f);
        daoPool.getTradeOperationRepository().save(operation);
        daoPool.getTradedSupplyRepository().save(ts);
        TradedSupplyEntity s=daoPool.getTradedSupplyRepository().findById(new TradedSupplyEntity.EmbeddedTradedSupply(operation,supplies.get(0))).get();
        System.out.println(s.getEmbeddedTradedSupply().getOperation().isSellingOperation());

    }
}
