package very_stupid_game;

import java.util.List;

import Common.Grid;
import aStar.*;

public class VeryStupidAgent implements Runnable {
    private int[] currentPos;
    private int[] goalPos;
    static Grid grid;
    static List<VeryStupidAgent> agents;
    public int id;
    public List<Node> chemin;

    public VeryStupidAgent(int[] currentPos, int[] goalPos, int id) {
        this.currentPos = currentPos;
        this.goalPos = goalPos;
        this.id = id;
        grid.placeAgent(currentPos, id);
        grid.printGrid();
    }

    public VeryStupidAgent() {

    }

    public void getShortestPath() {
        Node initialNode = new Node(currentPos[0], currentPos[1]);
        Node finalNode = new Node(goalPos[0], goalPos[1]);
        int rows = grid.getxSize();
        int cols = grid.getySize();
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
        chemin = aStar.findPath();
        chemin.remove(0);
    }

    public synchronized boolean move(int[] goal) {
        if((Math.abs(goal[0] - currentPos[0]) + Math.abs(goal[1] - currentPos[1]) == 1)) {
            boolean b = grid.updateGrid(currentPos, goal);
            if (b) {
                currentPos = goal.clone();
            }
            return b;
        }
        return false;
    }

    public void run() {
        getShortestPath();
        while (currentPos[0] != goalPos[0] || currentPos[1] != goalPos[1]) {
            int[] pos = {chemin.get(0).getRow(), chemin.get(0).getCol()};
            if (move(pos)) {
                chemin.remove(0);
            }
        }
    }

    public static void main(String[] args) {
        VeryStupidAgent a = new VeryStupidAgent();
        a.getShortestPath();
    }

}
