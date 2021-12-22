package agh.ics.oop;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatsDisplay {
    private final VBox stats;
    private final AbstractWorldMap map;
    public StatsDisplay(AbstractWorldMap map){
        this.map = map;
        this.stats = new VBox(20);
        this.stats.setMinWidth(200);
        this.stats.setStyle("-fx-padding: 10;");
        _updateStats();
    }

    private void _updateStats(){
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
    public void updateStats(){
        this.stats.getChildren().clear();
        _updateStats();
    }

    public VBox getStats() {
        return this.stats;
    }
}
