package agh.ics.oop.gui;
import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class App extends Application {
    private final IWorldMap map = new GrassField(10);
    @Override
    public void init(){
        //String[] argstab = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        String[] argstab = getParameters().getRaw().toArray(new String[0]);
        MoveDirection[] directions = new OptionsParser().parse(argstab);
        //IWorldMap map = new RectangularMap(10, 5);

        //System.out.println(map);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine = new SimulationEngine(directions, this.map, positions);
        engine.run();
    }
    private GridPane createGrid(){
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        Label label = new Label("y/x");
        grid.add(label, 0, 0);
        grid.getColumnConstraints().add(new ColumnConstraints(20));
        grid.getRowConstraints().add(new RowConstraints(20));
        GridPane.setHalignment(label, HPos.CENTER);

        Vector2d ll = ((AbstractWorldMap) this.map).getLowerLeft();
        Vector2d ur = ((AbstractWorldMap) this.map).getUpperRight();
        //col
        for (int i = 0; i < ur.x - ll.x + 1; i++){
            label = new Label("" + (ll.x + i));
            grid.add(label, i+1, 0);
            grid.getColumnConstraints().add(new ColumnConstraints(20));
            GridPane.setHalignment(label, HPos.CENTER);
        }
        //row
        for (int i = 0; i < ur.y - ll.y + 1; i++){
            label = new Label("" + (ur.y - ll.y - i));
            grid.add(label, 0, i+1);
            grid.getRowConstraints().add(new RowConstraints(20));
            GridPane.setHalignment(label, HPos.CENTER);
        }
        //el
        for(int i = 0; i < ur.x - ll.x + 1; i++){
            for(int j = 0; j < ur.y - ll.y + 1; j++){
                IMapElement mapElement = (IMapElement) this.map.objectAt(new Vector2d((ll.x + i),(ur.y - j)));
                if (mapElement != null){
                    label = new Label("" + mapElement);
                    grid.add(label, i+1,j+1);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }



        return grid;
    }

    @Override
    public void start(Stage primaryStage){
        Scene scene = new Scene(createGrid(), 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
