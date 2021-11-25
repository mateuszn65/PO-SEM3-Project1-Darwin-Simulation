package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap{
    protected List<IMapElement>mapElements = new ArrayList<>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected MapVisualizer vis = new MapVisualizer(this);

    public boolean canMoveTo(Vector2d position) {
        if (isOccupied(position)){
            return (position.follows(this.lowerLeft) &&
                    position.precedes(this.upperRight) &&
                    objectAt(position).canWalkOver());
        }
        return (position.follows(this.lowerLeft) &&
                position.precedes(this.upperRight));
    }
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            this.mapElements.add(animal);
            return true;
        }
        return false;
    }
    public boolean isOccupied(Vector2d position) {
        for(IMapElement e : mapElements){
            if (e.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }
    public IMapElement objectAt(Vector2d position) {
        IMapElement res = null;
        for(IMapElement e : mapElements){
            if (e.getPosition().equals(position)){
                if (e instanceof Animal) {
                    return e;
                }
                res = e;
            }
        }
        return res;
    }
    abstract Vector2d getLowerLeft();
    abstract Vector2d getUpperRight();
    public String toString(){
        return this.vis.draw(getLowerLeft(), getUpperRight());
    }
}
