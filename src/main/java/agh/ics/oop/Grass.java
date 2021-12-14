package agh.ics.oop;

public class Grass extends AbstractMapElement{
    public Grass(Vector2d position){
        super(position);
    }

    @Override
    public String getImagePath() {
        return "src/main/resources/grass.png";
    }

    @Override
    public String toString() {
        return "*";
    }

}
