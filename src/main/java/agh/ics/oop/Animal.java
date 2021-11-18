package agh.ics.oop;


public class Animal {
    private Vector2d position = new Vector2d(2,2);
    private MapDirection direction = MapDirection.NORTH;
    private final IWorldMap map;
    public Animal(){
        this.map = new RectangularMap(5, 5);
    }
    public Animal(IWorldMap map){
        this.map = map;
    }
    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
    }

    public Vector2d getPosition() {
        return position;
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

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
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
                if (!this.map.isOccupied(f) && this.map.canMoveTo(f)) {
                    this.position = f;
                }
            }
            case BACKWARD -> {
                Vector2d b = this.position.subtract(this.direction.toUnitVector());
                if (!this.map.isOccupied(b) && this.map.canMoveTo(b)) {
                    this.position = b;
                }
            }
        }
    }
}
