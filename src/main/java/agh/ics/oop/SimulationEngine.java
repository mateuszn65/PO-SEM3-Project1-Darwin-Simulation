package agh.ics.oop;


import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable{
    private final int moveDelay = 30;
    protected AbstractWorldMap map;
    protected List<IGUIObserver> observers = new ArrayList<>();
    private boolean paused = false;

    //CONSTRUCTOR
    public SimulationEngine(AbstractWorldMap map){
        this.map = map;
        this.map.randomlyPlaceAnimals();
    }


    public void tooglePaused() {
        this.paused = !this.paused;
    }

    //OBSERVERS
    public void addObserver(IGUIObserver observer){
        this.observers.add(observer);
    }
    public void removeObserver(IGUIObserver observer) {
        this.observers.remove(observer);
    }
    public void guiChange(){
        for (IGUIObserver observer : this.observers) {
            observer.updateGUI(this.map instanceof WrappedMap);
        }
    }

    //HEARTH OF THE ENGINE
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                this.map.addGrass();
                guiChange();
                Animal[] animals = this.map.animalsList.toArray(new Animal[0]);
                if (animals.length == 0)
                    break;
                for (Animal animal : animals) {
                    animal.move(animal.genes.getRandomGen());
                    guiChange();
                    Thread.sleep(this.moveDelay);
                }
                this.map.checkForEating();
                this.map.nextDay();
                this.map.checkForCopulation();
                this.map.removeDeadAnimals();
                guiChange();
                while (this.paused && !Thread.currentThread().isInterrupted()){
                    Thread.onSpinWait();
                }
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
}
