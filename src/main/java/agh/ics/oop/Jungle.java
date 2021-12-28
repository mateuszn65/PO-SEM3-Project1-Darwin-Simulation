package agh.ics.oop;

public class Jungle {
    protected Vector2d lowerLeftJungle;
    protected Vector2d upperRightJungle;

    //CONSTRUCTOR
    public Jungle(Vector2d lowerLeft, Vector2d upperRight){
        this.lowerLeftJungle = lowerLeft;
        this.upperRightJungle = upperRight;
    }
    //SETTER
    public void setJungleCorners(Vector2d lowerLeft, Vector2d upperRight){
        this.lowerLeftJungle = lowerLeft;
        this.upperRightJungle = upperRight;
    }

    //CHECKS IF POSITION BELONGS TO JUNGLE
    public boolean belongsToJungle(Vector2d position) {
        return position.follows(this.lowerLeftJungle) && position.precedes(this.upperRightJungle);
    }
}
