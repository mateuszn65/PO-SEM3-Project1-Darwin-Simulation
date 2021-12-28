package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{

    protected Vector2d lowerLeft;
    protected Vector2d upperRight;

    protected int mapWidth;
    protected int mapHeight;
    protected int startingNumberOfAnimals;
    protected int startEnergy;
    protected int moveEnergy;
    protected int plantEnergy;

    protected Jungle jungle;
    protected Map<Vector2d, LinkedList<Animal>> animalMap = new HashMap<>();
    protected Map<Vector2d, Grass> grassMap = new HashMap<>();
    protected LinkedList<Animal> animalsList = new LinkedList<>();
    protected LinkedList<Grass> grassList = new LinkedList<>();
    protected LinkedList<int[]> genotypesList = new LinkedList<>();
    protected Tracer tracer = new Tracer();
    private int totalNumberOfDeadAnimals = 0, sumOfLivedDays = 0, numberOfDays = 0;
    private float sumOfChildren = 0;

    //SETTING MAP PARAMETER
    public void setParameters(int width, int height, Jungle jungle,int startingNumberOfAnimals, int startEnergy, int moveEnergy, int plantEnergy){
        this.mapWidth = width;
        this.mapHeight = height;
        this.startingNumberOfAnimals = startingNumberOfAnimals;
        this.startEnergy = startEnergy;
        this.moveEnergy = (-1)*moveEnergy;
        this.plantEnergy = plantEnergy;
        this.lowerLeft = new Vector2d(0 ,0);
        this.upperRight = new Vector2d(width - 1, height - 1);
        this.jungle = jungle;
    }

    //GETTERS MOSTLY FOR dISPLAYING STATS
    public Tracer getTracer() {
        return tracer;
    }
    public int getNumberOfAnimalsOnSamePosition(Vector2d position){
        if(this.animalMap.get(position) != null){
            return this.animalMap.get(position).size();
        }
        return 0;
    }
    public int getNumberOfAnimals(){
        return this.animalsList.size();
    }
    public int getNumberOfGrass(){
        return this.grassList.size();
    }
    public int getAverageEnergy(){
        int alive = 0, energySum = 0;
        Animal[] animals = this.animalsList.toArray(new Animal[0]);
        for (Animal animal: animals){
            if (!animal.isDead()){
                alive++;
                energySum += animal.energy;
            }
        }
        if (alive == 0)
            return 0;
        return energySum/alive;
    }
    public int getAverageLengthOfLife(){
        if (this.totalNumberOfDeadAnimals == 0)
            return 0;
        return this.sumOfLivedDays/this.totalNumberOfDeadAnimals;
    }
    public float getAverageChildren(){
        return (float) Math.round(this.sumOfChildren/getNumberOfAnimals() * 100f) / 100f;
    }
    public int getNumberOfDays(){
        return this.numberOfDays;
    }
    public int[] getArrayDominantGenotype(){
        if (this.genotypesList.size() > 0){
            int[] res = {};
            this.genotypesList.sort(new GenotypeComparator());
            int[] prev = genotypesList.getFirst();
            int i = 1;
            int[] F = new int[this.genotypesList.size() + 1];
            for (int[] genotype : this.genotypesList){
                if (Arrays.equals(prev, genotype)){
                    F[i] = F[i-1] + 1;
                }else {
                    F[i] = 1;
                    prev = genotype;
                }
            }
            int maxAt = 0;
            for (int j = 1; j < F.length; j++){
                if (F[j] > F[maxAt])
                    maxAt = j;
            }
            res = this.genotypesList.get(maxAt-1);
            return res;
        }
        return null;
    }
    public String getDominantGenotype(){
        int[] res = getArrayDominantGenotype();
        StringBuilder resString = new StringBuilder();
        if (res == null)
            return resString.toString();

        for (int i = 0; i < 10; i++){
            resString.append(res[i]);
            resString.append(" ");
        }
        resString.append(System.lineSeparator());
        for (int i = 10; i < res.length; i++){
            resString.append(res[i]);
            resString.append(" ");
        }
        return resString.toString();
    }

    //TO HANDLE DIFFERENT MAP EDGES
    abstract public Vector2d convertPosition(Vector2d position);

    //FUNCTIONS HELPING WITH POSITIONS OF MAP ELEMENTS
    public void positionChange(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        removeAnimal(animal, oldPosition);
        addAnimal(animal, newPosition);
    }
    public boolean canMoveTo(Vector2d position) {
        return (position.follows(this.lowerLeft) &&
                position.precedes(this.upperRight));
    }
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            LinkedList<Animal> animalsOnSamePosition = this.animalMap.get(animal.position);
            if (animalsOnSamePosition != null){
                animalsOnSamePosition.add(animal);
                _sort(animalsOnSamePosition);
            }else{
                LinkedList<Animal> tmp = new LinkedList<>();
                tmp.add(animal);
                this.animalMap.put(animal.position, tmp);
            }
            this.animalsList.add(animal);
            this.genotypesList.add(animal.genes.genotype);
            animal.addObserver(this);
            return true;
        }else{
            throw new IllegalArgumentException("Animal on position " + animal.getPosition() + " is not valid");
        }

    }
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }
    public IMapElement objectAt(Vector2d position) {
        LinkedList<Animal> animalsOnSamePosition = this.animalMap.get(position);
        if (animalsOnSamePosition == null || animalsOnSamePosition.size() == 0){
            return this.grassMap.get(position);
        }
        return animalsOnSamePosition.getFirst();
    }
    public boolean isDominantAt(Vector2d position, int[] dominant){
        LinkedList<Animal> animalsOnSamePosition = this.animalMap.get(position);
        if (animalsOnSamePosition == null || animalsOnSamePosition.size() == 0){
            return false;
        }
        for (Animal animal : animalsOnSamePosition){
            if (Arrays.equals(dominant, animal.getArrayGenotype()))
                return true;
        }
        return false;
    }

    //SPAWNING GRASS ONE IN JUNGLE AND ONE ON THE ENTIRE MAP
    public void addGrass(){
        Random generator = new Random();
        int toManyTimes = this.mapWidth*this.mapHeight;
        Vector2d randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
        while (isOccupied(randpos) && toManyTimes > 0){
            randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
            toManyTimes--;
        }
        if (toManyTimes > 0){
            Grass tmp = new Grass(randpos);
            this.grassMap.put(randpos, tmp);
            this.grassList.add(tmp);
        }

        toManyTimes = this.mapWidth*this.mapHeight;
        randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
        while (isOccupied(randpos) && toManyTimes > 0){
            randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
            toManyTimes--;
            while (!this.jungle.belongsToJungle(randpos) && toManyTimes > 0){
                randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
                toManyTimes--;
            }

        }
        if (toManyTimes > 0){
            Grass tmp = new Grass(randpos);
            this.grassMap.put(randpos, tmp);
            this.grassList.add(tmp);
        }
    }
    //REMOVE GRASS FROM HASHMAP AND LIST
    private void removeGrass(Grass grass){
        this.grassMap.remove(grass.position);
        this.grassList.remove(grass);
    }

    //FUNCTIONS HANDLING ANIMALS HASHMAP
    private void _sort(LinkedList<Animal> list){
        list.sort(new EnergyComparator());
    }
    private void addAnimal(Animal animal, Vector2d position){
        if (canMoveTo(position)){
            LinkedList<Animal> animalsOnSamePosition = animalMap.get(position);
            if (animalsOnSamePosition != null){
                animalsOnSamePosition.add(animal);
            }else{
                LinkedList<Animal> tmp = new LinkedList<>();
                tmp.add(animal);
                this.animalMap.put(position, tmp);
            }
        }
    }
    private void removeAnimal(Animal animal, Vector2d position){
        LinkedList<Animal> animalsOnSamePosition = animalMap.get(position);
        if (animalsOnSamePosition != null && animalsOnSamePosition.size() != 0){
            animalsOnSamePosition.remove(animal);
            if (animalsOnSamePosition.size() == 0){
                this.animalMap.remove(position);
            }
        }
    }

    //HELPER FUNCTIONS FOR RANDOM PLACING OF ANIMALS
    private Vector2d getRandomPositionToPlaceAnimal(){
        Random generator = new Random();
        int toManyTimes = this.mapWidth*this.mapHeight*4;
        Vector2d randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
        while (isOccupied(randpos) && toManyTimes > 0){
            randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
            toManyTimes--;
        }
        if (toManyTimes == 0) return null;
        return randpos;
    }
    private boolean randomlyPlaceAnimal(){
        Vector2d randpos = getRandomPositionToPlaceAnimal();
        if (randpos == null) return false;
        Animal tmp = new Animal(this, randpos, this.startEnergy);
        place(tmp);
        return true;
    }
    //FUNCTION HANDLING RANDOM PLACING OF ANIMALS
    public void randomlyPlaceAnimals(){
        for (int i = 0; i < this.startingNumberOfAnimals; i++) {
            if (!randomlyPlaceAnimal()) return ;
        }
    }

    //CHANGING ENERGY AFTER MOVES
    public void nextDay(){
        for (Animal animal: this.animalsList) {
            animal.changeEnergy(this.moveEnergy);
            animal.daysLived++;
        }
        this.numberOfDays++;
        if (this.tracer.isActive && !this.tracer.animal.isDead())
            this.tracer.daysTraced++;
    }
    //HANDLES EATING
    public void checkForEating(){
        LinkedList<Grass> eatenGrass = new LinkedList<>();
        for(Grass grass: this.grassList){
            LinkedList<Animal> animalsOnSamePosition = this.animalMap.get(grass.position);

            if (animalsOnSamePosition != null){
                int n = animalsOnSamePosition.size();
                if (n > 0){
                    _sort(animalsOnSamePosition);
                    if (!animalsOnSamePosition.getFirst().isDead()){
                        int maxEnergy = animalsOnSamePosition.getFirst().energy;
                        Iterator<Animal> iterator = animalsOnSamePosition.iterator();
                        Animal curr;
                        LinkedList<Animal> eatingAnimals = new LinkedList<>();
                        while (iterator.hasNext()){
                            curr = iterator.next();
                            if (curr.energy == maxEnergy){
                                eatingAnimals.add(curr);
                            }else {
                                break;
                            }
                        }
                        for(Animal animal: eatingAnimals){
                            animal.changeEnergy(this.plantEnergy/eatingAnimals.size());
                        }
                        eatenGrass.add(grass);
                    }
                }
            }
        }

        for (Grass grass: eatenGrass){
            removeGrass(grass);
        }
    }
    //HANDLES COPULATION
    public void checkForCopulation(){
        Collection<LinkedList<Animal>> animalsOnSamePositionToIter = this.animalMap.values();
        for (LinkedList<Animal> animalsOnSamePosition: animalsOnSamePositionToIter){
            if (animalsOnSamePosition != null && animalsOnSamePosition.size() >= 2){
                _sort(animalsOnSamePosition);
                Iterator<Animal> iterator = animalsOnSamePosition.iterator();
                Animal parent1 = animalsOnSamePosition.getFirst(), parent2;
                for (int i = 0; iterator.hasNext(); i++) {
                    parent2 = iterator.next();
                    if (i == 1){
                        if (parent1.energy >= this.startEnergy/2 && parent2.energy >= this.startEnergy/2){
                            Animal child = parent1.copulation(parent2);
                            place(child);
                            parent1.children++;
                            parent2.children++;
                            this.sumOfChildren += 2;

                            if (this.tracer.isActive){
                                if (parent1 == this.tracer.animal || parent2 == this.tracer.animal){
                                    this.tracer.numberOfChildren++;
                                    this.tracer.descendants.add(child);
                                }else if (this.tracer.descendants.contains(parent1) || this.tracer.descendants.contains(parent2)){
                                    this.tracer.descendants.add(child);
                                }
                            }
                        }
                        break;
                    }
                }

            }
        }
    }
    //HANDLES REMOVING DEAD ANIMALS
    public void removeDeadAnimals(){
        int n = animalsList.size();
        LinkedList<Animal> deadAnimals = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            Animal animal = this.animalsList.get(i);
            if (animal.isDead()){
                deadAnimals.add(animal);

            }
        }
        for (Animal animal: deadAnimals){
            this.sumOfLivedDays += animal.daysLived;
            this.totalNumberOfDeadAnimals++;
            this.sumOfChildren -= animal.children;

            removeAnimal(animal, animal.position);
            animal.removeObserver(this);
            this.genotypesList.remove(animal.genes.genotype);
            this.animalsList.remove(animal);
        }
        if (this.tracer.isActive && this.tracer.animal.isDead() && this.tracer.epochOfDeath == -1)
            this.tracer.epochOfDeath = this.numberOfDays;
    }

    //HANDLES MAGIC EVOLUTION
    public void magicEvolution(){
        Animal[] animals = this.animalsList.toArray(new Animal[0]);
        for (Animal animal : animals){
            Vector2d randpos = getRandomPositionToPlaceAnimal();
            if (randpos == null) return;
            Animal tmp = new Animal(this, randpos, this.startEnergy);
            tmp.genes.genotype = animal.genes.genotype.clone();
            place(tmp);
        }
    }

}
