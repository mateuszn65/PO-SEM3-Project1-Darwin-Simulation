package agh.ics.oop;


import java.util.Arrays;
import java.util.Comparator;


public class GenotypeComparator implements Comparator<int[]> {

    @Override
    public int compare(int[] o1, int[] o2) {
        return Arrays.compare(o1, o2);
    }
}
