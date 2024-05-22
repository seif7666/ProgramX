package com.application.ProgramX.service.message;

public interface Message {

    String categoryWithNameAlreadyExists(String name);

    String sureToCreateCategory();

    String sureToUpdateCategory();

    String sureToDeleteCategory();

    String categoryNameMustNotBeEmpty();

    String categoryCreatedSuccessfully();

    String categoryUpdatedSuccessfully();

    String categoryDeleted();
}
