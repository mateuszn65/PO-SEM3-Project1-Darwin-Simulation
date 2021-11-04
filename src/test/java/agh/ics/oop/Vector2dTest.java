package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    @Test
    public void equalsTest(){
        Vector2d a = new Vector2d(3, 4);
        Vector2d b = new Vector2d(3, 4);
        Vector2d c = new Vector2d(4, 3);
        assertTrue(a.equals(b));
        assertFalse(a.equals(c));
    }

    @Test
    public void toStringTest(){
        Vector2d a = new Vector2d(3, 4);
        assertEquals(a.toString(), "(3, 4)");
    }

    @Test
    public void precedesTest(){
        Vector2d a = new Vector2d(3, 4);
        Vector2d b = new Vector2d(6, 4);
        Vector2d c = new Vector2d(1, 3);
        assertTrue(a.precedes(b));
        assertFalse(a.precedes(c));
    }

    @Test
    public void followsTest(){
        Vector2d a = new Vector2d(3, 4);
        Vector2d b = new Vector2d(-3, 4);
        Vector2d c = new Vector2d(4, 3);
        assertTrue(a.follows(b));
        assertFalse(a.follows(c));
    }

    @Test
    public void upperRightTest(){
        Vector2d a = new Vector2d(3, 4);
        Vector2d b = new Vector2d(2, 5);
        Vector2d c = new Vector2d(3, 5);
        assertTrue(c.equals(a.upperRight(b)));
    }

    @Test
    public void lowerLeftTest(){
        Vector2d a = new Vector2d(3, 4);
        Vector2d b = new Vector2d(2, 5);
        Vector2d c = new Vector2d(2, 4);
        assertTrue(c.equals(a.lowerLeft(b)));
    }

    @Test
    public void addTest(){
        Vector2d a = new Vector2d(3, 4);
        Vector2d b = new Vector2d(2, 5);
        Vector2d c = new Vector2d(5, 9);
        assertTrue(c.equals(a.add(b)));
    }

    @Test
    public void subtractTest(){
        Vector2d a = new Vector2d(3, 4);
        Vector2d b = new Vector2d(2, 5);
        Vector2d c = new Vector2d(1, -1);
        assertTrue(c.equals(a.subtract(b)));
    }

    @Test
    public void oppositeTest(){
        Vector2d a = new Vector2d(3, -4);
        Vector2d b = new Vector2d(-3, 4);
        Vector2d c = new Vector2d(3, 4);
        assertTrue(b.equals(a.opposite()));
        assertFalse(c.equals(a.opposite()));
    }
}
