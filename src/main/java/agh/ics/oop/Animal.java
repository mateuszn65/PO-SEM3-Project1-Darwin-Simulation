package agh.ics.oop;


import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractMapElement{
    protected int energy, startEnergy, daysLived = 0, children = 0;
    protected Genes genes = new Genes();
    private MapDirection direction = MapDirection.getRandomDirection();
    private final IWorldMap map;
    protected List<IPositionChangeObserver> observers = new ArrayList<>();


    //CONSTRUCTOR
    public Animal(IWorldMap map, Vector2d initialPosition, int startEnergy){
        super(initialPosition);
        this.map = map;
        this.startEnergy = startEnergy;
        this.energy = startEnergy;
    }


    //GETTERS
    public int[] getArrayGenotype(){
        return this.genes.genotype;
    }
    public String getGenotype(){
        StringBuilder resString = new StringBuilder();
        for (int i = 0; i < this.genes.genotype.length; i++){
            resString.append(this.genes.genotype[i]);
            resString.append(" ");
        }
        return resString.toString();

    }
    public Color getColor(){
        if (this.energy == 0) return Color.ANTIQUEWHITE;
        if (this.energy < 0.5 * this.startEnergy) return Color.LIGHTGREY;
        if (this.energy < 0.75 * this.startEnergy) return Color.GRAY;
        if (this.energy < 1.25 * this.startEnergy) return Color.BROWN;
        if (this.energy < 1.75 * this.startEnergy) return Color.FIREBRICK;
        return Color.BLUEVIOLET;

    }
    public String toString(){
        return switch (this.direction){
            case EAST -> this.energy + " > ";
            case WEST -> this.energy + " < ";
            case SOUTH -> this.energy + " v ";
            case NORTH -> this.energy + " ^ ";
            case NORTHWEST -> this.energy + " <^ ";
            case SOUTHWEST -> this.energy + " <v ";
            case NORTHEAST -> this.energy + " ^> ";
            case SOUTHEAST -> this.energy + " v> ";
        };
    }


    //CHECKING STATE AND CHENCHING ENERGY
    public boolean isDead() {
        return this.energy <= 0;
    }
    public void changeEnergy(int value) {
        this.energy += value;
        if (this.energy < 0) {
            this.energy = 0;
        }
    }


    //PARENTS LOSING ENERGY FOR THE CHILD - RETURNS CHILD
    public Animal copulation(Animal parent){
        int childEnergy = (int) (0.25*this.energy + 0.25* parent.energy);
        changeEnergy((int) (-0.25*this.energy));
        parent.changeEnergy((int) (-0.25*parent.energy));
        Animal child = new Animal(this.map, this.position, childEnergy);
        child.genes = new Genes(this, parent);
        return child;
    }

    //MOVING
    public void move(int x){
        switch (x){
            case 0 -> {
                Vector2d f = this.position.add(this.direction.toUnitVector());
                f = this.map.convertPosition(f);
                if (this.map.canMoveTo(f)) {
                    Vector2d oldPosition = getPosition();
                    this.position = f;
                    positionChange(oldPosition);
                }
            }
            case 1 -> this.direction = this.direction.next();
            case 2 ->{
                for (int i = 0; i < 2; i++) {
                    this.direction = this.direction.next();
                }
            }
            case 3 ->{
                for (int i = 0; i < 3; i++) {
                    this.direction = this.direction.next();
                }
            }
            case 4 ->{
                Vector2d b = this.position.subtract(this.direction.toUnitVector());
                b = this.map.convertPosition(b);
                if (this.map.canMoveTo(b)) {
                    Vector2d oldPosition = getPosition();
                    this.position = b;
                    positionChange(oldPosition);
                }
            }
            case 5 ->{
                for (int i = 0; i < 3; i++) {
                    this.direction = this.direction.previous();
                }
            }
            case 6 ->{
                for (int i = 0; i < 2; i++) {
                    this.direction = this.direction.previous();
                }
            }
            case 7 -> this.direction = this.direction.previous();
            default -> System.out.println("Something wrong with swich move");
        }
    }


    //OBSERVERS
    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }
    public void positionChange(Vector2d oldPosition){
        for (IPositionChangeObserver observer: this.observers){
            observer.positionChange(this, oldPosition, this.getPosition());
        }
    }
}
