package agh.ics.oop;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatsDisplay {
    private final VBox stats;
    private final AbstractWorldMap map;
    private final LineChart<Number,Number> plot;
    private final XYChart.Series<Number,Number> dataSeriesAnimals;
    private final XYChart.Series<Number,Number> dataSeriesGrass;
    private final NumberAxis xAxis;
    public StatsDisplay(AbstractWorldMap map){
        this.map = map;
        this.stats = new VBox(20);
        this.stats.setMinWidth(200);
        this.stats.setStyle("-fx-padding: 10;");

        this.xAxis = new NumberAxis();
        this.xAxis.setAutoRanging(false);
        NumberAxis yAxis = new NumberAxis();


        this.plot = new LineChart<>(xAxis, yAxis);
        this.dataSeriesAnimals = new XYChart.Series<>();
        this.dataSeriesGrass = new XYChart.Series<>();
        this.dataSeriesAnimals.setName("Animals");
        this.dataSeriesGrass.setName("Grass");
        this.plot.getData().add(this.dataSeriesAnimals);
        this.plot.getData().add(this.dataSeriesGrass);
        this.plot.setCreateSymbols(false);
        _updateStats();
    }

    private void _updateStats(){
        int days = this.map.getNumberOfDays();
        this.dataSeriesAnimals.getData().add(new XYChart.Data<>(days, this.map.getNumberOfAnimals()));
        this.dataSeriesGrass.getData().add(new XYChart.Data<>(days, this.map.getNumberOfGrass()));
        if (days > 100){
            this.xAxis.setLowerBound(days - 100);
            this.xAxis.setUpperBound(days);

        }

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
        this.stats.getChildren().addAll(this.plot, numOfAniamls, numOfGrass, averageEnergy, averageLengthOfLife, averageChildren);
    }
    public void updateStats(){
        this.stats.getChildren().clear();
        _updateStats();
    }

    public VBox getStats() {
        return this.stats;
    }
}
