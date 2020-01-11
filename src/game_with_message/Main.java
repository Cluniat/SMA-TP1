package game_with_message;

import Common.Grid;

public class Main {

    public static void main(String[] args) {
//        Common.Grid grid = new Common.Grid(5,5);
//        very_stupid_game.VeryStupidTaquin taquin = new very_stupid_game.VeryStupidTaquin(grid);
//        grid.addObserver(taquin);
        Grid grid = new Grid(5,5);
        MessageTaquin taquin = new MessageTaquin(grid);
        grid.addObserver(taquin);
//        Common.Grid grid = new Common.Grid(5,5);
//        game_with_message.Taquin taquin = new game_with_message.Taquin(grid);
//        grid.addObserver(taquin);
    }
}
