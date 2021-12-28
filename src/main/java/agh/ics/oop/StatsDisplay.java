package agh.ics.oop;

import agh.ics.oop.gui.AlertBox;
import agh.ics.oop.gui.EpochStats;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class StatsDisplay {
    private final VBox stats;
    private final AbstractWorldMap map;
    private final LineChart<Number,Number> plot;
    private final XYChart.Series<Number,Number> dataSeriesAnimals;
    private final XYChart.Series<Number,Number> dataSeriesGrass;
    private final NumberAxis xAxis;
    private final boolean magic;
    private int magicCounter = 0;
    private int displayCounter = 0;
    private final LinkedList<EpochStats> data;
    private final EpochStats sumStats = new EpochStats(0 ,0 ,0 ,0 ,0 ,0);
    private final String fileName;

    public StatsDisplay(AbstractWorldMap map, boolean magic, String fileName){
        this.map = map;
        this.magic = magic;
        this.fileName = fileName;
        this.data = new LinkedList<>();

        int epoch = this.map.getNumberOfDays();
        int noAnimals = this.map.getNumberOfAnimals();
        int noGrass = this.map.getNumberOfGrass();
        int avgEnergy = this.map.getAverageEnergy();
        int avgLifeTime = this.map.getAverageLengthOfLife();
        float avgNoChildren = this.map.getAverageChildren();
        this.data.add(new EpochStats(epoch, noAnimals, noGrass, avgEnergy, avgLifeTime, avgNoChildren));

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
        this.stats.getChildren().add(this.plot);
        if (this.magic){
            if (this.map.getNumberOfAnimals() == 5 && this.magicCounter < 3){
                this.map.magicEvolution();
                this.magicCounter++;
                this.displayCounter = 1;
            }
            if (this.displayCounter > 0 && this.displayCounter < 200){
                Label magicLabel = new Label("MAGIC HAPPEND!!!");
                this.stats.getChildren().add(magicLabel);
                this.displayCounter++;
            }
        }
        int epoch = this.map.getNumberOfDays();
        int noAnimals = this.map.getNumberOfAnimals();
        int noGrass = this.map.getNumberOfGrass();
        int avgEnergy = this.map.getAverageEnergy();
        int avgLifeTime = this.map.getAverageLengthOfLife();
        float avgNoChildren = this.map.getAverageChildren();

        Label numOfAniamls = new Label("Number of Animals:  " + noAnimals);
        Label numOfGrass = new Label("Number of Grasses:  " + noGrass);
        Label dominantGenotype = new Label("Dominant Genotype:  " + this.map.getDominantGenotype());
        Label averageEnergy = new Label("Average Energy:  " + avgEnergy);
        Label averageLengthOfLife = new Label("Average Length of Life:  " + avgLifeTime);
        Label averageChildren = new Label("Average Number of Children:  " + avgNoChildren);
        this.stats.getChildren().addAll(numOfAniamls, numOfGrass, dominantGenotype, averageEnergy, averageLengthOfLife, averageChildren);
        if (this.data.getLast().getEpoch() != epoch){
            this.data.add(new EpochStats(epoch, noAnimals, noGrass, avgEnergy, avgLifeTime, avgNoChildren));
            this.sumStats.sumEpochStats(epoch, noAnimals, noGrass, avgEnergy, avgLifeTime, avgNoChildren);
        }
    }
    public void updateStats(){
        this.stats.getChildren().clear();
        _updateStats();
        if (this.map.tracer.isActive){
            _updateTracingStats();
        }
    }

    private void _updateTracingStats(){
        Label chosenAnimal = new Label("TRACED ANIMAL:   " + this.map.tracer.animal);
        chosenAnimal.setAlignment(Pos.CENTER);
        Label numberOfChildren = new Label("Number of children:  " + this.map.tracer.getNumberOfChildren());
        Label numberOfDescendants = new Label("Number od descendants:  " + this.map.tracer.getNumberOfDescendants());
        Label epochOfDeath = new Label(this.map.tracer.getEpochOfDeath());
        this.stats.getChildren().addAll(chosenAnimal, numberOfChildren, numberOfDescendants, epochOfDeath);
    }

    public void saveToFile(){
        try (PrintWriter writer = new PrintWriter(this.fileName)) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.sumStats.headers());
            for (EpochStats epochStats : this.data){
                sb.append(epochStats.toString());
            }
            if (this.data.size() > 0)
                sb.append(this.sumStats.avgToString(this.data.size()));
            writer.write(sb.toString());
            AlertBox.display("File saved", "Stats succesfully saved to file");
        } catch (IOException ex) {
            AlertBox.display("Failed to save", "Failed to save stats to file\n" + ex.getMessage());
        }

    }

    public VBox getStats() {
        return this.stats;
    }
}
