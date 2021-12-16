package agh.ics.oop;

public class Jungle {
    protected Vector2d lowerLeftJungle;
    protected Vector2d upperRightJungle;

    public Jungle(Vector2d lowerLeft, Vector2d upperRight){
        this.lowerLeftJungle = lowerLeft;
        this.upperRightJungle = upperRight;
    }
    public void setJungleCorners(Vector2d lowerLeft, Vector2d upperRight){
        this.lowerLeftJungle = lowerLeft;
        this.upperRightJungle = upperRight;
    }

    public boolean belongsToJungle(Vector2d vector) {
        return vector.follows(this.lowerLeftJungle) && vector.precedes(this.upperRightJungle);
    }
}
