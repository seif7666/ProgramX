package com.application.ProgramX.repository;

import com.application.ProgramX.domain.Supply;
import com.application.ProgramX.domain.SupplyCategory;
import com.application.ProgramX.domain.trading.BuyOperation;
import com.application.ProgramX.domain.trading.SellOperation;
import com.application.ProgramX.domain.trading.TradeOperation;
import com.application.ProgramX.domain.trading.TradedSupply;
import org.junit.jupiter.api.BeforeAll;
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
public class TradeOperationTest {

    @Autowired
    private DAOPool daoPool;

    private List<Supply> supplies;

    @BeforeEach
    public void createSuppliesAndCategories(){
        List<SupplyCategory> categories= Helper.generateCategories();
        daoPool.getCategoryRepository().saveAll(categories);
        supplies= Helper.createSupplies(categories);
        daoPool.getSupplyRepository().saveAll(supplies);
    }
    @Test
    public void checkThatNumbersAreCorrect(){
        BuyOperation operation=new BuyOperation();
        TradedSupply ts= new TradedSupply();
        ts.setSupply(supplies.get(0));
        ts.setOperation(operation);
        ts.setQuantity(15.0f);
        daoPool.getTradeOperationRepository().save(operation);
        daoPool.getTradedSupplyRepository().save(ts);
        TradedSupply s=daoPool.getTradedSupplyRepository().findById(new TradedSupply.EmbeddedTradedSupply(operation,supplies.get(0))).get();
        System.out.println(s.getEmbeddedTradedSupply().getOperation().isSellingOperation());

    }
}
