package com.application.ProgramX.repository;

import com.application.ProgramX.model.entities.SupplyEntity;
import com.application.ProgramX.model.entities.SupplyCategoryEntity;
import com.application.ProgramX.model.entities.trading.SellOperationEntityEntity;
import com.application.ProgramX.model.entities.trading.TradedSupplyEntity;

import java.util.*;

public class Helper {

    public static List<SupplyCategoryEntity> generateCategories(){
        LinkedList<SupplyCategoryEntity> list=new LinkedList<>();
        String[] names=new String[]{"Chicken Stuff","Cow Stuff","Soil Stuff"};
        for(String name : names)
            list.add(SupplyCategoryEntity.builder().CategoryName(name).build());
        return list;
    }

    public static List<SupplyEntity> createSupplies(Iterable<SupplyCategoryEntity> categories){
        Iterator<SupplyCategoryEntity> iterator= categories.iterator();
        SupplyCategoryEntity category= iterator.next();
        List<SupplyEntity>supplies= new LinkedList<>();
        supplies.add(SupplyEntity.builder()
                        .SupplyName("ChickenFood")
                .supplyCategory(category)
                .numberOfBags(10)
                .pricePerBag(250.00f)
                .quantity(25.00f)
                .pricePerBag(1500.00f)
                .pricePerKilo(15.89f)
                .build());
        supplies.add(SupplyEntity.builder()
                .SupplyName("ChickenMedicine")
                .supplyCategory(category)
                .numberOfBags(2)
                .pricePerBag(500.00f)
                .quantity(2.00f)
                .pricePerBag(7000.00f)
                .pricePerKilo(90.89f)
                .build());

        supplies.add(SupplyEntity.builder()
                .SupplyName("Leaves")
                .supplyCategory(iterator.next())
                .numberOfBags(2)
                .pricePerBag(500.00f)
                .quantity(2.00f)
                .pricePerBag(7000.00f)
                .pricePerKilo(90.89f)
                .build());

        return supplies;

    }


    public static List<SellOperationEntityEntity> createSellOperations(List<SupplyEntity> supplies) {
        List<SellOperationEntityEntity> operations = new LinkedList<>();
        Set<TradedSupplyEntity> tradedSupplies = new HashSet<>();
        SellOperationEntityEntity op = new SellOperationEntityEntity();
        tradedSupplies.add(TradedSupplyEntity.builder()
                .embeddedTradedSupply(TradedSupplyEntity.EmbeddedTradedSupply.builder().supplyEntity(supplies.get(0)).operation(op).build())
                .numberOfBags(2)
                .totalPrice(150.00f)
                .build()
        );
        tradedSupplies.add(TradedSupplyEntity.builder()
                .embeddedTradedSupply(TradedSupplyEntity.EmbeddedTradedSupply.builder().supplyEntity(supplies.get(1)).operation(op).build())
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

    public static int getSize(Iterable<?> iterable){
        int size=0;
        for (Object o : iterable) {
            size++;
        }
        return size;

    }
}
