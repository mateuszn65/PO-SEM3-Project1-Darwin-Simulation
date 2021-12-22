package agh.ics.oop.gui;
import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

public class App extends Application implements IGUIObserver{
    private Stage window;
    private Scene scene1, scene2;
    //Starting parameters
    private int mapWidth = 10,
            mapHeight = 10,
            jungleRatio = 40,
            startingNumberOfAnimals = 15,
            startEnergy = 50,
            moveEnergy = 1,
            plantEnergy = 5;
    private final int cellSize = 25;

    private final Jungle jungle = new Jungle(new Vector2d(this.mapWidth*(100-this.jungleRatio)/200, this.mapHeight*(100-this.jungleRatio)/200), new Vector2d(this.mapWidth*(100+this.jungleRatio)/200, this.mapHeight*(100+this.jungleRatio)/200));

    private final AbstractWorldMap wrappedMap = new WrappedMap();
    private final AbstractWorldMap wallMap = new WallMap();
    private MapGrid wrappedGrid;
    private MapGrid wallGrid;
    private StatsDisplay wrappedStats;
    private StatsDisplay wallStats;
    private final VBox wrappedMapVBox = new VBox(20);
    private final VBox wallMapVBox = new VBox(20);
    private final HBox mainBox = new HBox(20);
    private SimulationEngine wrappedEngine;
    private SimulationEngine wallEngine;
    private Thread wrappedEngineThread;
    private Thread wallEngineThread;


    @Override
    public void init(){

    }

    private void _init(){
        this.jungle.setJungleCorners(new Vector2d(this.mapWidth*(100-this.jungleRatio)/200, this.mapHeight*(100-this.jungleRatio)/200), new Vector2d(this.mapWidth*(100+this.jungleRatio)/200, this.mapHeight*(100+this.jungleRatio)/200));
        this.wrappedMap.setParameters(this.mapWidth, this.mapHeight, this.jungle, this.startingNumberOfAnimals, this.startEnergy, this.moveEnergy, this.plantEnergy);
        this.wallMap.setParameters(this.mapWidth, this.mapHeight, this.jungle, this.startingNumberOfAnimals, this.startEnergy, this.moveEnergy, this.plantEnergy);

        this.wrappedEngine = new SimulationEngine(this.wrappedMap);
        this.wallEngine = new SimulationEngine(this.wallMap);
        this.wrappedEngine.addObserver(this);
        this.wallEngine.addObserver(this);

        this.wrappedGrid = new MapGrid(this.wrappedMap, this.mapWidth, this.mapHeight, this.cellSize, this. jungle);
        this.wallGrid = new MapGrid(this.wallMap, this.mapWidth, this.mapHeight, this.cellSize, this. jungle);

        Button pauseWrappedMap = new Button("Pause map");
        pauseWrappedMap.setOnAction(e->{
            this.wrappedEngine.tooglePaused();
            if (pauseWrappedMap.getText().contains("Unpause")){
                pause(pauseWrappedMap);
            }else {
                unpause(pauseWrappedMap);
            }
        });
        Button pauseWallMap = new Button("Pause map");
        pauseWallMap.setOnAction(e->{
            this.wallEngine.tooglePaused();
            if (pauseWallMap.getText().contains("Unpause")){
                pause(pauseWallMap);
            }else {
                unpause(pauseWallMap);
            }
        });
        this.wrappedMapVBox.getChildren().addAll(this.wrappedGrid.getGrid(), pauseWrappedMap);
        this.wallMapVBox.getChildren().addAll(this.wallGrid.getGrid(), pauseWallMap);

        this.wrappedMapVBox.setAlignment(Pos.TOP_CENTER);
        this.wallMapVBox.setAlignment(Pos.TOP_CENTER);

        this.wrappedStats = new StatsDisplay(this.wrappedMap);
        this.wallStats = new StatsDisplay(this.wallMap);

        this.mainBox.getChildren().addAll(this.wrappedStats.getStats(), this.wrappedMapVBox, this.wallStats.getStats(), this.wallMapVBox);



        this.scene2 = new Scene(this.mainBox, 800+2*this.cellSize*this.mapWidth, 600+2*this.cellSize*this.mapHeight);
        this.window.setScene(scene2);



        this.wrappedEngineThread = new Thread(this.wrappedEngine);
        this.wallEngineThread = new Thread(this.wallEngine);
        wrappedEngineThread.start();
        wallEngineThread.start();
    }

    private void pause(Button button){
        button.setText("Pause map");
    }
    private void unpause(Button button){
        button.setText("Unpause map");
    }


    private VBox startScene(){

        Label widthLabel = new Label("Map Width");
        TextField mapWidthInput = new TextField("" + this.mapWidth);
        mapWidthInput.setAlignment(Pos.CENTER);
        HBox form1 = new HBox(widthLabel, mapWidthInput);
        form1.setSpacing(20);

        Label heightLabel = new Label("Map Height");
        TextField mapHeightInput = new TextField("" + this.mapHeight);
        mapHeightInput.setAlignment(Pos.CENTER);
        HBox form2 = new HBox(heightLabel, mapHeightInput);
        form2.setSpacing(20);

        Label jungleRatioLabel = new Label("Junge Ratio");
        TextField jungleRatioInput = new TextField("" + this.jungleRatio);
        jungleRatioInput.setAlignment(Pos.CENTER);
        Label procentLabel = new Label("% of the Map");
        HBox form3 = new HBox(jungleRatioLabel, jungleRatioInput, procentLabel);
        form3.setSpacing(20);

        Label startingNumberOfAnimalsLabel = new Label("Starting number of animals");
        TextField startingNumberOfAnimalsInput = new TextField("" + this.startingNumberOfAnimals);
        startingNumberOfAnimalsInput.setAlignment(Pos.CENTER);
        HBox form4 = new HBox(startingNumberOfAnimalsLabel, startingNumberOfAnimalsInput);
        form4.setSpacing(20);

        Label startEnergyLabel = new Label("Start Energy");
        TextField startEnergyInput = new TextField("" + this.startEnergy);
        startEnergyInput.setAlignment(Pos.CENTER);
        HBox form5 = new HBox(startEnergyLabel, startEnergyInput);
        form5.setSpacing(20);

        Label moveEnergyLabel = new Label("Move Energy");
        TextField moveEnergyInput = new TextField("" + this.moveEnergy);
        moveEnergyInput.setAlignment(Pos.CENTER);
        HBox form6 = new HBox(moveEnergyLabel, moveEnergyInput);
        form6.setSpacing(20);

        Label plantEnergyLabel = new Label("Plant Energy");
        TextField plantEnergyInput = new TextField("" + this.plantEnergy);
        plantEnergyInput.setAlignment(Pos.CENTER);
        HBox form7 = new HBox(plantEnergyLabel, plantEnergyInput);
        form7.setSpacing(20);

        Button startButton = new Button("Start");
        startButton.setOnAction(e->{
            try{
                this.mapWidth = Integer.parseInt(mapWidthInput.getText());
                this.mapHeight = Integer.parseInt(mapHeightInput.getText());
                this.jungleRatio = Integer.parseInt(jungleRatioInput.getText());
                this.startingNumberOfAnimals = Integer.parseInt(startingNumberOfAnimalsInput.getText());
                this.startEnergy = Integer.parseInt(startEnergyInput.getText());
                this.moveEnergy = Integer.parseInt(moveEnergyInput.getText());
                this.plantEnergy = Integer.parseInt(plantEnergyInput.getText());

                _init();



            }catch (NumberFormatException ex){
                AlertBox.display("Wrong Input", "Can't convert input into Integer");
            }
        });


        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(form1, form2, form3, form4, form5, form6, form7, startButton);
        vBox.setStyle("-fx-padding: 20;");
        vBox.setAlignment(Pos.TOP_RIGHT);
        return vBox;
    }


    @Override
    public void start(Stage primaryStage){
        this.window = primaryStage;
        this.window.setTitle("Evolution simulation");
        this.scene1 = new Scene(startScene(), 400, 600);


        this.window.setScene(scene1);
        this.window.show();
    }

    @Override
    public void updateGUI(boolean wrapped) {
        Platform.runLater(()->{
            if (wrapped){
                this.wrappedGrid.updateGrid();
                this.wrappedStats.updateStats();
            } else{
                this.wallGrid.updateGrid();
                this.wallStats.updateStats();
            }

        });
    }

    @Override
    public void stop() throws Exception {

        this.wallEngineThread.interrupt();
        this.wrappedEngineThread.interrupt();

        super.stop();
    }
}
