public class Main {

    public static void main(String[] args) {
//        Grid grid = new Grid(5,5);
//        VeryStupidTaquin taquin = new VeryStupidTaquin(grid);
//        grid.addObserver(taquin);
        Grid grid = new Grid(5,5);
        Taquin taquin = new Taquin(grid);
        grid.addObserver(taquin);
    }
}
