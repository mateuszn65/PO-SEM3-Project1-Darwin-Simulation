package agh.ics.oop;

import java.util.Comparator;

public class EnergyComparator implements Comparator<Animal> {

    //COMPARATOR FOR ANIMALS TO SORT THEM ACCORDING TO THE HIGHEST ENERGY
    @Override
    public int compare(Animal o1, Animal o2) {
        return Integer.compare(o2.energy, o1.energy);
    }
}
