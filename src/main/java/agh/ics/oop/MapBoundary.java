package agh.ics.oop;

import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{
    public final SortedSet<IMapElement> xElements = new TreeSet<IMapElement>(new XComperator());
    public final SortedSet<IMapElement> yElements = new TreeSet<IMapElement>(new YComperator());
    private final IWorldMap map;
    public MapBoundary(IWorldMap map){
        this.map = map;
    }
    public void positionChange(Vector2d oldPosition, Vector2d newPosition){
        IMapElement tmp = (IMapElement) this.map.objectAt(newPosition);
        removeMapElement(tmp);
        addMapElement(tmp);
    }
    public void addMapElement(IMapElement mapElement){
        this.xElements.add(mapElement);
        this.yElements.add(mapElement);
    }
    public void removeMapElement(IMapElement mapElement){
        this.xElements.remove(mapElement);
        this.yElements.remove(mapElement);
    }
    public Vector2d getLowerLeft(){
        return xElements.first().getPosition().lowerLeft(yElements.first().getPosition());
    }
    public Vector2d getUpperRight(){
        return xElements.last().getPosition().upperRight(yElements.last().getPosition());
    }
}
