package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private Image image;
    private ImageView imageView;
    private VBox vBox;
    private Label label;
    public GuiElementBox(IMapElement mapElement){
        try{
            this.image = new Image(new FileInputStream(mapElement.getImagePath()));
        }
        catch (FileNotFoundException ex){
            System.out.println("File not found");
        }
        this.imageView = new ImageView(this.image);
        this.imageView.setFitWidth(20);
        this.imageView.setFitHeight(20);

        if (mapElement instanceof Grass){
            this.label = new Label("trawa");
        }
        else{
            this.label = new Label("Z " + mapElement.getPosition());
        }
        this.vBox = new VBox();
        this.vBox.getChildren().add(this.imageView);
        this.vBox.getChildren().add(this.label);
        this.vBox.setAlignment(Pos.CENTER);
    }
    public VBox getvBox() {
        return vBox;
    }
}
