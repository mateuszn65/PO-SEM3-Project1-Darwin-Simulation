package agh.ics.oop;

public class Grass extends AbstractMapElement{
    public Grass(Vector2d position){
        super(position);
    }
    @Override
    public Boolean canWalkOver() {
        return true;
    }
    @Override
    public String toString() {
        return "*";
    }
}
