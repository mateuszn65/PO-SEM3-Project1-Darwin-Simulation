package agh.ics.oop;


import java.util.Random;
public class GrassField extends AbstractWorldMap{
    private final int n;
    private final int bound;
    private final Random generator = new Random(13);

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
        this.mapElements.put(randpos, new Grass(randpos));
    }
    public void randomGrass(){
        for(int i = 0; i < n; i ++){
            addGrass();
        }
    }
    public Vector2d getLowerLeft() {
        Vector2d lowerCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for(IMapElement e: this.mapElements.values()){
            lowerCorner = lowerCorner.lowerLeft(e.getPosition());
        }
        return lowerCorner;
    }
    public Vector2d getUpperRight() {
        Vector2d rightCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for(IMapElement e: this.mapElements.values()){
            rightCorner = rightCorner.upperRight(e.getPosition());
        }
        return rightCorner;
    }
    @Override
    public void positionChange(Vector2d oldPosition, Vector2d newPosition) {
        if (isOccupied(newPosition)){
            addGrass();
        }
        IMapElement e = objectAt(oldPosition);
        this.mapElements.remove(oldPosition);
        this.mapElements.put(newPosition, e);

    }
    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            if (isOccupied(animal.getPosition()) &&
                    (objectAt(animal.getPosition()) instanceof Grass)){
                    addGrass();
                }
            this.mapElements.put(animal.position, animal);
            return true;
        }
        return false;
    }
}
