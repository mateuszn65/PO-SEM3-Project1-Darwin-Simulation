package agh.ics.oop;

import java.util.Arrays;
import java.util.Random;

public class Genes {
    private int n = 32, v = 7;
    protected int[] genotype;
    private final Random generator = new Random();

    //CONSTRUCTOR FOR STARTING ANIMALS
    public Genes(){
        this.genotype = new int[this.n];
        for (int i = 0; i < this.n; i++){
            this.genotype[i] = this.generator.nextInt(this.v);
        }
        Arrays.sort(this.genotype);
    }

    //CONSTRUCTOR FOR CHILD OF TWO ANIMALS
    public Genes(Animal animal1, Animal animal2){
        this.genotype = new int[this.n];
        int strongerSite = this.generator.nextInt(2);
        if ((strongerSite == 0 && animal1.energy >= animal2.energy) || (strongerSite == 1 && animal1.energy < animal2.energy)){
            int div = (animal1.energy)/(animal1.energy + animal2.energy);
            int k = 0;
            for (int i = 0; i <= div; i++) {
                this.genotype[k] = animal1.genes.genotype[i];
                k++;
            }
            for (int i = div+1; i < this.n; i++) {
                this.genotype[k] = animal2.genes.genotype[i];
                k++;
            }
        }else {
            int div = (animal2.energy)/(animal1.energy + animal2.energy);
            int k = 0;
            for (int i = 0; i <= div; i++) {
                this.genotype[k] = animal2.genes.genotype[i];
                k++;
            }
            for (int i = div+1; i < this.n; i++) {
                this.genotype[k] = animal1.genes.genotype[i];
                k++;
            }
        }
        Arrays.sort(this.genotype);
    }

    public int getRandomGen() {
        int rand = this.generator.nextInt(this.n);
        return this.genotype[rand];
    }

    @Override
    public String toString() {
        return Arrays.toString(this.genotype);
    }
}
