package com.yury.tetris;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("application.properties")
class Tetris implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        InputStream file = null;

        if (args.length > 1)
            file = new FileInputStream(args[0]);
        else
            file = this.getClass().getResourceAsStream("/input.txt");

        Scanner input = new Scanner(new InputStreamReader(file));
        PrintWriter output = new PrintWriter(new FileOutputStream("output.txt"));

        while (input.hasNext()) {

            String line = input.nextLine();

            System.out.println(line);

            String[] blockCodes = line.split(",");

            List<List<Boolean>> grid = new ArrayList<>(10);
            for (int i = 0; i<10; i++) {
                grid.add(new LinkedList<>());
            }

            Arrays.stream(blockCodes)
                    .map(blockCode -> Block.parse(blockCode))
                    .forEachOrdered(block -> {
                        // columns that are used when a block is placed into the grid
                int[] requiredColumnSpace = block.requiredColumnSpace();

                // Bottom line represents top positions occupied in columns required by the block to be placed.
                // The row numbers in bottom line are 0 based. Thus, empty grip would have bottom line of -1's.
                int[] bottomLine = new int[requiredColumnSpace.length];

                for (int i=0; i<requiredColumnSpace.length; i++) {
                    bottomLine[i] = grid.get(requiredColumnSpace[i]).size() - 1;
                }

                block.positionIntoGrid(bottomLine);

                for (int i=0; i<requiredColumnSpace.length; i++) {
                    int[] blockPositionsInGridColumn = block.positionIntoGridColumnBasedOnVerticalPosition(i);
                    addPositionsToGridColumn(grid.get(requiredColumnSpace[i]), blockPositionsInGridColumn);
                }

                int[] affectedLines = block.affectedLines();
                removeCompleteRows(grid, affectedLines[0], affectedLines[1]);
            });

            // Compute the height of the grid
            int heightOfGrid = grid.stream()
                    .map(column -> column.size())
                    .reduce(Math::max).get();

            System.out.println("Height of grid is " + heightOfGrid);
            output.println(heightOfGrid);
        }
        output.flush();
        output.close();
    }

    /**
     * Fill in block positions in a single grid column
     *
     * @param gridColumn
     * @param blockPositions ordered array of block positions. The first element is the most bottom position.
     */
    void addPositionsToGridColumn(List<Boolean> gridColumn, int[] blockPositions) {
        // Fill in empty positions below the block
        for (int i=gridColumn.size(); i<blockPositions[0]; i++) {
            gridColumn.add(Boolean.FALSE);
        }

        // Fill in block positions
        for (int i=0; i<blockPositions.length; i++) {
            gridColumn.add(Boolean.TRUE);
        }
    }

    /**
     * Remove rows where all positions are filled in by blocks
     * The rows that can be removed after the block is placed are specified by the last two parameters. No need to look at other rows.
     *
     * @param grid
     * @param lowestAffectedRow
     * @param numberOfAffectedRows
     */
    void removeCompleteRows(List<List<Boolean>> grid, final int lowestAffectedRow, final int numberOfAffectedRows) {
        List<Iterator<Boolean>> row = new ArrayList<>(grid.size());
        int completeRows = 0;

        for (int rowIndex = lowestAffectedRow; rowIndex < lowestAffectedRow + numberOfAffectedRows - completeRows; rowIndex++) {
            for (Iterator<List<Boolean>> gridIter = grid.iterator(); gridIter.hasNext(); ) {
                List<Boolean> column = gridIter.next();
                if (column.size()-1 < rowIndex) {
                    // This column does not have anything at this height and above
                    return;
                } else if (column.get(rowIndex) == Boolean.FALSE) {
                    // This row has empty position at this column, so it is not complete
                    break;
                } else {
                    row.add(column.listIterator(rowIndex));
                }
            }

            // If the row is complete, remove row position from every column.
            if (row.size() == grid.size()) {
                row.forEach(
                        // delete this row position from every column
                        column -> {
                            column.next();
                            column.remove();
                        }
                );

                // When a row is deleted we need to adjust indexes, so we can continue iterate over rows.
                completeRows += 1;
                rowIndex -= 1;
            }
            row.clear();
        }
    }
}