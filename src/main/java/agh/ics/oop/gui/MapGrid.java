package agh.ics.oop.gui;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.IMapElement;
import agh.ics.oop.Jungle;
import agh.ics.oop.Vector2d;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class MapGrid {
    private final int mapWidth;
    private final int mapHeight;
    private final int cellSize;
    private final Jungle jungle;
    private final AbstractWorldMap map;
    private final GridPane grid;


    public MapGrid(AbstractWorldMap map, int mapWidth, int mapHeight, int cellSize, Jungle jungle){
        this.map = map;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.cellSize = cellSize;
        this.jungle = jungle;
        this.grid = new GridPane();
        this.grid.setStyle("-fx-padding: 10;");
        _updateGrid(null);
    }
    private void _updateGrid(int[] dominant){
        Label label = new Label("y/x");
        this.grid.add(label, 0, 0);
        this.grid.getColumnConstraints().add(new ColumnConstraints(this.cellSize));
        this.grid.getRowConstraints().add(new RowConstraints(this.cellSize));
        GridPane.setHalignment(label, HPos.CENTER);

        Vector2d ll = new Vector2d(0 ,0);;
        Vector2d ur = new Vector2d(this.mapWidth - 1, this.mapHeight - 1);
        //col
        for (int i = 0; i < ur.x - ll.x + 1; i++){
            label = new Label("" + (ll.x + i));
            this.grid.add(label, i+1, 0);
            this.grid.getColumnConstraints().add(new ColumnConstraints(this.cellSize));
            GridPane.setHalignment(label, HPos.CENTER);
        }
        //row
        for (int i = 0; i < ur.y - ll.y + 1; i++){
            label = new Label("" + (ur.y - ll.y - i));
            this.grid.add(label, 0, i+1);
            this.grid.getRowConstraints().add(new RowConstraints(this.cellSize));
            GridPane.setHalignment(label, HPos.CENTER);
        }
        //el
        for(int i = 0; i < ur.x - ll.x + 1; i++){
            for(int j = 0; j < ur.y - ll.y + 1; j++){
                IMapElement mapElement = this.map.objectAt(new Vector2d((ll.x + i),(ur.y - j)));

                if (mapElement != null){
                    GuiElementBox guiElementBox = new GuiElementBox(mapElement, this.map, Color.DARKGREEN, this.cellSize, dominant);
                    this.grid.add(guiElementBox.getvBox(), i+1,j+1);
                }else if (this.jungle.belongsToJungle(new Vector2d(i,j))){
                    GuiElementBox guiElementBox = new GuiElementBox(mapElement, this.map, Color.LIGHTGREEN, this.cellSize, dominant);
                    this.grid.add(guiElementBox.getvBox(), i+1,j+1);
                }else{
                    GuiElementBox guiElementBox = new GuiElementBox(mapElement, this.map, Color.YELLOW, this.cellSize, dominant);
                    this.grid.add(guiElementBox.getvBox(), i+1,j+1);
                }
            }
        }
    }

    public void updateGrid(){
        this.grid.getChildren().clear();
        this.grid.getColumnConstraints().clear();
        this.grid.getRowConstraints().clear();
        _updateGrid(null);
    }

    public void displayDominants(int[] dominant){
        this.grid.getChildren().clear();
        this.grid.getColumnConstraints().clear();
        this.grid.getRowConstraints().clear();
        _updateGrid(dominant);
    }

    public GridPane getGrid() {
        return this.grid;
    }
}
