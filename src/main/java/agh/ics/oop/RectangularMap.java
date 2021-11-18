package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

class RectangularMap implements IWorldMap{
    private final List<Animal>animals = new ArrayList<>();
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    public RectangularMap(int width, int height){
        this.lowerLeft = new Vector2d(0 ,0);
        this.upperRight = new Vector2d(width - 1, height - 1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.follows(this.lowerLeft) && position.precedes(this.upperRight));
    }

    @Override
    public boolean place(Animal animal) {
        if (isOccupied(animal.getPosition())){
            return false;
        }
        else{
            animals.add(animal);
            return true;
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for(Animal i : animals){
            if (i.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for(Animal i : animals){
            if (i.getPosition().equals(position)){
                return i;
            }
        }
        return null;
    }
    @Override
    public String toString(){
        MapVisualizer vis = new MapVisualizer(this);
        return vis.draw(this.lowerLeft, this.upperRight);
    }
}
