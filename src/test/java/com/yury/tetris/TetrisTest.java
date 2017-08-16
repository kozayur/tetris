package com.yury.tetris;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;
import static org.junit.Assert.*;

public class TetrisTest {

    @Test
    public void testAddPositionsToGridColumn() {
        Tetris engine = new Tetris();

        List<Boolean> gridColumn = new LinkedList<>();
        gridColumn.add(Boolean.TRUE);

        engine.addPositionsToGridColumn(gridColumn, new int[]{2, 3});

        assertArrayEquals("Positions were not added to the grid correctly",
                new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE},
                gridColumn.toArray());
    }

    @Test
    public void testRemoveCompleteRows() {
        Tetris engine = new Tetris();
        List<List<Boolean>> grid = new ArrayList<>(3);
        for (int i=0; i<3; i++) {
            List<Boolean> column = new LinkedList<Boolean>();
            grid.add(new LinkedList<Boolean>());
        }

        grid.get(0).add(Boolean.TRUE);
        grid.get(0).add(Boolean.TRUE);
        grid.get(0).add(Boolean.TRUE);
        grid.get(0).add(Boolean.TRUE);

        grid.get(1).add(Boolean.TRUE);
        grid.get(1).add(Boolean.TRUE);
        grid.get(1).add(Boolean.FALSE);
        grid.get(1).add(Boolean.TRUE);

        grid.get(2).add(Boolean.TRUE);
        grid.get(2).add(Boolean.TRUE);
        grid.get(2).add(Boolean.TRUE);

        engine.removeCompleteRows(grid, 0, 4);

        // Only rows 0 and 1 are deleted because row 2 has a false in column 2, and row 3 has nothing in column 3
        assertArrayEquals("", new Boolean[]{Boolean.TRUE, Boolean.TRUE}, grid.get(0).toArray());
        assertArrayEquals("", new Boolean[]{Boolean.FALSE, Boolean.TRUE}, grid.get(1).toArray());
        assertArrayEquals("", new Boolean[]{Boolean.TRUE}, grid.get(2).toArray());
    }
}
