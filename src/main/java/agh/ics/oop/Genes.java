package agh.ics.oop;

import java.util.Arrays;
import java.util.Random;

public class Genes {
    private int n = 32, v = 7;
    protected int[] genes;
    private final Random generator = new Random();
    public Genes(){
        this.genes = new int[this.n];
        for (int i = 0; i < this.n; i++){
            genes[i] = this.generator.nextInt(this.v);
        }
        Arrays.sort(this.genes);
    }


    public Genes(Animal animal1, Animal animal2){
        this.genes = new int[this.n];
        int strongerSite = this.generator.nextInt(2);
        if ((strongerSite == 0 && animal1.energy >= animal2.energy) || (strongerSite == 1 && animal1.energy < animal2.energy)){
            int div = (animal1.energy)/(animal1.energy + animal2.energy);
            int k = 0;
            for (int i = 0; i <= div; i++) {
                genes[k] = animal1.genes.genes[i];
                k++;
            }
            for (int i = div+1; i < this.n; i++) {
                genes[k] = animal2.genes.genes[i];
                k++;
            }
        }else {
            int div = (animal2.energy)/(animal1.energy + animal2.energy);
            int k = 0;
            for (int i = 0; i <= div; i++) {
                genes[k] = animal2.genes.genes[i];
                k++;
            }
            for (int i = div+1; i < this.n; i++) {
                genes[k] = animal1.genes.genes[i];
                k++;
            }
        }
        Arrays.sort(this.genes);
    }

    public int getRandomGen() {
        int rand = this.generator.nextInt(this.n);
        return genes[rand];
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}
