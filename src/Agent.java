import java.util.List;
import aStar.*;

public class Agent{
    private int [] currentPos;
    private int [] goalPos;
    static Agent [][] grid;
    static List<Agent> agents;

    public Agent(int[] currentPos, int[] goalPos) {
        this.currentPos = currentPos;
        this.goalPos = goalPos;
    }

    public Agent() {

    }

    public void getShortestPath() {
        Node initialNode = new Node(2, 1);
        Node finalNode = new Node(2, 4);
        int rows = 5;
        int cols = 5;
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
        int[][] blocksArray = new int[][]{ {2, 3}, {3, 3}};
        aStar.setBlocks(blocksArray);
        List<Node> path = aStar.findPath();
        for (Node node : path) {
            System.out.println(node);
        }
    }

    public static void main(String[] args) {
        Agent a = new Agent();
        a.getShortestPath();
    }

}
