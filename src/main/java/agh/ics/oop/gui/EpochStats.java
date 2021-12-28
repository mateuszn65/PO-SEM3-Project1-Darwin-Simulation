package agh.ics.oop.gui;

public class EpochStats {
    private int epoch;
    private int noAnimals;
    private int noGrass;
    private int avgEnergy;
    private int avgLifeTime;
    private float avgNoChildren;

    public EpochStats(int epoch, int noAnimals, int noGrass, int avgEnergy, int avgLifeTime, float avgNoChildren){
        this.epoch = epoch;
        this.noAnimals = noAnimals;
        this.noGrass = noGrass;
        this.avgEnergy = avgEnergy;
        this.avgLifeTime = avgLifeTime;
        this.avgNoChildren = avgNoChildren;
    }

    public void sumEpochStats(int epoch, int noAnimals, int noGrass, int avgEnergy, int avgLifeTime, float avgNoChildren){
        this.epoch += epoch;
        this.noAnimals += noAnimals;
        this.noGrass += noGrass;
        this.avgEnergy += avgEnergy;
        this.avgLifeTime += avgLifeTime;
        this.avgNoChildren += avgNoChildren;
    }

    public int getEpoch() {
        return epoch;
    }

    @Override
    public String toString() {
        return this.epoch + ","
        + this.noAnimals + ","
        + this.noGrass + ","
        + this.avgEnergy + ","
        + this.avgLifeTime + ","
        + this.avgNoChildren + System.lineSeparator();
    }

    public String avgToString(int n){
        return "TotalAVG" + ","
                + this.noAnimals/n + ","
                + this.noGrass/n + ","
                + this.avgEnergy/n + ","
                + this.avgLifeTime/n + ","
                + this.avgNoChildren/n + System.lineSeparator();
    }

    public String headers(){
        return "Epoch" + ","
                + "NoAnimals" + ","
                + "NoGrass" + ","
                + "AvgEnergy" + ","
                + "AvgLifeTime" + ","
                + "AvgNoChildren" + System.lineSeparator();
    }
}
