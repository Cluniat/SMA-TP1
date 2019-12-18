package mute_game;

import Common.Grid;

public class Main {

    public static void main(String[] args) {
        Grid grid = new Grid(5,5);
        MuteTaquin taquin = new MuteTaquin(grid);
        grid.addObserver(taquin);
    }
}
