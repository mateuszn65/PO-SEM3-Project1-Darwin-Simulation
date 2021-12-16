package agh.ics.oop;

import java.util.Random;

public enum MapDirection{
    NORTH,
    SOUTH,
    WEST,
    EAST,
    NORTHWEST,
    SOUTHWEST,
    NORTHEAST,
    SOUTHEAST;

    @Override
    public String toString() {
        return switch (this){
            case NORTH -> "Polnoc";
            case SOUTH -> "Poludnie";
            case WEST -> "Zachod";
            case EAST -> "Wschod";
            case NORTHWEST -> "Północny-Zachód";
            case SOUTHWEST -> "Południowy-Zachód";
            case NORTHEAST -> "Północny-Wschód";
            case SOUTHEAST -> "Południowy-Wschód";
        };
    }
    public MapDirection next(){
        return switch (this){
            case NORTH -> NORTHEAST;
            case SOUTH -> SOUTHWEST;
            case WEST -> NORTHWEST;
            case EAST -> SOUTHEAST;
            case NORTHWEST -> NORTH;
            case SOUTHWEST -> WEST;
            case NORTHEAST -> EAST;
            case SOUTHEAST -> SOUTH;
        };
    }
    public MapDirection previous(){
        return switch (this){
            case NORTH -> NORTHWEST;
            case SOUTH -> SOUTHEAST;
            case WEST -> SOUTHWEST;
            case EAST -> NORTHEAST;
            case NORTHWEST -> WEST;
            case SOUTHWEST -> SOUTH;
            case NORTHEAST -> NORTH;
            case SOUTHEAST -> EAST;
        };
    }
    public Vector2d toUnitVector(){
        return switch (this){
            case NORTH -> new Vector2d(0,1);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1,0);
            case EAST -> new Vector2d(1, 0);
            case NORTHWEST -> new Vector2d(-1,1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case NORTHEAST -> new Vector2d(-1,1);
            case SOUTHEAST -> new Vector2d(1, -1);
        };
    }
    public static MapDirection getRandomDirection(){
        Random generator = new Random();
        return MapDirection.values()[generator.nextInt(8)];
    }
}
