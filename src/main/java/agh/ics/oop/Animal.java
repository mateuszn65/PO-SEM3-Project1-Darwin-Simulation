package agh.ics.oop;
//jako, że plansza jest małego rozmiaru 5x5 można zaimplementować klase board, która w tablicy 2d będzie przechowywać
//wartości True jeśli jakiś zwierzak jest na danym polu i False jeśli to pole jest puste


public class Animal {
    private Vector2d position = new Vector2d(2,2);
    private MapDirection direction = MapDirection.NORTH;

    public String toString(){
        return "Pozycja zwierzaka: " + position + " kierunek: " + direction;
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public boolean isIn(Vector2d position){
        return (0 <= position.x && position.x <= 4 && 0 <= position.y && position.y <= 4);
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
                if (isIn(f)) {
                    this.position = f;
                }
            }
            case BACKWARD -> {
                Vector2d b = this.position.subtract(this.direction.toUnitVector());
                if (isIn(b)) {
                    this.position = b;
                }
            }
        }
    }
}
