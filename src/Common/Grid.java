package Common;

import org.jetbrains.annotations.NotNull;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

public class Grid extends Observable {

    public int[][] grid;
    private int xSize;
    private int ySize;
    private ConcurrentHashMap<Integer, List<Message>> mailBox;
    private List<int[]> occupedPos;

    public Grid(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.grid = new int[xSize][ySize];
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                grid[i][j] = 0;
            }
        }
        mailBox = new ConcurrentHashMap<>();
        // occupedPos = new ArrayList<>();
    }

    public void placeAgent(int[] pos, int id) {
        grid[pos[0]][pos[1]] = id;
    }

    public synchronized void updateGrid(@NotNull int[] oldPos, int[] newPos) {
        this.grid[newPos[0]][newPos[1]] = this.grid[oldPos[0]][oldPos[1]];
        this.grid[oldPos[0]][oldPos[1]] = 0;
        printGrid();
        setChanged();
        notifyObservers();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public synchronized boolean isAvailable(int[] pos) {
        return (pos[0] < xSize && pos[0] >= 0 && pos[1] < ySize && pos[1] >= 0 && grid[pos[0]][pos[1]] == 0);
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public synchronized void printGrid() {
        System.out.println("Aff");
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                System.out.print(grid[j][i]);
                System.out.print("|");
            }
            System.out.println();
        }
    }

    public void subscribeMailBox(int id) {
        mailBox.put(id, new ArrayList<Message>());
    }

    public synchronized List<Message> getMail(int id) {
        List<Message> msg = new ArrayList<>();
        List<Message> a = mailBox.get(id);
        try {
            for (Message m : a
            ) {
                msg.add(m.clone());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return msg;
    }

    public void sendMail(int id, Message msg) {
        mailBox.get(id).add(msg);
        //System.out.println(mailBox.get(id).size());
        //System.out.println(id);
    }

    public void voidMail(int id) {
        mailBox.get(id).clear();
    }


    public boolean isFinish() {
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                if(grid[i][j] != 0 && grid[i][j] != j * 5 + i + 1) {
                    return false;
                }
            }

        }
        return true;
    }

}