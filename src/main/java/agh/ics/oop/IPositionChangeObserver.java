package agh.ics.oop;

public interface IPositionChangeObserver {
    void positionChange(Animal animal ,Vector2d oldPosition, Vector2d newPosition);
}
