package agh.ics.oop;
import java.util.Arrays;

import static java.lang.System.out;
public class World {
    public static void run(Dir[] args){
        /*out.println("zwierzak idzie do przodu");
        for(String arg : args){
            if (!arg.equals(args[0])){
                out.print(",");
            }
            out.print(arg);
        }
        out.println();*/
        out.println("Start");
        for(Dir arg : args){
            switch(arg){
                case FORWARD:
                    out.println("Zwierzak idzie do przodu");
                    break;
                case BACKWARD:
                    out.println("Zwierzak idzie do tylu");
                    break;
                case RIGHT:
                    out.println("Zwierzak skreca w prawo");
                    break;
                case LEFT:
                    out.println("Zwierzak skreca w lewo");
                    break;
            }

        }
        out.println("Stop");
    }
    public static void main(String[] args){
//        out.println("system wystartowal");
//        //String[] example = {"kot", "pies"};
//        Dir[] dirs = new Dir[args.length];
//        Direction.convert(args, dirs);
//        run(dirs);
//        out.println("system zakonczyl dzialanie");

//        Animal kot = new Animal();
//        System.out.println(kot);
////        kot.move(MoveDirection.RIGHT);
////        kot.move(MoveDirection.FORWARD);
////        kot.move(MoveDirection.FORWARD);
////        kot.move(MoveDirection.FORWARD);
////        System.out.println(kot);
//        String[] dir = {"r", "f", "f", "forward"};
//        //MoveDirection[] dir2 = OptionsParser.parse(dir);
//        MoveDirection[] dir2 = OptionsParser.parse(args);
//        for(MoveDirection move : dir2){
//            kot.move(move);
//
//        }
//        System.out.println(kot);



        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map = new RectangularMap(10, 5);

        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
    }
}
