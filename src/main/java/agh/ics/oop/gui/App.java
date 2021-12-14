package agh.ics.oop.gui;
import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.List;

public class App extends Application implements IGUIObserver{
    Stage window;
    Scene scene1, scene2;
    private final IWorldMap map = new GrassField(10);
    private GridPane grid = new GridPane();
    private ThreatedSimulationEngine engine;
    @Override
    public void init(){
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        this.engine = new ThreatedSimulationEngine(this.map, positions);
        engine.addObserver(this);
    }
    public void updateGrid(boolean first){
        this.grid.setGridLinesVisible(false);
        this.grid.setGridLinesVisible(true);
        Label label = new Label("y/x");
        this.grid.add(label, 0, 0);
        if (first){
            this.grid.getColumnConstraints().add(new ColumnConstraints(30));
            this.grid.getRowConstraints().add(new RowConstraints(30));
        }
        GridPane.setHalignment(label, HPos.CENTER);

        Vector2d ll = ((AbstractWorldMap) this.map).getLowerLeft();
        Vector2d ur = ((AbstractWorldMap) this.map).getUpperRight();
        //col
        for (int i = 0; i < ur.x - ll.x + 1; i++){
            label = new Label("" + (ll.x + i));
            this.grid.add(label, i+1, 0);
            if (first){
                this.grid.getColumnConstraints().add(new ColumnConstraints(50));
            }
            GridPane.setHalignment(label, HPos.CENTER);
        }
        //row
        for (int i = 0; i < ur.y - ll.y + 1; i++){
            label = new Label("" + (ur.y - ll.y - i));
            this.grid.add(label, 0, i+1);
            if (first){
                this.grid.getRowConstraints().add(new RowConstraints(50));
            }
            GridPane.setHalignment(label, HPos.CENTER);
        }
        //el
        for(int i = 0; i < ur.x - ll.x + 1; i++){
            for(int j = 0; j < ur.y - ll.y + 1; j++){
                IMapElement mapElement = (IMapElement) this.map.objectAt(new Vector2d((ll.x + i),(ur.y - j)));
                if (mapElement != null){
                    //label = new Label("" + mapElement);
                    GuiElementBox guiElementBox = new GuiElementBox(mapElement);
                    this.grid.add(guiElementBox.getvBox(), i+1,j+1);
                    //GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }



    }
    private VBox startScene(){
        TextField movesInput = new TextField("f b r l f f r r f f f f f f f f");
        movesInput.setAlignment(Pos.CENTER);
        movesInput.setPrefWidth(300);
        Button startButton = new Button("Start");
        startButton.setOnAction(e->{
            this.window.setScene(scene2);
            MoveDirection[] moves = new OptionsParser().parse(movesInput.getText().split(" "));
            this.engine.setMoves(moves);
            Thread engineThread = new Thread(this.engine);
            engineThread.start();
        });
        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(movesInput, startButton);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    @Override
    public void start(Stage primaryStage){
        this.window = primaryStage;
        this.scene1 = new Scene(startScene(), 400, 150);
        this.scene2 = new Scene(this.grid, 500, 600);

        this.window.setScene(scene1);
        this.window.show();
        updateGrid(true);
    }

    @Override
    public void updateGUI() {
        Platform.runLater(()->{
            this.grid.getChildren().clear();
            updateGrid(false);
        });

    }
}
