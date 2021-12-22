package agh.ics.oop;



public class WallMap extends AbstractWorldMap{
    public WallMap(){

    }

    @Override
    public Vector2d convertPosition(Vector2d position) {
        return position;
    }
}
