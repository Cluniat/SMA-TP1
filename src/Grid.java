import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private int [][] grid;
    private List<int[]> occupedPos;

    public Grid(int[][] grid) {
        this.grid = grid;
        occupedPos = new ArrayList<>();
    }

    public void updateGrid(@NotNull int [] oldPos, int [] newPos) {
        this.grid[newPos[0]][newPos[1]] = this.grid[oldPos[0]][oldPos[1]];
        this.grid[oldPos[0]][oldPos[1]] = 0;

    }
}