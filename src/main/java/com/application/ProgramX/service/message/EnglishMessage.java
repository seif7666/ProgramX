package com.application.ProgramX.service.message;

public class EnglishMessage implements Message{
    @Override
    public String categoryWithNameAlreadyExists(String name) {
        return String.format("Category with name %s already exists!",name);
    }

    @Override
    public String sureToCreateCategory() {
        return "Are you sure you want to create this category?";
    }

    @Override
    public String sureToUpdateCategory() {
        return  "Are you sure you want to update this category?";
    }

    @Override
    public String sureToDeleteCategory() {
        return "Are you sure you want to delete this category?";
    }
}
