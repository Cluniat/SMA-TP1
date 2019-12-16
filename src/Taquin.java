public class Taquin {


    public static void main(String[] args) {
        Grid grid = new Grid(5,5);
        Agent.grid = grid;
        int [] goal = {0, 0};
        int [] cur = {1, 1};
        Agent a1 = new Agent(cur, goal, 1);
        Thread t1 = new Thread(a1);
        int [] goal2 = {4, 0};
        int [] cur2 = {1, 0};
        Agent a2 = new Agent(cur2, goal2, 5);
        Thread t2 = new Thread(a2);
        int [] goal3 = {0, 4};
        int [] cur3 = {0, 1};
        Agent a3 = new Agent(cur3, goal3, 21);
        Thread t3 = new Thread(a3);
        t1.start();
        t2.start();
        t3.start();

    }
}