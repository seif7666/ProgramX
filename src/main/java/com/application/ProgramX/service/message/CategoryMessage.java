package com.application.ProgramX.service.message;

public interface CategoryMessage {

    String categoryWithNameAlreadyExists(String name);

    String sureToCreateCategory();

    String sureToUpdateCategory();

    String sureToDeleteCategory();

    String categoryNameMustNotBeEmpty();

    String categoryCreatedSuccessfully();

    String categoryUpdatedSuccessfully();

    String categoryDeleted();

    String enterNewCategoryName();
}
