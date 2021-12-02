package agh.ics.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {
    private IWorldMap map;
    private Animal kot;
    private Animal pies;


    @BeforeEach
    public void init(){
        this.map = new GrassField(10);
        this.kot = new Animal(this.map, new Vector2d(2, 2));
        this.pies = new Animal(this.map, new Vector2d(3, 4));
        this.map.place(kot);
        this.map.place(pies);
    }

    @Test
    public void canMoveToTest(){
        assertTrue(this.map.canMoveTo(new Vector2d(2,3)));
        assertTrue(this.map.canMoveTo(new Vector2d(76,38)));
        assertTrue(this.map.canMoveTo(new Vector2d(3,2)));
        assertTrue(this.map.canMoveTo(new Vector2d(0,0)));
        assertTrue(this.map.canMoveTo(new Vector2d(3,3)));
        assertFalse(this.map.canMoveTo(new Vector2d(2,2)));
        assertFalse(this.map.canMoveTo(new Vector2d(3,4)));
    }

    @Test
    public void placeTest(){
        assertFalse(this.map.place(this.kot));
        assertFalse(this.map.place(this.pies));
        assertTrue(this.map.place(new Animal(this.map, new Vector2d(10,6)) ));
    }

    @Test
    public void isOccupiedTest(){
        assertTrue(this.map.isOccupied(new Vector2d (2,2)));
        assertTrue(this.map.isOccupied(new Vector2d(3,4)));
    }
    @Test
    public void objectAtTest() {
        System.out.println((this.kot));
        assertEquals(this.map.objectAt(new Vector2d (2,2)), this.kot);
        assertEquals(this.map.objectAt(new Vector2d (3,4)), this.pies);
        Random rn = new Random();
        for (int i = 0; i < 20; i++) {
            Vector2d randpos = new Vector2d(rn.nextInt(10), rn.nextInt(10));
            if (this.map.isOccupied(randpos) &&
                    this.map.objectAt(randpos) != null &&
                    !(this.map.objectAt(randpos) instanceof Animal)){
                assertTrue(this.map.objectAt(randpos) instanceof Grass);
            }
            else{
                assertFalse(this.map.objectAt(randpos) instanceof Grass);
            }
        }

    }
    private int grassCount(AbstractWorldMap map){
        int count = 0;
        for(IMapElement e: map.mapElements.values()){
            if (e instanceof Grass){
                count ++;
            }
        }
        return count;
    }
    @Test
    public void GrassFieldIT(){
        String[] argstab = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] moves = new OptionsParser().parse(argstab);
        AbstractWorldMap map = new GrassField(10);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        List<Animal> animals = new ArrayList<>();
        Vector2d[] expectedPositions = {
                new Vector2d(2,2), new Vector2d(3,4),
                new Vector2d(2,3), new Vector2d(3,3),
                new Vector2d(2,3), new Vector2d(3,3),
                new Vector2d(2,3), new Vector2d(3,3),
                new Vector2d(2,3), new Vector2d(3,3),
                new Vector2d(2,2), new Vector2d(3,4),
                new Vector2d(2,1), new Vector2d(3,5),
                new Vector2d(2,0), new Vector2d(3,6)
        };
        MapDirection[] expectedDirections = {
                MapDirection.NORTH, MapDirection.NORTH,
                MapDirection.NORTH, MapDirection.NORTH,
                MapDirection.EAST, MapDirection.WEST,
                MapDirection.EAST, MapDirection.WEST,
                MapDirection.SOUTH, MapDirection.NORTH,
                MapDirection.SOUTH, MapDirection.NORTH,
                MapDirection.SOUTH, MapDirection.NORTH,
                MapDirection.SOUTH, MapDirection.NORTH
        };
        for(Vector2d pos : positions){
            Animal a = new Animal(map, pos);
            a.addObserver(map);
            if(map.place(a)){
                animals.add(a);
            }
        }
        int n = animals.size();
        int m = 10;
        for (int i = 0; i < moves.length; i++){
            if (i % n == 0){
                System.out.println(map);
                assertEquals(grassCount(map), m);
                for (int j = 0; j < n; j++){
                    assertTrue(animals.get(j).isAt(expectedPositions[i+j]));
                    assertEquals(animals.get(j).getDirection(), expectedDirections[i+j]);
                }

            }
            animals.get(i % n).move(moves[i]);
        }
        System.out.println(map);
        assertEquals(grassCount(map), m);
        assertTrue(animals.get(0).isAt(new Vector2d(2, 0)));
        assertTrue(animals.get(1).isAt(new Vector2d(3, 7)));
        assertEquals(animals.get(0).getDirection(), MapDirection.SOUTH);
        assertEquals(animals.get(1).getDirection(), MapDirection.NORTH);
    }
}
