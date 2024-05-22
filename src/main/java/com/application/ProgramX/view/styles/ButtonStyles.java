package com.application.ProgramX.view.styles;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javax.swing.*;

public class ButtonStyles {

    public static void StyleNavigationButton(Button button){
        button.setStyle(String.format("-fx-background-color: %s;",Constants.BACKGROUND_COLOR));
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setStyle(String.format("-fx-background-color: %s;",Constants.BUTTON_HOVER_COLOR));
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setStyle(String.format("-fx-background-color: %s;",Constants.BACKGROUND_COLOR));
            }
        });
    }
}
