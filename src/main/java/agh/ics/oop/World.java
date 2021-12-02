package agh.ics.oop;
import java.util.Arrays;

import static java.lang.System.out;
public class World {

    public static void main(String[] args){

        String[] argstab = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};

        MoveDirection[] directions = new OptionsParser().parse(argstab);
        //IWorldMap map = new RectangularMap(10, 5);
        IWorldMap map = new GrassField(10);
        //System.out.println(map);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();


    }
}
