package agh.ics.oop;



public class Animal extends AbstractMapElement{
    private MapDirection direction = MapDirection.NORTH;
    private final IWorldMap map;
    public Animal(){
        super(new Vector2d(2,2));
        this.map = new RectangularMap(5, 5);
    }
    public Animal(IWorldMap map){
        super(new Vector2d(2,2));
        this.map = map;
    }
    public Animal(IWorldMap map, Vector2d initialPosition){
        super(initialPosition);
        this.map = map;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public String toString(){
        return switch (this.direction){
            case EAST -> ">";
            case WEST -> "<";
            case SOUTH -> "v";
            case NORTH -> "^";
        };
    }

    public boolean isIn() {
        return (0 <= this.position.x && this.position.x <= 4 && 0 <= this.position.y && this.position.y <= 4);
    }

    public boolean equals(Vector2d pos, MapDirection dir){
        return this.position.equals(pos) && this.direction.equals(dir);
    }

    public void move(MoveDirection direction){
        switch (direction) {
            case LEFT -> this.direction = this.direction.previous();
            case RIGHT -> this.direction = this.direction.next();
            case FORWARD -> {
                Vector2d f = this.position.add(this.direction.toUnitVector());
                if (this.map.canMoveTo(f)) {
                    this.position = f;
                }
            }
            case BACKWARD -> {
                Vector2d b = this.position.subtract(this.direction.toUnitVector());
                if (this.map.canMoveTo(b)) {
                    this.position = b;
                }
            }
        }
    }
}
