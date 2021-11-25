package agh.ics.oop;


public class RectangularMap extends AbstractWorldMap{
    public RectangularMap(int width, int height){
        this.lowerLeft = new Vector2d(0 ,0);
        this.upperRight = new Vector2d(width - 1, height - 1);
    }

    @Override
    public Vector2d getLowerLeft() {
        return this.lowerLeft;
    }

    @Override
    public Vector2d getUpperRight() {
        return this.upperRight;
    }


}
