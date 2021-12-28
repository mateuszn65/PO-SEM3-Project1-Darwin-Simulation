package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.IMapElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class GuiElementBox {
    private final VBox vBox;

    //SINGLE CELL IN THE GRID
    public GuiElementBox(IMapElement mapElement, AbstractWorldMap map, Color color, int cellSize, int[] dominant){
        this.vBox = new VBox();
        if (mapElement instanceof Animal && map.getNumberOfAnimalsOnSamePosition(mapElement.getPosition()) > 0){
            int animalsCount = map.getNumberOfAnimalsOnSamePosition(mapElement.getPosition());
            Button button = new Button("" + animalsCount);

            if (cellSize < 25){
                button.setStyle("-fx-font-size: 8");
            }else if (animalsCount >= 100){
                button.setStyle("-fx-font-size: 8");
            }else if (animalsCount >= 10){
                button.setStyle("-fx-font-size: 10");
            }else {
                button.setStyle("-fx-font-size: 12");
            }

            button.setOnAction(e->{
                TrackingBox.display((Animal) mapElement, map);
            });

            button.setMinSize(cellSize, cellSize);
            button.setMaxSize(cellSize, cellSize);
            button.setPrefSize(cellSize, cellSize);

            button.setBackground(new Background(new BackgroundFill(((Animal) mapElement).getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
            this.vBox.getChildren().add(button);

            if (dominant != null){
                button.setDisable(true);
                if (map.isDominantAt(mapElement.getPosition(), dominant)){
                    button.setVisible(false);
                    this.vBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        }else {
            this.vBox.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        this.vBox.setAlignment(Pos.CENTER);
    }


    public VBox getvBox() {
        return vBox;
    }
}
