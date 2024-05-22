package com.application.ProgramX.view;

import com.application.ProgramX.ProgramXApp;
import com.application.ProgramX.SpringApplication;
import com.application.ProgramX.service.apis.ICategoryService;
import com.application.ProgramX.service.apis.impl.CategoryService;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.view.controllers.CategoryController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
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
            URL resource= getClass().getResource("/Categories.fxml");
            loader.setLocation(resource);
            loader.setControllerFactory(c ->{
                return new CategoryController(
                        applicationContext.getBean(MessageRetriever.class),
                        applicationContext.getBean(ICategoryService.class)
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
}