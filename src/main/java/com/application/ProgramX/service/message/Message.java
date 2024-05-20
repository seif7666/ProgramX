package com.application.ProgramX.service.message;

public interface Message {

    String categoryWithNameAlreadyExists(String name);

    String sureToCreateCategory();

    String sureToUpdateCategory();

    String sureToDeleteCategory();
}
