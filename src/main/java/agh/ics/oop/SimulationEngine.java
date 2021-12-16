package agh.ics.oop;

import agh.ics.oop.gui.App;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable{
    private final int moveDelay = 30;
    protected WallMap map;
    protected final List<Animal> animals = new ArrayList<>();
    protected List<IGUIObserver> observers = new ArrayList<>();
    private boolean paused = false;

    public void tooglePaused() {
        this.paused = !this.paused;
    }

    public SimulationEngine(WallMap map){
        this.map = (WallMap) map;
        this.map.randomlyPlaceAnimals();
    }

    public void addObserver(IGUIObserver observer){
        this.observers.add(observer);
    }
    public void removeObserver(IGUIObserver observer) {
        this.observers.remove(observer);
    }
    public Animal getAnimal(int i) {
        return animals.get(i);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            this.map.removeDeadAnimals();
            this.map.addGrass();
            Animal[] animals = this.map.animalsList.toArray(new Animal[0]);
            for (Animal animal : animals) {
                animal.move(animal.genes.getRandomGen());
                for (IGUIObserver observer : this.observers) {
                    observer.updateGUI();
                }
                try {
                    Thread.sleep(this.moveDelay);
                } catch (InterruptedException ex) {
                    System.out.println("Something went wrong");
                }
            }
            this.map.checkForEating();
            this.map.nextDay();
            this.map.checkForCopulation();
            while (this.paused){
                Thread.onSpinWait();
            }
        }

    }
}
