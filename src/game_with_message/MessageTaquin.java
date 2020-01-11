package game_with_message;

import Common.ColorHelper;
import Common.Grid;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MessageTaquin extends JFrame implements Observer {

    private JPanel panel;
    private Grid grid;
    private ArrayList<JButton> rectangles = new ArrayList<>();

    public MessageTaquin(Grid grid) {
        this.grid = grid;
        MessageAgent.grid = grid;
        int [] goal = {0, 0};
        int [] cur = {1, 1};
        MessageAgent a1 = new MessageAgent(cur, goal);
        Thread t1 = new Thread(a1);
        int [] goal2 = {4, 0};
        int [] cur2 = {1, 0};
        MessageAgent a2 = new MessageAgent(cur2, goal2);
        Thread t2 = new Thread(a2);
        int [] goal3 = {0, 4};
        int [] cur3 = {0, 1};
        MessageAgent a3 = new MessageAgent(cur3, goal3);
        Thread t3 = new Thread(a3);
        int [] goal4 = {3, 4};
        int [] cur4 = {0, 0};
        MessageAgent a4 = new MessageAgent(cur4, goal4);
        Thread t4 = new Thread(a4);
        int [] goal5 = {2, 4};
        int [] cur5 = {3, 1};
        MessageAgent a5 = new MessageAgent(cur5, goal5);
        Thread t5 = new Thread(a5);
        int [] goal6 = {1, 2};
        int [] cur6 = {1, 3};
        MessageAgent a6 = new MessageAgent(cur6, goal6);
        Thread t6 = new Thread(a6);
        int [] goal7 = {4, 4};
        int [] cur7 = {1, 2};
        MessageAgent a7 = new MessageAgent(cur7, goal7);
        Thread t7 = new Thread(a7);
        int [] goal8 = {3, 3};
        int [] cur8 = {4, 1};
        MessageAgent a8 = new MessageAgent(cur8, goal8);
        Thread t8 = new Thread(a8);
        int [] goal9 = {1, 4};
        int [] cur9 = {1, 4};
        MessageAgent a9 = new MessageAgent(cur9, goal9);
        Thread t9 = new Thread(a9);
        int [] goal10 = {1, 3};
        int [] cur10 = {3, 4};
        MessageAgent a10 = new MessageAgent(cur10, goal10);
        Thread t10 = new Thread(a10);
        int [] goal11 = {2, 2};
        int [] cur11 = {2, 3};
        MessageAgent a11 = new MessageAgent(cur11, goal11);
        Thread t11 = new Thread(a11);
        int [] goal12 = {3, 0};
        int [] cur12 = {4, 4};
        MessageAgent a12 = new MessageAgent(cur12, goal12);
        Thread t12 = new Thread(a12);


        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        generateRectangle();
        drawPanel();

        setSize(255, 290);
        setTitle("game_with_message.Taquin");
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
        t9.start();
        t10.start();
        t11.start();
        t12.start();
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