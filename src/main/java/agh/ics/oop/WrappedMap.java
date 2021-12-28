package agh.ics.oop;

public class WrappedMap extends AbstractWorldMap{
    //CONSTRUCTOR
    public WrappedMap(){}

    //RETURNS OPPOSITE SITE WHEN CROSSED THE EDGE
    @Override
    public Vector2d convertPosition(Vector2d position) {
        if (!canMoveTo(position)){
            if (position.x < 0)
                return position.add(new Vector2d(this.mapWidth, 0));
            if (position.x >= this.mapWidth)
                return position.subtract(new Vector2d(this.mapWidth, 0));
            if (position.y < 0)
                return position.add(new Vector2d(0, this.mapHeight));
            if (position.y >= this.mapHeight)
                return position.subtract(new Vector2d(0, this.mapHeight));
        }
        return position;
    }
}


