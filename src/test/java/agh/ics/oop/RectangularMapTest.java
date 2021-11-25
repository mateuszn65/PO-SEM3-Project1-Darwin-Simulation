package agh.ics.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    private IWorldMap map;
    private Animal kot;
    private Animal pies;


    @BeforeEach
    public void init(){
        this.map = new RectangularMap(10, 5);
        this.kot = new Animal(this.map, new Vector2d(2, 2));
        this.pies = new Animal(this.map, new Vector2d(3, 4));
        this.map.place(kot);
        this.map.place(pies);
    }

    @Test
    public void canMoveToTest(){
        assertTrue(this.map.canMoveTo(new Vector2d(2,3)));
        assertTrue(this.map.canMoveTo(new Vector2d(7,3)));
        assertTrue(this.map.canMoveTo(new Vector2d(3,2)));
        assertTrue(this.map.canMoveTo(new Vector2d(0,0)));
        assertTrue(this.map.canMoveTo(new Vector2d(3,3)));
        assertFalse(this.map.canMoveTo(new Vector2d(2,2)));
        assertFalse(this.map.canMoveTo(new Vector2d(3,4)));
        assertFalse(this.map.canMoveTo(new Vector2d(3,5)));
    }

    @Test
    public void placeTest(){
        assertFalse(this.map.place(this.kot));
        assertFalse(this.map.place(this.pies));
        assertTrue(this.map.place(new Animal(this.map, new Vector2d(7,4)) ));
    }

    @Test
    public void isOccupiedTest(){
        assertTrue(this.map.isOccupied(new Vector2d (2,2)));
        assertTrue(this.map.isOccupied(new Vector2d(3,4)));
        assertFalse(this.map.isOccupied(new Vector2d(3,2)));
        assertFalse(this.map.isOccupied(new Vector2d(7,4)));
    }
    @Test
    public void objectAtTest() {
        System.out.println((this.kot));
        assertEquals(this.map.objectAt(new Vector2d (2,2)), this.kot);
        assertEquals(this.map.objectAt(new Vector2d (3,4)), this.pies);
        assertNull(this.map.objectAt(new Vector2d(3,5)));
        assertNull(this.map.objectAt(new Vector2d(1,2)));
    }

    @Test
    public void RectangularMapIT(){
        String[] args = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        assertTrue(engine.getAnimal(0).isAt(new Vector2d(2, 0)));
        assertTrue(engine.getAnimal(1).isAt(new Vector2d(3, 4)));
        assertEquals(engine.getAnimal(0).getDirection(), MapDirection.SOUTH);
        assertEquals(engine.getAnimal(1).getDirection(), MapDirection.NORTH);
    }
}
