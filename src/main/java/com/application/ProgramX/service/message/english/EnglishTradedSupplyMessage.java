package com.application.ProgramX.service.message.english;

import com.application.ProgramX.service.message.TradedSupplyMessage;

public class EnglishTradedSupplyMessage implements TradedSupplyMessage {
    @Override
    public String insufficientQuantity() {
        return "You have insufficient quantity to trade with!";
    }

    @Override
    public String totalAmountIsNotValid() {
        return "Total amount is invalid!";
    }

    @Override
    public String thisNeedsOpeningMoreThanOneBag() {
        return "More than one bag needs to be opened! Try dividing instead.";
    }

    @Override
    public String thereIsNoSufficientQuantityDoYouWantToOpenNewBag() {
        return "There is insufficient quantity, do you want to open an extra bag?";
    }

    @Override
    public String newBagWasOpened() {
        return "New bag was opened!";
    }

    @Override
    public String youHaveInsufficientNumberOfBags() {
        return "You have insufficient number of bags!";
    }

    @Override
    public String supplyAlreadyChosenBefore() {
        return "You have already selected this supply.";
    }
}
