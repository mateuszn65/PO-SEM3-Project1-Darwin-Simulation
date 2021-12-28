package agh.ics.oop.gui;
import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application implements IGUIObserver{
    private Stage window;
    private Scene scene1, scene2;
    //Starting parameters
    private int mapWidth = 10;
    private int mapHeight = 10;
    private int jungleRatio = 40;
    private int startingNumberOfAnimals = 15;
    private int startEnergy = 50;
    private int moveEnergy = 1;
    private int plantEnergy = 5;
    private boolean wrappedMagic, wallMagic;
    private int cellSize = 25;
    //maps
    private final Jungle jungle = new Jungle(new Vector2d(this.mapWidth*(100-this.jungleRatio)/200,
            this.mapHeight*(100-this.jungleRatio)/200), new Vector2d(this.mapWidth*(100+this.jungleRatio)/200, this.mapHeight*(100+this.jungleRatio)/200));
    private final AbstractWorldMap wrappedMap = new WrappedMap();
    private final AbstractWorldMap wallMap = new WallMap();
    //display
    private MapGrid wrappedGrid;
    private MapGrid wallGrid;
    private StatsDisplay wrappedStats;
    private StatsDisplay wallStats;
    private final VBox wrappedMapVBox = new VBox(20);
    private final VBox wallMapVBox = new VBox(20);
    private final HBox mainBox = new HBox(20);
    //engines
    private SimulationEngine wrappedEngine;
    private SimulationEngine wallEngine;
    private Thread wrappedEngineThread;
    private Thread wallEngineThread;



    //SETS EVERYTHING FOR THE SIMULATION
    private void _init(){
        if (this.mapWidth > 20 || this.mapHeight > 20){
            if (this.mapWidth >= this.mapHeight)
                this.cellSize = 600/this.mapWidth;
            else
                this.cellSize = 600/this.mapHeight;
        }


        this.jungle.setJungleCorners(new Vector2d(this.mapWidth*(100-this.jungleRatio)/200, this.mapHeight*(100-this.jungleRatio)/200), new Vector2d(this.mapWidth*(100+this.jungleRatio)/200, this.mapHeight*(100+this.jungleRatio)/200));
        this.wrappedMap.setParameters(this.mapWidth, this.mapHeight, this.jungle, this.startingNumberOfAnimals, this.startEnergy, this.moveEnergy, this.plantEnergy);
        this.wallMap.setParameters(this.mapWidth, this.mapHeight, this.jungle, this.startingNumberOfAnimals, this.startEnergy, this.moveEnergy, this.plantEnergy);

        this.wrappedEngine = new SimulationEngine(this.wrappedMap);
        this.wallEngine = new SimulationEngine(this.wallMap);
        this.wrappedEngine.addObserver(this);
        this.wallEngine.addObserver(this);

        this.wrappedGrid = new MapGrid(this.wrappedMap, this.mapWidth, this.mapHeight, this.cellSize, this. jungle);
        this.wallGrid = new MapGrid(this.wallMap, this.mapWidth, this.mapHeight, this.cellSize, this. jungle);

        Button dominantsWrappedMap = new Button();
        dominantsWrappedMap.setOnAction(e->{
            if (dominantsWrappedMap.getText().contains("Show")){
                this.wrappedGrid.displayDominants(this.wrappedMap.getArrayDominantGenotype());
                dominantsWrappedMap.setText("Hide animals with \ndominant genotype");
            }
            else{
                this.wrappedGrid.updateGrid();
                dominantsWrappedMap.setText("Show animals with \ndominant genotype");
            }
        });
        Button saveWrappedMap = new Button("Save to file");
        saveWrappedMap.setOnAction(e -> this.wrappedStats.saveToFile());
        Button stopTrackingWrappedMap = new Button("Stop tracking");
        stopTrackingWrappedMap.setOnAction(e -> {
            this.wrappedMap.getTracer().stopTracking();
            this.wrappedStats.updateStats();
            stopTrackingWrappedMap.setVisible(false);
        });
        Button pauseWrappedMap = new Button();
        pauseWrappedMap.setOnAction(e->{
            this.wrappedEngine.tooglePaused();
            if (pauseWrappedMap.getText().contains("Unpause")){
                unpause(pauseWrappedMap, dominantsWrappedMap, saveWrappedMap, stopTrackingWrappedMap);
            }else {
                pause(pauseWrappedMap, dominantsWrappedMap, saveWrappedMap);
                if (this.wrappedMap.getTracer().isActive())
                    stopTrackingWrappedMap.setVisible(true);
            }
        });
        unpause(pauseWrappedMap, dominantsWrappedMap, saveWrappedMap, stopTrackingWrappedMap);
        Button dominantsWallMap = new Button();
        dominantsWallMap.setOnAction(e->{
            if (dominantsWallMap.getText().contains("Show")){
                this.wallGrid.displayDominants(this.wallMap.getArrayDominantGenotype());
                dominantsWallMap.setText("Hide animals with \ndominant genotype");
            }
            else{
                this.wallGrid.updateGrid();
                dominantsWallMap.setText("Show animals with \ndominant genotype");
            }
        });
        Button saveWallMap = new Button("Save to file");
        saveWallMap.setOnAction(e -> this.wallStats.saveToFile());
        Button stopTrackingWallMap = new Button("Stop tracking");
        stopTrackingWallMap.setOnAction(e -> {
            this.wallMap.getTracer().stopTracking();
            this.wallStats.updateStats();
            stopTrackingWallMap.setVisible(false);
        });
        Button pauseWallMap = new Button();
        pauseWallMap.setOnAction(e->{
            this.wallEngine.tooglePaused();
            if (pauseWallMap.getText().contains("Unpause")){
                unpause(pauseWallMap, dominantsWallMap, saveWallMap, stopTrackingWallMap);
            }else {
                pause(pauseWallMap, dominantsWallMap, saveWallMap);
                if (this.wallMap.getTracer().isActive())
                    stopTrackingWallMap.setVisible(true);
            }
        });
        unpause(pauseWallMap, dominantsWallMap, saveWallMap, stopTrackingWallMap);
        this.wrappedMapVBox.getChildren().addAll(this.wrappedGrid.getGrid(), pauseWrappedMap, dominantsWrappedMap, saveWrappedMap, stopTrackingWrappedMap);
        this.wallMapVBox.getChildren().addAll(this.wallGrid.getGrid(), pauseWallMap, dominantsWallMap, saveWallMap, stopTrackingWallMap);

        this.wrappedMapVBox.setAlignment(Pos.TOP_CENTER);
        this.wallMapVBox.setAlignment(Pos.TOP_CENTER);

        this.wrappedStats = new StatsDisplay(this.wrappedMap, this.wrappedMagic, "WrappedStats.csv");
        this.wallStats = new StatsDisplay(this.wallMap, this.wallMagic, "WallStats.csv");


        this.mainBox.getChildren().addAll(this.wrappedStats.getStats(), this.wrappedMapVBox, this.wallStats.getStats(), this.wallMapVBox);



        this.scene2 = new Scene(this.mainBox, 800+2*this.cellSize*this.mapWidth, 600+2*this.cellSize*this.mapHeight);

        this.window.setScene(scene2);
        this.window.setMaximized(true);


        this.wrappedEngineThread = new Thread(this.wrappedEngine);
        this.wallEngineThread = new Thread(this.wallEngine);
        wrappedEngineThread.start();
        wallEngineThread.start();
    }
    //HELPER FUNCTIONS FOR BUTTONS
    private void unpause(Button pause, Button dominant, Button save, Button stop){
        pause.setText("Pause map");
        dominant.setVisible(false);
        save.setVisible(false);
        stop.setVisible(false);
    }
    private void pause(Button button, Button dominant, Button save){
        button.setText("Unpause map");
        dominant.setText("Show animals with \ndominant genotype");
        dominant.setVisible(true);
        save.setVisible(true);
    }

    //HANDLES USERS INPUT
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

        CheckBox wrappedCheckBox = new CheckBox("Wrapped Map - Magic Evolution type");
        CheckBox wallCheckBox = new CheckBox("Wall Map - Magic Evolution type");

        Button startButton = new Button("Start");
        HBox startBox = new HBox(startButton);
        startBox.setAlignment(Pos.CENTER);
        startButton.setOnAction(e->{
            try{
                this.mapWidth = Integer.parseInt(mapWidthInput.getText());
                this.mapHeight = Integer.parseInt(mapHeightInput.getText());
                this.jungleRatio = Integer.parseInt(jungleRatioInput.getText());
                this.startingNumberOfAnimals = Integer.parseInt(startingNumberOfAnimalsInput.getText());
                this.startEnergy = Integer.parseInt(startEnergyInput.getText());
                this.moveEnergy = Integer.parseInt(moveEnergyInput.getText());
                this.plantEnergy = Integer.parseInt(plantEnergyInput.getText());
                this.wrappedMagic = wrappedCheckBox.isSelected();
                this.wallMagic = wallCheckBox.isSelected();
                if (this.mapWidth <= 0 || this.mapWidth > 40 ||
                        this.mapHeight <= 0 || this.mapHeight > 40 ||
                        this.jungleRatio <= 0 || this.jungleRatio > 100 ||
                        this.startingNumberOfAnimals <= 0 || this.startingNumberOfAnimals > this.mapWidth * this.mapHeight ||
                        this.startEnergy <= 0 ||
                        this.moveEnergy < 0)
                    throw new IllegalArgumentException("Incorrect input, try more appropriate range of input");
                _init();



            }catch (NumberFormatException ex){
                AlertBox.display("Wrong Input", "Can't convert input into Integer");
            }catch (IllegalArgumentException ex){
                AlertBox.display("Incorrect Input", ex.getMessage());
            }

        });


        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(form1, form2, form3, form4, form5, form6, form7, wrappedCheckBox, wallCheckBox, startBox);
        vBox.setStyle("-fx-padding: 20;");
        return vBox;
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
    public void start(Stage primaryStage){
        this.window = primaryStage;
        this.window.setTitle("Evolution simulation");
        this.scene1 = new Scene(startScene(), 400, 600);

        this.window.setScene(scene1);
        this.window.show();
    }

    @Override
    public void stop() throws Exception {
        if (this.window.getScene() == this.scene2){
            this.wallEngineThread.interrupt();
            this.wrappedEngineThread.interrupt();
        }
        super.stop();
    }
}
