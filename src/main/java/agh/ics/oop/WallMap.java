package agh.ics.oop;



public class WallMap extends AbstractWorldMap{
    //CONSTRUCTOR
    public WallMap(){}

    //RETURNS DEFAULT POSITION
    @Override
    public Vector2d convertPosition(Vector2d position) {
        return position;
    }
}
