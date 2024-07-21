package com.application.ProgramX.service.message;

public interface TradedSupplyMessage {


    String insufficientQuantity();

    String totalAmountIsNotValid();

    String thisNeedsOpeningMoreThanOneBag();

    String thereIsNoSufficientQuantityDoYouWantToOpenNewBag();

    String newBagWasOpened();

    String youHaveInsufficientNumberOfBags();

    String supplyAlreadyChosenBefore();
}
