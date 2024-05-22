package com.application.ProgramX.service.message.english;

import com.application.ProgramX.service.message.SupplyMessage;

public class EnglishSupplyMessage implements SupplyMessage {

    @Override
    public String supplyNameIsNotUnique(String supplyName) {
        return String.format("Supply %s is not unique!",supplyName);
    }

    @Override
    public String sureYouWantToCreateSupply() {
        return "Are you sure you want to create this supply?";
    }

    @Override
    public String updateSuccessful() {
        return "Supply updated successfully!";
    }

    @Override
    public String sureYouWantToUpdateSupply() {
        return "Are you sure you want to update this supply?";
    }

    @Override
    public String supplyDeleted() {
        return "Supply was deleted.";
    }

    @Override
    public String sureYouWantToDelete() {
        return "Are you sure you want to delete this supply?";
    }

    @Override
    public String createdSuccessfully() {
        return "Supply created successfully!";
    }
}
