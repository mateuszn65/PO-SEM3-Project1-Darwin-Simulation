package agh.ics.oop;

import java.util.*;

public class WallMap extends AbstractWorldMap{
    protected int mapWidth,
            mapHeight,
            startingNumberOfAnimals,
            startEnergy,
            moveEnergy,
            plantEnergy;

    protected Jungle jungle;
    protected Map<Vector2d, SortedSet<Animal>> animalMap = new HashMap<>();
    protected Map<Vector2d, Grass> grassMap = new HashMap<>();
    protected LinkedList<Animal> animalsList = new LinkedList<>();
    protected LinkedList<Grass> grassList = new LinkedList<>();
    private int totalNumberOfDeadAnimals = 0, sumOfLivedDays = 0;
    private float sumOfChildren = 0;

    public WallMap(){
        //
    }
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
        for (Animal animal: animalsList){
            if (!animal.isDead()){
                alive++;
                energySum += animal.energy;
            }
        }
        return energySum/alive;
    }
    public int getTotalNumberOfDeadAnimals() {
        return totalNumberOfDeadAnimals;
    }
    public int getAverageLengthOfLife(){
        return this.sumOfLivedDays/this.totalNumberOfDeadAnimals;
    }
    public float getAverageChildren(){
        return (float) Math.round(this.sumOfChildren/getNumberOfAnimals() * 100f) / 100f;
    }

    @Override
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
            SortedSet<Animal> animalsOnSamePosition = this.animalMap.get(animal.position);
            if (animalsOnSamePosition != null){
                animalsOnSamePosition.add(animal);
            }else{
                SortedSet<Animal> tmp = new TreeSet<>(new EnergyComperator());
                tmp.add(animal);
                this.animalMap.put(animal.position, tmp);
            }
            this.animalsList.add(animal);
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
        SortedSet<Animal> animalsOnSamePosition = this.animalMap.get(position);
        if (animalsOnSamePosition == null || animalsOnSamePosition.size() == 0){
            return this.grassMap.get(position);
        }
        return animalsOnSamePosition.first();
    }

    public void addGrass(){
        Random generator = new Random();
        int toManyTimes = this.mapWidth*this.mapHeight*4;
        Vector2d randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
        while (isOccupied(randpos) && toManyTimes > 0){
            randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
            toManyTimes--;
        }
        Grass tmp = new Grass(randpos);
        this.grassMap.put(randpos, tmp);
        this.grassList.add(tmp);

        toManyTimes = this.mapWidth*this.mapHeight*4;
        randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
        while (isOccupied(randpos) && toManyTimes > 0 && this.jungle.belongsToJungle(randpos)){
            randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
            toManyTimes--;
        }
        tmp = new Grass(randpos);
        this.grassMap.put(randpos, tmp);
        this.grassList.add(tmp);
    }
    public void removeGrass(Grass grass){
        this.grassMap.remove(grass.position);
        this.grassList.remove(grass);
    }

    //for hashmap
    private void addAnimal(Animal animal, Vector2d position){
        if (canMoveTo(position)){
            SortedSet<Animal> animalsOnSamePosition = animalMap.get(position);
            if (animalsOnSamePosition != null){
                animalsOnSamePosition.add(animal);
            }else{
                SortedSet<Animal> tmp = new TreeSet<>(new EnergyComperator());
                tmp.add(animal);
                this.animalMap.put(position, tmp);
            }
        }
    }
    private void removeAnimal(Animal animal, Vector2d position){
        SortedSet<Animal> animalsOnSamePosition = animalMap.get(position);
        if (animalsOnSamePosition != null && animalsOnSamePosition.size() != 0){
            animalsOnSamePosition.remove(animal);
            if (animalsOnSamePosition.size() == 0){
                this.animalMap.remove(position);
            }
        }
    }


    private boolean randomlyPlaceAnimal(){
        Random generator = new Random();
        int toManyTimes = this.mapWidth*this.mapHeight*4;
        Vector2d randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
        while (isOccupied(randpos) && toManyTimes > 0){
            randpos = new Vector2d(generator.nextInt(this.mapWidth), generator.nextInt(this.mapHeight));
            toManyTimes--;
        }
        if (toManyTimes == 0) return false;
        Animal tmp = new Animal(this, randpos, this.startEnergy);
        place(tmp);
        return true;
    }
    public void randomlyPlaceAnimals(){
        for (int i = 0; i < this.startingNumberOfAnimals; i++) {
            if (!randomlyPlaceAnimal()) return ;
        }
    }

    public void nextDay(){
        for (Animal animal: this.animalsList) {
            animal.changeEnergy(this.moveEnergy);
            animal.daysLived++;
        }
    }

    public void checkForEating(){
        LinkedList<Grass> eatenGrass = new LinkedList<>();
        for(Grass grass: this.grassList){
            SortedSet<Animal> animalsOnSamePosition = this.animalMap.get(grass.position);

            if (animalsOnSamePosition != null){
                int n = animalsOnSamePosition.size();
                if (n > 0){
                    int maxEnergy = animalsOnSamePosition.first().energy;
                    Iterator<Animal> iterator = animalsOnSamePosition.iterator();
                    Animal curr = null;
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

        for (Grass grass: eatenGrass){
            removeGrass(grass);
        }
    }

    public void checkForCopulation(){
        for (SortedSet<Animal> animalsOnSamePosition: this.animalMap.values()){
            if (animalsOnSamePosition != null && animalsOnSamePosition.size() >= 2){
                Iterator<Animal> iterator = animalsOnSamePosition.iterator();
                Animal parent1 = animalsOnSamePosition.first(), parent2;
                for (int i = 0; iterator.hasNext(); i++) {
                    parent2 = iterator.next();
                    if (i == 1){
                        if (parent1.energy >= this.startEnergy/2 && parent2.energy >= this.startEnergy/2){
                            Animal child = parent1.copulation(parent2);
                            place(child);
                            parent1.children++;
                            parent2.children++;
                            this.sumOfChildren += 2;
                        }
                        break;
                    }
                }

            }
        }
    }
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
            this.animalsList.remove(animal);
        }
    }




    @Override
    public Vector2d getLowerLeft() {
        return this.lowerLeft;
    }

    @Override
    public Vector2d getUpperRight() {
        return this.upperRight;
    }
}
