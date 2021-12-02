package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{

    protected Map<Vector2d, IMapElement> mapElements = new LinkedHashMap<>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected MapVisualizer vis = new MapVisualizer(this);

    @Override
    public void positionChange(Vector2d oldPosition, Vector2d newPosition) {
        IMapElement e = objectAt(oldPosition);
        this.mapElements.remove(oldPosition);
        this.mapElements.put(newPosition, e);

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
        }
        return false;
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
    abstract Vector2d getLowerLeft();
    abstract Vector2d getUpperRight();
    public String toString(){
        return this.vis.draw(getLowerLeft(), getUpperRight());
    }
}
