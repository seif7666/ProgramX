package com.application.ProgramX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class ProgramXApp extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init(){
        this.context=new SpringApplicationBuilder(SpringApplication.class).run();
    }

    @Override
    public void stop(){
        this.context.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage) throws Exception {
        context.publishEvent(new StageReadyEvent(stage));
    }
    public static class StageReadyEvent extends ApplicationEvent{
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return (Stage) super.getSource();
        }
    }

}
