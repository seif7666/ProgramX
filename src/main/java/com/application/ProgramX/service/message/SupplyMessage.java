package com.application.ProgramX.service.message;

public interface SupplyMessage {
    String supplyNameIsNotUnique(String supplyName);

    String sureYouWantToCreateSupply();

    String updateSuccessful();

    String sureYouWantToUpdateSupply();

    String supplyDeleted();

    String sureYouWantToDelete();

    String createdSuccessfully();
}
