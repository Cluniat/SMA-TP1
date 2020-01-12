package game_with_message;

import java.util.List;

import Common.Grid;
import Common.Message;
import aStar.*;
public class MessageAgent implements Runnable{
    private int [] currentPos;
    private int [] goalPos;
    static Grid grid;
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
        /*Calcule le plus court chemin pour atteindre l'objectif*/

        Node initialNode = new Node(currentPos[0], currentPos[1]);
        Node finalNode = new Node(goalPos[0], goalPos[1]);
        int rows = grid.getxSize();
        int cols = grid.getySize();
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
        chemin = aStar.findPath();
        chemin.remove(0);
    }

    public synchronized boolean move(int[] goal) {
        /*Fonction essayant de déplacer l'agent sur la case goal*/
        if((Math.abs(goal[0] - currentPos[0]) + Math.abs(goal[1] - currentPos[1]) == 1)) {
            boolean b = grid.updateGrid(currentPos, goal);
            if (b) {
                currentPos = goal.clone();
            }
            return b;
        }
        return false;
    }

    public boolean haveToMove() {
        /*Fonction vérifiant si l'agent a un message le concernant (demande de libérer la case sur laquel il est)*/
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
        /*fonction utilisé lors d'une agression pour tenter de se déplacer à tout prix (dans n'importe quelle direction)*/
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
        /*Fonction principale de l'agent*/
        getShortestPath();
        int nbRep = 0;
        int noMove = 0;
        //noRep permet de savoir combien de temps l'agent n'a pas bougé (pour trouver un autre chemin
        //noMove permet la même chose mais est utilisé pour savoir quand envoyer un message
        int lastMoveX = -1;
        int lastMoveY = -1;
        //lastMove permet d'enregistrer le dernier obstacle pour pouvoir en avoir 2 dans le calcul de chemin

        while(!grid.isFinish()) {
            //Tant que tout les agents ne sont pas à leur place
            if(currentPos[0] != goalPos[0] || currentPos[1] != goalPos[1]) {
                //Si l'agent n'a pas encore atteint son but.
                int[] pos = {chemin.get(0).getRow(), chemin.get(0).getCol()};
                if (move(pos)) {
                    //Il tente de se déplacer
                    nbRep = 0;
                    noMove = 0;
                    chemin.remove(0);
                    lastMoveX = -1;
                    lastMoveY = -1;
                } else if (haveToMove()) {
                    //Si il n'y arrive pas, il écoute les messages, et si il en a le concernant, il tente de se déplacer
                    if(tryToMove()) {
                        //Si il arrive à se déplacer, il recalule le chemin depuis sa nouvelle position
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
                        try {
                            Thread.sleep(500) ;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                } else {
                    noMove++;
                    nbRep++;
                    if (noMove > 10) {
                        //Si cela fait plus de 10 fois qu'il ne bouge pas, il demande à l'agent occupant la case de la libérer
                        int idMove = grid.grid[chemin.get(0).getRow()][chemin.get(0).getCol()];
                        if (idMove != 0) {
                            Message msg = new Message(this.id, idMove, "MOVE", pos);
                            grid.sendMail(idMove, msg);
                        }
                        noMove = 0;
                        nbRep = 0;
                    } else {
                    if (nbRep > 5 && (goalPos[0] != pos[0] || goalPos[1] != pos[1])) {
                        //Si il n'a pas bougé depuis plus de 5 itérations, il recalcule un nouvel itinéraire en ajoutant un obstacle sur la case bloqué
                        Node initialNode = new Node(currentPos[0], currentPos[1]);
                        Node finalNode = new Node(goalPos[0], goalPos[1]);
                        int rows = grid.getxSize();
                        int cols = grid.getySize();
                        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
                        int[][] b = {pos};
                        aStar.setBlocks(b);
                        if (lastMoveX != -1) {
                            //Il ajout aussi l'obstacle précédent le cas échéant
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
