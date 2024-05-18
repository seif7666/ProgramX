package com.application.ProgramX.repository;

import com.application.ProgramX.domain.Supply;
import com.application.ProgramX.domain.SupplyCategory;
import com.application.ProgramX.domain.trading.BuyOperation;
import com.application.ProgramX.domain.trading.SellOperation;
import com.application.ProgramX.domain.trading.TradeOperation;
import com.application.ProgramX.domain.trading.TradedSupply;

import java.util.*;

public class Helper {

    public static List<SupplyCategory> generateCategories(){
        LinkedList<SupplyCategory> list=new LinkedList<>();
        String[] names=new String[]{"Chicken Stuff","Cow Stuff","Soil Stuff"};
        for(String name : names)
            list.add(SupplyCategory.builder().CategoryName(name).build());
        return list;
    }

    public static List<Supply> createSupplies(Iterable<SupplyCategory> categories){
        Iterator<SupplyCategory> iterator= categories.iterator();
        SupplyCategory category= iterator.next();
        List<Supply>supplies= new LinkedList<>();
        supplies.add(Supply.builder()
                        .SupplyName("ChickenFood")
                .supplyCategory(category)
                .numberOfBags(10)
                .pricePerBag(250.00f)
                .quantity(25.00f)
                .pricePerBag(1500.00f)
                .pricePerKilo(15.89f)
                .build());
        supplies.add(Supply.builder()
                .SupplyName("ChickenMedicine")
                .supplyCategory(category)
                .numberOfBags(2)
                .pricePerBag(500.00f)
                .quantity(2.00f)
                .pricePerBag(7000.00f)
                .pricePerKilo(90.89f)
                .build());

        return supplies;

    }


    public static List<SellOperation> createSellOperations(List<Supply> supplies) {
        List<SellOperation> operations = new LinkedList<>();
        Set<TradedSupply> tradedSupplies = new HashSet<>();
        SellOperation op = new SellOperation();
        tradedSupplies.add(TradedSupply.builder()
                .embeddedTradedSupply(TradedSupply.EmbeddedTradedSupply.builder().supply(supplies.get(0)).operation(op).build())
                .numberOfBags(2)
                .totalPrice(150.00f)
                .build()
        );
        tradedSupplies.add(TradedSupply.builder()
                .embeddedTradedSupply(TradedSupply.EmbeddedTradedSupply.builder().supply(supplies.get(1)).operation(op).build())
                .quantity(5.25f)
                .totalPrice(150.00f)
                .build()
        );
        op.setTradedSupplies(tradedSupplies);
        operations.add(op);/*
        ////////////////////////
        tradedSupplies = new HashSet<>();
        tradedSupplies.add(TradedSupply.builder()
                .embeddedTradedSupply(TradedSupply.EmbeddedTradedSupply.builder().supply(supplies.get(0)).build())
                .numberOfBags(2)
                .quantity(10.50f)
                .totalPrice(350.00f)
                .build()
        );
        op = new SellOperation();
        op.setTradedSupplies(tradedSupplies);
        operations.add(op);
        ///////////////////////////////////////////////////////
        tradedSupplies = new HashSet<>();
        tradedSupplies.add(TradedSupply.builder()
                .embeddedTradedSupply(TradedSupply.EmbeddedTradedSupply.builder().supply(supplies.get(0)).build())
                .numberOfBags(10)
                .quantity(25.50f)
                .totalPrice(500.00f)
                .build()
        );
        op = new SellOperation();
        op.setTradedSupplies(tradedSupplies);
        operations.add(op);
        return operations;

    }

    public static List<BuyOperation> createBuyOperations(List<Supply>supplies) {
        List<BuyOperation> operations = new LinkedList<>();
        Set<TradedSupply> tradedSupplies = new HashSet<>();
        tradedSupplies.add(TradedSupply.builder()
                .embeddedTradedSupply(TradedSupply.EmbeddedTradedSupply.builder().supply(supplies.get(0)).build())
                .numberOfBags(2)
                .totalPrice(150.00f)
                .build()
        );
        tradedSupplies.add(TradedSupply.builder()
                .embeddedTradedSupply(TradedSupply.EmbeddedTradedSupply.builder().supply(supplies.get(1)).build())
                .quantity(5.25f)
                .totalPrice(150.00f)
                .build()
        );
        BuyOperation op = new BuyOperation();
        op.setTradedSupplies(tradedSupplies);
        operations.add(op);
        ////////////////////////
        tradedSupplies = new HashSet<>();
        tradedSupplies.add(TradedSupply.builder()
                .embeddedTradedSupply(TradedSupply.EmbeddedTradedSupply.builder().supply(supplies.get(0)).build())
                .numberOfBags(2)
                .quantity(10.50f)
                .totalPrice(350.00f)
                .build()
        );
        op = new BuyOperation();
        op.setTradedSupplies(tradedSupplies);
        operations.add(op);*/
        return operations;
    }
}
