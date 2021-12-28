package agh.ics.oop;

public abstract class AbstractMapElement implements IMapElement{
    protected Vector2d position;
    public AbstractMapElement(Vector2d position){
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

}
