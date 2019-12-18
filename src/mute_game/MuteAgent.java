package mute_game;

import java.util.List;

import Common.Grid;
import aStar.*;

public class MuteAgent implements Runnable{
    private int [] currentPos;
    private int [] goalPos;
    static Grid grid;
    static List<MuteAgent> agents;
    public int id;
    public List<Node> chemin;

    public MuteAgent(int[] currentPos, int[] goalPos) {
        this.currentPos = currentPos;
        this.goalPos = goalPos;
        this.id = goalPos[1] * 5 + goalPos[0] + 1;
        grid.placeAgent(currentPos, id);
        grid.printGrid();
    }

    public MuteAgent() {

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
        if(grid.isAvailable(goal)) {
            grid.updateGrid(currentPos, goal);
            currentPos = goal;
            return true;
        }
        return false;
    }

    public void run() {
        getShortestPath();
        int nbRep = 0;
        while(currentPos[0] != goalPos[0] || currentPos[1] != goalPos[1]) {
            int [] pos = {chemin.get(0).getRow(), chemin.get(0).getCol()};
            if(move(pos)) {
                nbRep = 0;
                chemin.remove(0);
            }else {
                nbRep++;
                if (nbRep > 5 && (goalPos[0] != pos[0] || goalPos[1] != pos[1])) {
                    Node initialNode = new Node(currentPos[0], currentPos[1]);
                    Node finalNode = new Node(goalPos[0], goalPos[1]);
                    int rows = grid.getxSize();
                    int cols = grid.getySize();
                    AStar aStar = new AStar(rows, cols, initialNode, finalNode);
                    int [][] b = {pos};
                    aStar.setBlocks(b);
                    chemin = aStar.findPath();
                    System.out.println(chemin);
                    chemin.remove(0);
                }
            }
        }
    }

    public static void main(String[] args) {
        MuteAgent a = new MuteAgent();
        a.getShortestPath();
    }

}
