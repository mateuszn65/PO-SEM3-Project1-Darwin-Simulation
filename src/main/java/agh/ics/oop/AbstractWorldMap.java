package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected Map<Vector2d, IMapElement> mapElements = new LinkedHashMap<>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;


    @Override
    public void positionChange(Animal animal,Vector2d oldPosition, Vector2d newPosition) {
        this.mapElements.remove(oldPosition);
        this.mapElements.put(newPosition, animal);

    }
    public boolean canMoveTo(Vector2d position) {
        if (isOccupied(position)){
            return (position.follows(this.lowerLeft) &&
                    position.precedes(this.upperRight) &&
                    objectAt(position) instanceof Grass);
        }
        return (position.follows(this.lowerLeft) &&
                position.precedes(this.upperRight));
    }
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            this.mapElements.put(animal.getPosition(), animal);
            return true;
        }else{
            throw new IllegalArgumentException("Animal on position " + animal.getPosition() + " is not valid");
        }

    }
    public boolean isOccupied(Vector2d position) {
        return this.mapElements.containsKey(position);
    }
    public IMapElement objectAt(Vector2d position) {
        if (isOccupied(position)){
            return this.mapElements.get(position);
        }
        return null;
    }
    public abstract Vector2d getLowerLeft();
    public abstract Vector2d getUpperRight();

}
