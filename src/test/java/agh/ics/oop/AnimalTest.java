package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    @Test
    public void cloneTest(){
        Animal animal = new Animal(new WallMap(), new Vector2d(2, 2), 40);
        Animal tmp = new Animal(new WallMap(), new Vector2d(2, 2), 50);
        tmp.genes.genotype = animal.genes.genotype.clone();
        assertNotEquals(tmp.genes.genotype, animal.genes.genotype);
    }
}
