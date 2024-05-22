package com.application.ProgramX.service.message.english;

import com.application.ProgramX.service.message.CategoryMessage;

public class EnglishCategoryMessage implements CategoryMessage {
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

    @Override
    public String categoryUpdatedSuccessfully() {
        return "Category was successfully updated!";
    }

    @Override
    public String enterNewCategoryName() {
        return "Enter new category name";
    }

    @Override
    public String categoryDeleted() {
        return "Category was deleted!";
    }

    @Override
    public String categoryCreatedSuccessfully() {
        return "New Category was successfully created!";
    }

    @Override
    public String categoryNameMustNotBeEmpty() {
        return "Category name is empty!";
    }
}
