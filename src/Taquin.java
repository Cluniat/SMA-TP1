import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Taquin extends JFrame implements Observer {

    private JPanel panel;
    private Grid grid;

    public Taquin(Grid grid) {
        this.grid = grid;
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

        panel = new JPanel();


        drawPanel();

        setSize(325, 275);
        setTitle("Taquin");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        t1.start();
        t2.start();
        t3.start();
    }

    private void drawPanel() {
        panel.removeAll();
        panel.setLayout(new GridLayout(grid.getxSize(), grid.getySize(), 0, 0));
        add(Box.createRigidArea(new Dimension(0, grid.getxSize()+1)), BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        for (int i = 0; i < grid.getxSize(); i++) {
            for (int j = 0; j < grid.getySize(); j++) {
                JButton rectangle = new JButton();
                rectangle.setBackground(ColorHelper.generateColor());
                Label label = new Label(""+grid.grid[j][i]);
                rectangle.add(label);
                panel.add(rectangle);
            }
        }
        panel.updateUI();
    }

    @Override
    public void update(Observable o, Object arg) {
        drawPanel();
    }

}