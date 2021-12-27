package agh.ics.oop;

import java.util.LinkedList;

public class Tracer {
    protected Animal animal;
    protected LinkedList<Animal> descendants;
    protected int numberOfChildren;
    protected int daysTraced;
    protected int epochOfDeath;
    protected boolean isActive;

    public Tracer(){
        this.isActive = false;
    }

    public void startTracking(Animal animal) {
        this.animal = animal;
        this.descendants = new LinkedList<>();
        this.numberOfChildren = 0;
        this.daysTraced = 0;
        this.epochOfDeath = -1;
        this.isActive = true;
    }

    public void stopTracking(){
        this.isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public int getNumberOfDescendants(){
        if (this.descendants != null)
            return this.descendants.size();
        return 0;
    }

    public String getEpochOfDeath(){
        StringBuilder resString = new StringBuilder();
        if (this.animal.isDead()){
            resString.append("Died in ");
            resString.append(this.epochOfDeath);
            resString.append(" epoch and lived through ");
            resString.append(this.daysTraced);
            resString.append(" days");
        }else {
            resString.append("Still alive and well");
        }
        return resString.toString();
    }
}
