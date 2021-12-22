package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.IMapElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private Image image;
    private ImageView imageView;
    private VBox vBox;
    public GuiElementBox(IMapElement mapElement, AbstractWorldMap map, Color color, int cellSize){

//        try{
//            this.image = new Image(new FileInputStream(mapElement.getImagePath()));
//        }
//        catch (FileNotFoundException ex){
//            System.out.println("File not found");
//        }
//        this.imageView = new ImageView(this.image);
//        this.imageView.setFitWidth(20);
//        this.imageView.setFitHeight(20);
        this.vBox = new VBox();
        if (mapElement instanceof Animal && map.getNumberOfAnimalsOnSamePosition(mapElement.getPosition()) > 0){
            int animalsCount = map.getNumberOfAnimalsOnSamePosition(mapElement.getPosition());
            Button button = new Button("" + animalsCount);
            if (animalsCount >= 100){
                button.setStyle("-fx-font-size: 8");
            }else if (animalsCount >= 10){
                button.setStyle("-fx-font-size: 10");
            }else {
                button.setStyle("-fx-font-size: 12");
            }

            button.setOnAction(e->{
                System.out.println("hyhy");
            });

            button.setMinSize(cellSize, cellSize);
            button.setMaxSize(cellSize, cellSize);
            button.setPrefSize(cellSize, cellSize);

            button.setBackground(new Background(new BackgroundFill(((Animal) mapElement).getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
            this.vBox.getChildren().add(button);

        }else {

            Label label = new Label("");
            this.vBox.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
            this.vBox.getChildren().add(label);
        }
        this.vBox.setAlignment(Pos.CENTER);
    }


    public VBox getvBox() {
        return vBox;
    }
}