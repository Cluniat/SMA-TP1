package very_stupid_game;

import Common.Grid;

public class Main {

    public static void main(String[] args) {
        Grid grid = new Grid(5,5);
        very_stupid_game.VeryStupidTaquin taquin = new very_stupid_game.VeryStupidTaquin(grid);
        grid.addObserver(taquin);

    }
}
