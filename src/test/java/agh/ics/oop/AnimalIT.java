package agh.ics.oop;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AnimalIT {
    @Test
    public void animalMoveTest(){
        Animal zwierzak = new Animal();
        String[] args = {"forward", "f", "r", "f", "kot", "left", "f"};
        MoveDirection[] con_args = OptionsParser.parse(args);
        MoveDirection[] exp_args = {MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.FORWARD
                                  , MoveDirection.LEFT, MoveDirection.FORWARD};
        Vector2d[] exp_pos = {new Vector2d(2,2), new Vector2d(2,3), new Vector2d(2,4)
                            , new Vector2d(2,4), new Vector2d(3,4), new Vector2d(3,4), new Vector2d(3,4)};
        MapDirection[] exp_dir = {MapDirection.NORTH, MapDirection.NORTH, MapDirection.NORTH, MapDirection.EAST, MapDirection.EAST
                                , MapDirection.NORTH, MapDirection.NORTH};
        //Poprawna interpretacja tablicy łańcuchów znaków
        assertArrayEquals(con_args, exp_args);
        //Poprawność pozycji startowej
        assertTrue(zwierzak.equals(exp_pos[0], exp_dir[0]));
        int i = 1;
        for (MoveDirection move: con_args) {
            zwierzak.move(move);
            //System.out.println(zwierzak);
            assertTrue(zwierzak.isIn(), "not in");
            assertTrue(zwierzak.equals(exp_pos[i], exp_dir[i]), "wrong position");
            i += 1;
        }
    }

    @Test
    public void mapTest(){
        String[] args = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map = new RectangularMap(10, 5);

        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        assertTrue(engine.getAnimal(0).isAt(new Vector2d(2, 0)));
        assertTrue(engine.getAnimal(1).isAt(new Vector2d(3, 4)));
    }
}
