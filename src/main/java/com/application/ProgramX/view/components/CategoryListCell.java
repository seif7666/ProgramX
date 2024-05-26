package com.application.ProgramX.view.components;

import com.application.ProgramX.service.apis.ICategoryService;
import com.application.ProgramX.service.apis.ServicePool;
import com.application.ProgramX.service.dtos.SupplyCategoryDTO;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.service.responses.dialogs.ErrorDialogue;
import com.application.ProgramX.view.controllers.Controller;
import com.application.ProgramX.view.controllers.Observer;
import com.application.ProgramX.view.controllers.SupplyController;
import com.application.ProgramX.view.styles.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class CategoryListCell extends ListCell<SupplyCategoryDTO> {

    private final ServicePool service;

    private SupplyCategoryDTO categoryDTO;
    private MessageRetriever retriever;

    public CategoryListCell(ServicePool service, MessageRetriever retriever, Observer observer) {
        this.service = service;
        this.retriever = retriever;
        this.observer = observer;
    }

    private final Observer observer;


    public void updateItem(SupplyCategoryDTO pos, boolean empty) {
        super.updateItem(pos, empty);

        if (pos == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.categoryDTO= pos;
            setCell();
        }
    }
    private void setCell(){
        BorderPane pane = new BorderPane();
        pane.setStyle(String.format("-fx-background-color:%s",Constants.BACKGROUND_COLOR));
        Label label= new Label();
        pane.rightProperty().set(label);
        setLabelStyle(label);
        HBox hbox=new HBox();
        setHboxStyle(hbox);
        pane.leftProperty().set(hbox);
        setGraphic(pane);

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SupplyController controller= new SupplyController(service,retriever);
                controller.setSwitchingCategory(categoryDTO);
                Controller.switchWindow(Controller.SUPPLIES_FXML,controller,mouseEvent);
            }
        });
    }

    private void setHboxStyle(HBox hbox) {
        hbox.getChildren().add(new DeleteButton(this.categoryDTO));
        hbox.getChildren().add(new UpdateButton(this.categoryDTO));
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(15.5);
        hbox.setPadding(new Insets(10));
        hbox.setStyle(String.format("-fx-background-color: %s", Constants.BACKGROUND_COLOR));
    }

    private void setLabelStyle(Label label) {
        label.setText(this.categoryDTO.getCategoryName());
        label.setFont(new Font("Arial",19.0));
        label.setPadding(new Insets(10,10,10,10));
        label.getParent().setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                label.setTextFill(Color.DARKCYAN);
            }
        });
        label.getParent().setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                label.setTextFill(Color.BLACK);
            }
        });
    }

    private abstract class CategoryButton extends Button{
        protected SupplyCategoryDTO category;

        public CategoryButton(SupplyCategoryDTO dto, ImageView view){
            super();
            this.category=dto;
            view.setPreserveRatio(true);
            view.setFitHeight(25);
            setGraphic(view);
            setStyle();
            setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    execute();
                    observer.update();
                }
            });
        }
        private  void setStyle(){
            setStyle(String.format("-fx-background-color: %s ; -fx-border-color:black",Constants.BACKGROUND_COLOR));
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    setStyle(String.format("-fx-background-color: %s ; -fx-border-color:black",Constants.BUTTON_HOVER_COLOR));
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    setStyle(String.format("-fx-background-color: %s ; -fx-border-color:black",Constants.BACKGROUND_COLOR));
                }
            });
        }

        protected abstract void execute();

    }
    private  class DeleteButton extends CategoryButton{

        public DeleteButton(SupplyCategoryDTO dto){
            super(dto, new ImageView(Constants.ICONS.DELETE_ICON));
        }
        @Override
        protected void execute(){
            service.getCategoryService().delete(categoryDTO).runDialogue();
        }

    }

    private  class UpdateButton extends CategoryButton{

        public UpdateButton(SupplyCategoryDTO dto){
            super(dto,new ImageView(Constants.ICONS.UPDATE_ICON));
        }

        @Override
        protected void execute() {
            TextInputDialog x=new TextInputDialog("Enter anything here");
            x.setHeaderText(retriever.getMessage().getCategoryMessage().enterNewCategoryName());
            x.showAndWait();
            String text= x.getResult();
            if(text.isEmpty() || text.trim().isEmpty()){
                new ErrorDialogue(retriever.getMessage().getCategoryMessage().categoryNameMustNotBeEmpty());
                return;
            }
            this.category.setCategoryName(text);
            service.getCategoryService().update(this.category).runDialogue();
        }
    }
}
