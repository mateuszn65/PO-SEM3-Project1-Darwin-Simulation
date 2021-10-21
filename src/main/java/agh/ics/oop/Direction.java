package agh.ics.oop;

enum Dir{
    FORWARD,
    BACKWARD,
    RIGHT,
    LEFT,
    NONE
}
public class Direction {
    public static void convert(String[] args,Dir[] dirs){
        for(int i = 0; i < args.length; i++){
            dirs[i] = switch (args[i]){
                case "f" -> Dir.FORWARD;
                case "b" -> Dir.BACKWARD;
                case "r" -> Dir.RIGHT;
                case "l" -> Dir.LEFT;
                default -> Dir.NONE;
            };
        }
    }
}
