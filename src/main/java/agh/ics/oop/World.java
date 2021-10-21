package agh.ics.oop;
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
        out.println("system wystartowal");
        //String[] example = {"kot", "pies"};
        Dir[] dirs = new Dir[args.length];
        Direction.convert(args, dirs);
        run(dirs);
        out.println("system zakonczyl dzialanie");
    }
}
