package com.application.ProgramX.view;

import com.application.ProgramX.ProgramXApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class StageInitializer implements ApplicationListener<ProgramXApp.StageReadyEvent> {

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    @Override
    public void onApplicationEvent(ProgramXApp.StageReadyEvent event) {
        Stage stage = event.getStage();
        try {
            FXMLLoader loader = new FXMLLoader();
            URL resource= getClass().getResource("/Categories.fxml");
            loader.setLocation(resource);
            VBox vbox=loader.<VBox>load();
            Scene scene = new Scene(vbox);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();

        }
    }
}