package game_with_message;

import java.util.List;

import Common.Grid;
import Common.Message;
import aStar.*;
public class MessageAgent implements Runnable{
    private int [] currentPos;
    private int [] goalPos;
    static Grid grid;
    static List<MessageAgent> agents;
    public int id;
    public List<Node> chemin;

    public MessageAgent(int[] currentPos, int[] goalPos) {
        this.currentPos = currentPos;
        this.goalPos = goalPos;
        this.id = goalPos[1] * 5 + goalPos[0] + 1;
        grid.placeAgent(currentPos, id);
        grid.printGrid();
        grid.subscribeMailBox(id);
    }

    public MessageAgent() {

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
            currentPos = goal.clone();
            return true;
        }
        return false;
    }

    public boolean haveToMove() {
        List<Message> msg = grid.getMail(id);
        for (Message mes : msg) {
            if (mes.getPos()[0] == currentPos[0] && mes.getPos()[1] == currentPos[1]) {
                grid.voidMail(id);
                return true;
            }
        }
        grid.voidMail(id);
        return false;

    }


    public synchronized boolean tryToMove() {
        boolean a = false;
        int [] nPos = currentPos.clone();
        if(nPos[0] != 0) {
            nPos[0] -= 1;
            a = move(nPos);
            nPos[0] += 1;
        }
        if(nPos[0] != 4 && !a) {
            nPos[0] += 1;
            a = move(nPos);
            nPos[0] -= 1;
        }
        if(nPos[1] != 0 && !a) {
            nPos[1] -= 1;
            a = move(nPos);
            nPos[1] += 1;
        }
        if(nPos[1] != 4 && !a) {
            nPos[1] += 1;
            a = move(nPos);
            nPos[1] -= 1;
        }
        return a;
    }


    public void run() {
        getShortestPath();
        int nbRep = 0;
        int noMove = 0;
        int lastMoveX = -1;
        int lastMoveY = -1;

        while(!grid.isFinish()) {
            if(currentPos[0] != goalPos[0] || currentPos[1] != goalPos[1]) {
                int[] pos = {chemin.get(0).getRow(), chemin.get(0).getCol()};
                if (move(pos)) {
                    nbRep = 0;
                    noMove = 0;
                    chemin.remove(0);
                    lastMoveX = -1;
                    lastMoveY = -1;
                } else if (haveToMove()) {
                    if(tryToMove()) {
                        Node initialNode = new Node(currentPos[0], currentPos[1]);
                        Node finalNode = new Node(goalPos[0], goalPos[1]);
                        int rows = grid.getxSize();
                        int cols = grid.getySize();
                        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
                        chemin = aStar.findPath();
                        System.out.println(chemin);
                        chemin.remove(0);
                        lastMoveX = -1;
                        lastMoveY = -1;

                    }

                } else {
                    noMove++;
                    nbRep++;
                    if (noMove > 20) {
                        int idMove = grid.grid[chemin.get(0).getRow()][chemin.get(0).getCol()];
                        Message msg = new Message(this.id, idMove, "MOVE", pos);
                        grid.sendMail(idMove, msg);
                        try {
                            Thread.sleep(100) ;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        noMove = 0;
                    } else {
                    if (nbRep > 5 && (goalPos[0] != pos[0] || goalPos[1] != pos[1])) {
                        Node initialNode = new Node(currentPos[0], currentPos[1]);
                        Node finalNode = new Node(goalPos[0], goalPos[1]);
                        int rows = grid.getxSize();
                        int cols = grid.getySize();
                        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
                        int[][] b = {pos};
                        aStar.setBlocks(b);
                        if (lastMoveX != -1) {
                            b = new int[][]{{lastMoveX, lastMoveY}};
                            aStar.setBlocks(b);
                        }
                        chemin = aStar.findPath();
                        System.out.println(chemin);
                        chemin.remove(0);
                        lastMoveX = pos[0];
                        lastMoveY = pos[1];
                        nbRep = 0;
                    }}
                }
            } else {
                //System.out.println(id);
                if (haveToMove()) {
                    if (tryToMove()) {
                        Node initialNode = new Node(currentPos[0], currentPos[1]);
                        Node finalNode = new Node(goalPos[0], goalPos[1]);
                        int rows = grid.getxSize();
                        int cols = grid.getySize();
                        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
                        chemin = aStar.findPath();
                        chemin.remove(0);
                    }
                }

            }
        }

    }

    public static void main(String[] args) {
        MessageAgent a = new MessageAgent();
        a.getShortestPath();
    }

}
