package agh.ics.oop;

import agh.ics.oop.gui.App;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable{
    private final int moveDelay = 700;
    protected MoveDirection[] moves;
    protected AbstractWorldMap map;
    protected final List<Animal> animals = new ArrayList<>();
    protected List<IGUIObserver> observers = new ArrayList<>();
    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] positions){
        this.moves = moves;
        this.map = (AbstractWorldMap) map;
        placeAnimals(positions);
    }
    public SimulationEngine(IWorldMap map, Vector2d[] positions){
        this.map = (AbstractWorldMap) map;
        placeAnimals(positions);
    }
    private void placeAnimals(Vector2d[] positions){
        for(Vector2d pos : positions){
            Animal a = new Animal(this.map, pos);
            a.addObserver(this.map);
            if(this.map.place(a)){
                animals.add(a);
            }
        }
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
        Vector2d v1 = new Vector2d(0,0);
        Vector2d v2= new Vector2d(0,0);
        int n = animals.size();
        for (int i = 0; i < this.moves.length; i++){
            animals.get(i % n).move(moves[i]);
            for (IGUIObserver observer: this.observers){
                observer.updateGUI();
            }
            if (i % n == 0){
                System.out.println(this.map);
            }
            try{
                Thread.sleep(this.moveDelay);
            }catch (InterruptedException ex){
                System.out.println("Something went wrong");
            }
        }
        System.out.println(this.map);
    }
}
