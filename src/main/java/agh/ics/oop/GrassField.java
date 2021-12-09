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
        IMapElement tmp = new Grass(randpos);
        this.mapElements.put(randpos, tmp);
        this.mapBoundary.addMapElement(tmp);

    }
    public void randomGrass(){
        for(int i = 0; i < n; i ++){
            addGrass();
        }
    }
    public Vector2d getLowerLeft() {
        return this.mapBoundary.getLowerLeft();
    }
    public Vector2d getUpperRight() {
        return this.mapBoundary.getUpperRight();
    }
    @Override
    public void positionChange(Vector2d oldPosition, Vector2d newPosition) {
        if (isOccupied(newPosition)){
            addGrass();
        }
        super.positionChange(oldPosition, newPosition);
    }
    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            if (isOccupied(animal.getPosition()) &&
                    (objectAt(animal.getPosition()) instanceof Grass)){
                    addGrass();
                }
            this.mapElements.put(animal.getPosition(), animal);
            this.mapBoundary.addMapElement(animal);
            return true;
        }else{
            throw new IllegalArgumentException("Animal on position " + animal.getPosition() + " is not valid");
        }
    }
}
