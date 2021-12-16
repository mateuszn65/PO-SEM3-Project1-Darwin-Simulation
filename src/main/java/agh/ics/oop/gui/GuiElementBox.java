package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.IMapElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private Label label;
    public GuiElementBox(IMapElement mapElement, WallMap map, Color color){

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
            this.label = new Label("" + map.getNumberOfAnimalsOnSamePosition(mapElement.getPosition()));
            this.vBox.setBackground(new Background(new BackgroundFill(((Animal) mapElement).getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
        }else {
            this.label = new Label("");
            this.vBox.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        }

        this.vBox.getChildren().add(this.label);
        this.vBox.setAlignment(Pos.CENTER);
        //this.vBox.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    public VBox getvBox() {
        return vBox;
    }
}
