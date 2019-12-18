import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Taquin extends JFrame implements Observer {

    private JPanel panel;
    private Grid grid;
    private ArrayList<JButton> rectangles = new ArrayList<>();

    public Taquin(Grid grid) {
        this.grid = grid;
        Agent.grid = grid;
        int [] goal = {0, 0};
        int [] cur = {1, 1};
        Agent a1 = new Agent(cur, goal);
        Thread t1 = new Thread(a1);
        int [] goal2 = {4, 0};
        int [] cur2 = {1, 0};
        Agent a2 = new Agent(cur2, goal2);
        Thread t2 = new Thread(a2);
        int [] goal3 = {0, 4};
        int [] cur3 = {0, 1};
        Agent a3 = new Agent(cur3, goal3);
        Thread t3 = new Thread(a3);
        int [] goal4 = {3, 4};
        int [] cur4 = {0, 0};
        Agent a4 = new Agent(cur4, goal4);
        Thread t4 = new Thread(a4);
        int [] goal5 = {2, 4};
        int [] cur5 = {3, 1};
        Agent a5 = new Agent(cur5, goal5);
        Thread t5 = new Thread(a5);
        int [] goal6 = {1, 2};
        int [] cur6 = {1, 3};
        Agent a6 = new Agent(cur6, goal6);
        Thread t6 = new Thread(a6);
        int [] goal7 = {4, 4};
        int [] cur7 = {1, 2};
        Agent a7 = new Agent(cur7, goal7);
        Thread t7 = new Thread(a7);
        int [] goal8 = {3, 3};
        int [] cur8 = {4, 1};
        Agent a8 = new Agent(cur8, goal8);
        Thread t8 = new Thread(a8);


        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        generateRectangle();
        drawPanel();

        setSize(255, 290);
        setTitle("Taquin");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
    }

    private void drawPanel() {
        panel.removeAll();
        panel.setLayout(null);
        add(Box.createRigidArea(new Dimension(0, grid.getxSize()+1)), BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        ArrayList<JButton> tempRectangles = this.rectangles;

        for (int i = 0; i < grid.getxSize(); i++) {
            for (int j = 0; j < grid.getySize(); j++) {
                for (JButton rectangle: tempRectangles) {
                    if(rectangle.getText().equals("" + grid.grid[j][i])) {
                        panel.add(rectangle);
                        rectangle.setBounds(j*50, i*50, 50, 50);
                    }
                }
            }
        }
        panel.updateUI();
    }

    private void generateRectangle() {
        for (int i = 0; i < grid.getxSize(); i++) {
            for (int j = 0; j < grid.getySize(); j++) {
                JButton rectangle = new JButton();
                if(grid.grid[j][i] != 0) {
                    rectangle.setBackground(ColorHelper.generateColor());
                    rectangle.setText(""+grid.grid[j][i]);
                    this.rectangles.add(rectangle);
                }
            }
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        drawPanel();
    }

}