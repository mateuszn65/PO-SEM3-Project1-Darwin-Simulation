package agh.ics.oop;


import java.util.Random;
public class GrassField extends AbstractWorldMap{
    private final int n;
    private final int bound;
    private final Random generator = new Random(42);

    public GrassField(int n){
        this.n = n;
        this.bound = (int) Math.sqrt(n*10);
        this.lowerLeft = new Vector2d(0 ,0);
        this.upperRight = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.randomGrass();
    }
    public void addGrass(){
        Vector2d randpos = new Vector2d(generator.nextInt(this.bound), generator.nextInt(this.bound));
        while (isOccupied(randpos)){
            randpos = new Vector2d(generator.nextInt(this.bound), generator.nextInt(this.bound));
        }
        this.mapElements.add(new Grass(randpos));
    }
    public void randomGrass(){
        for(int i = 0; i < n; i ++){
            addGrass();
        }
    }
    public Vector2d getLowerLeft() {
        Vector2d lowerCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for(IMapElement e : this.mapElements){
            lowerCorner = lowerCorner.lowerLeft(e.getPosition());
        }
        return lowerCorner;
    }
    public Vector2d getUpperRight() {
        Vector2d rightCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for(IMapElement e : this.mapElements){
            rightCorner = rightCorner.upperRight(e.getPosition());
        }
        return rightCorner;
    }
}
