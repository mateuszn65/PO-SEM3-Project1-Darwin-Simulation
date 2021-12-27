package agh.ics.oop.gui;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.Animal;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TrackingBox {
    public static void display(Animal animal, AbstractWorldMap map){


        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Chosen animal");
        window.setMinWidth(300);

        Label genotype = new Label("This animals's genotype is \n " + animal.getGenotype());
        genotype.setAlignment(Pos.CENTER);
        Label question = new Label("Do you want to follow this animal");

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e ->{
            map.getTracer().startTracking(animal);
            window.close();
        });
        Button noButton = new Button("No");
        noButton.setOnAction(e -> window.close());

        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(yesButton, noButton);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(genotype, question, buttonsBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
    }
}
