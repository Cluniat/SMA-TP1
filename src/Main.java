import Common.Grid;
import mute_game.MuteTaquin;

public class Main {

    public static void main(String[] args) {
//        Common.Grid grid = new Common.Grid(5,5);
//        very_stupid_game.VeryStupidTaquin taquin = new very_stupid_game.VeryStupidTaquin(grid);
//        grid.addObserver(taquin);
        Grid grid = new Grid(5,5);
        MuteTaquin taquin = new MuteTaquin(grid);
        grid.addObserver(taquin);
//        Common.Grid grid = new Common.Grid(5,5);
//        Taquin taquin = new Taquin(grid);
//        grid.addObserver(taquin);
    }
}
