package agh.ics.oop;

import java.util.Comparator;

public class EnergyComperator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        if (o1.energy < o2.energy) return 1;
        if (o1.energy == o2.energy) return 0;
        return -1;
    }
}
