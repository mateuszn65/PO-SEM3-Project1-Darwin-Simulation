package agh.ics.oop;

import agh.ics.oop.gui.App;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable{
    private final int moveDelay = 30;
    protected AbstractWorldMap map;
    protected final List<Animal> animals = new ArrayList<>();
    protected List<IGUIObserver> observers = new ArrayList<>();
    private boolean paused = false;

    public void tooglePaused() {
        this.paused = !this.paused;
    }

    public SimulationEngine(AbstractWorldMap map){
        this.map = map;
        this.map.randomlyPlaceAnimals();
    }

    public void addObserver(IGUIObserver observer){
        this.observers.add(observer);
    }
    public void removeObserver(IGUIObserver observer) {
        this.observers.remove(observer);
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                this.map.removeDeadAnimals();
                this.map.addGrass();
                Animal[] animals = this.map.animalsList.toArray(new Animal[0]);
                for (Animal animal : animals) {
                    //pause here?

                    animal.move(animal.genes.getRandomGen());
                    for (IGUIObserver observer : this.observers) {
                        observer.updateGUI(this.map instanceof WrappedMap);
                    }
                    Thread.sleep(this.moveDelay);
                }
                this.map.checkForEating();
                this.map.nextDay();
                this.map.checkForCopulation();
                // or here?
                while (this.paused){
                    Thread.onSpinWait();
                }
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
}
