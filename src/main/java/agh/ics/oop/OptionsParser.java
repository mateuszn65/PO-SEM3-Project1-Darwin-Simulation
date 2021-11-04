package agh.ics.oop;


import java.util.Arrays;

public class OptionsParser {
    public static MoveDirection[] parse(String[] tab){
        MoveDirection[] res = new MoveDirection[tab.length];
        int i = 0;
        for (String el : tab) {
            switch (el) {
                case "f", "forward" -> {
                    res[i] = MoveDirection.FORWARD;
                    i += 1;
                }
                case "b", "backward" -> {
                    res[i] = MoveDirection.BACKWARD;
                    i += 1;
                }
                case "r", "right" -> {
                    res[i] = MoveDirection.RIGHT;
                    i += 1;
                }
                case "l", "left" -> {
                    res[i] = MoveDirection.LEFT;
                    i += 1;
                }
                default -> {
                }
            }
        }
        return Arrays.copyOf(res, i);
    }
}
