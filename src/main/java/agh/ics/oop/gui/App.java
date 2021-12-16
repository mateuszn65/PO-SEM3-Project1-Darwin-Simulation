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

    private WallMap map = new WallMap();
    private Jungle jungle = new Jungle(new Vector2d(this.mapWidth*(100-this.jungleRatio)/200, this.mapHeight*(100-this.jungleRatio)/200), new Vector2d(this.mapWidth*(100+this.jungleRatio)/200, this.mapHeight*(100+this.jungleRatio)/200));
    private GridPane grid = new GridPane();
    private VBox stats = new VBox(20);
    private VBox map1 = new VBox(20);
    private HBox mainBox = new HBox(20);
    private SimulationEngine engine;
    @Override
    public void init(){
        this.stats.setMinWidth(200);
        this.stats.setStyle("-fx-padding: 10;");
        this.grid.setStyle("-fx-padding: 10;");

        Button pauseMap1 = new Button("Pause map 1");
        pauseMap1.setOnAction(e->{
            this.engine.tooglePaused();

            if (pauseMap1.getText().contains("Unpause")){
                pauseMap1.setText("Pause map 1");
            }else {
                pauseMap1.setText("Unpause map 1");
            }
        });
        this.map1.getChildren().addAll(this.grid, pauseMap1);
        this.map1.setAlignment(Pos.TOP_CENTER);
        this.mainBox.getChildren().addAll(this.stats, this.map1);


    }
    public void updateGrid(){

        this.grid.getColumnConstraints().clear();
        this.grid.getRowConstraints().clear();

        Label label = new Label("y/x");
        this.grid.add(label, 0, 0);
        this.grid.getColumnConstraints().add(new ColumnConstraints(20));
        this.grid.getRowConstraints().add(new RowConstraints(20));
        GridPane.setHalignment(label, HPos.CENTER);

        Vector2d ll = new Vector2d(0 ,0);;
        Vector2d ur = new Vector2d(this.mapWidth - 1, this.mapHeight - 1);
        //col
        for (int i = 0; i < ur.x - ll.x + 1; i++){
            label = new Label("" + (ll.x + i));
            this.grid.add(label, i+1, 0);
            this.grid.getColumnConstraints().add(new ColumnConstraints(20));
            GridPane.setHalignment(label, HPos.CENTER);
        }
        //row
        for (int i = 0; i < ur.y - ll.y + 1; i++){
            label = new Label("" + (ur.y - ll.y - i));
            this.grid.add(label, 0, i+1);
            this.grid.getRowConstraints().add(new RowConstraints(20));
            GridPane.setHalignment(label, HPos.CENTER);
        }
        //el
        for(int i = 0; i < ur.x - ll.x + 1; i++){
            for(int j = 0; j < ur.y - ll.y + 1; j++){

                IMapElement mapElement = this.map.objectAt(new Vector2d((ll.x + i),(ur.y - j)));
                if (mapElement != null){
                    //label = new Label("" + mapElement);
                    GuiElementBox guiElementBox = new GuiElementBox(mapElement, this.map, Color.DARKGREEN);
                    this.grid.add(guiElementBox.getvBox(), i+1,j+1);
                    //GridPane.setHalignment(label, HPos.CENTER);
                }else if (this.jungle.belongsToJungle(new Vector2d(i,j))){
                    GuiElementBox guiElementBox = new GuiElementBox(mapElement, this.map, Color.LIGHTGREEN);
                    this.grid.add(guiElementBox.getvBox(), i+1,j+1);
                }else{
                    GuiElementBox guiElementBox = new GuiElementBox(mapElement, this.map, Color.YELLOW);
                    this.grid.add(guiElementBox.getvBox(), i+1,j+1);
                }
            }
        }



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

                this.scene2 = new Scene(this.mainBox, 300+20*this.mapWidth, 300+20*this.mapHeight);
                this.window.setScene(scene2);



                this.jungle.setJungleCorners(new Vector2d(this.mapWidth*(100-this.jungleRatio)/200, this.mapHeight*(100-this.jungleRatio)/200), new Vector2d(this.mapWidth*(100+this.jungleRatio)/200, this.mapHeight*(100+this.jungleRatio)/200));
                this.map.setParameters(this.mapWidth, this.mapHeight, this.jungle, this.startingNumberOfAnimals, this.startEnergy, this.moveEnergy, this.plantEnergy);

                this.engine = new SimulationEngine(this.map);
                this.engine.addObserver(this);
                Thread engineThread = new Thread(this.engine);
                engineThread.start();
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

    private void updateStats(){
        Label numOfAniamls = new Label("Number of Animals:  " + this.map.getNumberOfAnimals());
        Label numOfGrass = new Label("Number of Grasses:  " + this.map.getNumberOfGrass());
        Label averageEnergy = new Label("Average Energy:  " + this.map.getAverageEnergy());
        Label averageLengthOfLife = new Label();
        if (this.map.getTotalNumberOfDeadAnimals()>0){
            averageLengthOfLife.setText("Average Length of Life:  " + this.map.getAverageLengthOfLife());
        }else {
            averageLengthOfLife.setText("Average Length of Life:  ");
        }
        Label averageChildren = new Label("Average Number of Children:  " + this.map.getAverageChildren());


        this.stats.getChildren().addAll(numOfAniamls, numOfGrass, averageEnergy, averageLengthOfLife, averageChildren);
    }

    @Override
    public void start(Stage primaryStage){
        this.window = primaryStage;
        this.window.setTitle("Evolution simulation");
        this.scene1 = new Scene(startScene(), 400, 600);


        this.window.setScene(scene1);
        this.window.show();
        updateGrid();
    }

    @Override
    public void updateGUI() {
        Platform.runLater(()->{
            this.grid.getChildren().clear();
            updateGrid();
            this.stats.getChildren().clear();
            updateStats();
        });

    }
}
