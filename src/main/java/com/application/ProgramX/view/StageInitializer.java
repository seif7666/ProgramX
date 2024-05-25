package com.application.ProgramX.view;

import com.application.ProgramX.ProgramXApp;
import com.application.ProgramX.service.apis.ServicePool;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.view.controllers.CategoryController;
import com.application.ProgramX.view.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class StageInitializer implements ApplicationListener<ProgramXApp.StageReadyEvent> {
    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    @Override
    public void onApplicationEvent(ProgramXApp.StageReadyEvent event) {
        Stage stage = event.getStage();
        try {
            FXMLLoader loader = new FXMLLoader();
            URL resource= getClass().getResource(Controller.CATEGORIES_FXML);
            loader.setLocation(resource);
            loader.setControllerFactory(c ->{
                return new CategoryController(
                        applicationContext.getBean(MessageRetriever.class),
                        applicationContext.getBean(ServicePool.class)
                );
            });
            VBox vbox=loader.<VBox>load();
            Scene scene = new Scene(vbox);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /*public static void switchScene(Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL resource= StageInitializer.class.getResource("/FXMLs/Categories.fxml");
            loader.setLocation(resource);
            loader.setControllerFactory(c ->{
                return new CategoryController(
                        applicationContext.getBean(MessageRetriever.class),
                        applicationContext.getBean(ServicePool.class)
                );
            });
            VBox vbox=loader.<VBox>load();
            Scene scene = new Scene(vbox);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }*/
}