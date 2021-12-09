package agh.ics.oop;

import java.util.Comparator;
public class XComperator implements Comparator<IMapElement> {
    @Override
    public int compare(IMapElement o1, IMapElement o2) {
        if (o1.getPosition().x == o2.getPosition().x){
            if (o1.getPosition().y == o2.getPosition().y){
                if (o1 instanceof Grass && o2 instanceof Animal)
                    return -1;
                if (o1 instanceof Animal && o2 instanceof Grass)
                    return 1;
                return 0;
            }
            return o1.getPosition().y - o2.getPosition().y;
        }
        return o1.getPosition().x - o2.getPosition().x;
    }
}
