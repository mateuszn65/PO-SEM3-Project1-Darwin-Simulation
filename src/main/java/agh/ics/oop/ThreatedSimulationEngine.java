package agh.ics.oop;

public class ThreatedSimulationEngine extends SimulationEngine{

    public ThreatedSimulationEngine(IWorldMap map, Vector2d[] positions) {
        super(map, positions);
    }
    public void setMoves(MoveDirection[] moves){
        if (moves.length % this.animals.size() != 0){
            throw new IllegalArgumentException("Needs equal number of moves for each animal");
        }
        this.moves = moves;
    }
}
