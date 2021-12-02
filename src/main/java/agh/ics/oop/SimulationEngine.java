package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine{
    private final MoveDirection[] moves;
    private final AbstractWorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] positions){
        this.moves = moves;
        this.map = (AbstractWorldMap) map;
        for(Vector2d pos : positions){
            Animal a = new Animal(this.map, pos);
            a.addObserver(this.map);
            if(this.map.place(a)){
                animals.add(a);
            }
        }
    }

    public Animal getAnimal(int i) {
        return animals.get(i);
    }

    @Override
    public void run() {
        int n = animals.size();
        for (int i = 0; i < this.moves.length; i++){
            if (i % n == 0){
                System.out.println(this.map);
            }
            animals.get(i % n).move(moves[i]);
        }
        System.out.println(this.map);
    }
}
