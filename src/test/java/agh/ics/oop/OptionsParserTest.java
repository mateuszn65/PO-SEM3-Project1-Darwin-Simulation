package agh.ics.oop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class OptionsParserTest {
    @Test
    public void parseTest(){
        String[] validInput = {
                "f", "b", "r", "l",
                "forward", "backward", "right", "left"
        };
        MoveDirection[] expecedDirections = {
                MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.LEFT,
                MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.LEFT
        };
        assertArrayEquals(expecedDirections, new OptionsParser().parse(validInput));
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                ()->{
                    new OptionsParser().parse(new String[] {"not", "valid"});
                });
        assertTrue(ex.getMessage().contains("is not legal move specificiation"));
    }
}
