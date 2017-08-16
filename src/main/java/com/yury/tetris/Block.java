package com.yury.tetris;

public class Block {
    final int column;
    final char shape;

    int verticalPosition = -1;

    public Block(int column, char shape) {
        this.column = column;
        this.shape = shape;
    }

    public int getColumn() {
        return column;
    }

    public char getShape() {
        return shape;
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    /**
     * Create a block from the string. A string is a single letter from the set Q, Z, S, T, I, L, and J and a single-digit integer.
     * The integer represents the left-most column of the grid that the shape occupies, starting from zero.
     *
     * @param blockCode
     * @return
     */
    public static Block parse(String blockCode) {
        return new Block((int)blockCode.charAt(1) - (int)'0', blockCode.charAt(0));
    }

    /**
     * return columns of the grid required to place a block
     *
     * @return
     */
    public int[] requiredColumnSpace() {
        switch (shape) {
            case 'Q': // Square
            case 'L': // L shape with short end on the bottom
            case 'J': // J shape with short end on the bottom
                return new int[]{column, column+1};
            case 'Z': // horizontal Z shape
            case 'S': // horizontal S shape
            case 'T': // T shape with the bar on the top
                return new int[]{column, column+1, column+2};
            case 'I': // horizontal I shape
                return new int[]{column, column+1, column+2, column+3};
            default: throw new RuntimeException("Invalid shape: " + shape);
        }
    }

    /**
     * Compute vertical position of a block.
     *
     * @param bottomLine positions taken by the previously placed blocks in columns required by the new block.
     */
    public void positionIntoGrid(int[] bottomLine) {
        int positionBasedOnBottomLine = 0;
        for (int indexOnBottomLine=0; indexOnBottomLine<bottomLine.length; ++indexOnBottomLine) {
            switch (shape) {
                case 'Q':
                    // xx
                    // xx
                    positionBasedOnBottomLine = Math.max(positionBasedOnBottomLine, bottomLine[indexOnBottomLine] + 2);
                    break;
                case 'L':
                    // x
                    // x
                    // xx
                case 'J':
                    //  x
                    //  x
                    // xx
                    positionBasedOnBottomLine = Math.max(positionBasedOnBottomLine, bottomLine[indexOnBottomLine] + 3);
                    break;
                case 'I':
                    // xxxx
                    positionBasedOnBottomLine = Math.max(positionBasedOnBottomLine, bottomLine[indexOnBottomLine] + 1);
                    break;
                case 'Z':
                    // xx
                    //  xx
                    if (indexOnBottomLine == 0)
                        positionBasedOnBottomLine = Math.max(positionBasedOnBottomLine, bottomLine[indexOnBottomLine] + 1);
                    else
                        positionBasedOnBottomLine = Math.max(positionBasedOnBottomLine, bottomLine[indexOnBottomLine] + 2);
                    break;
                case 'S':
                    //  xx
                    // xx
                    if (indexOnBottomLine == 2)
                        positionBasedOnBottomLine = Math.max(positionBasedOnBottomLine, bottomLine[indexOnBottomLine] + 1);
                    else
                        positionBasedOnBottomLine = Math.max(positionBasedOnBottomLine, bottomLine[indexOnBottomLine] + 2);
                    break;
                case 'T':
                    // xxx
                    //  x
                    if (indexOnBottomLine == 1)
                        positionBasedOnBottomLine = Math.max(positionBasedOnBottomLine, bottomLine[indexOnBottomLine] + 2);
                    else
                        positionBasedOnBottomLine = Math.max(positionBasedOnBottomLine, bottomLine[indexOnBottomLine] + 1);
                    break;
                default: throw new RuntimeException("Invalid shape: " + shape);
            }
        }
        verticalPosition = positionBasedOnBottomLine;
    }

    /**
     * Compute block positions in a specified column based on a verticalPosition of a block.
     *
     * @param column index of the column in the grid.
     * @return ordered positions in the column. First element is the lowest in the column
     */
    public int[] positionIntoGridColumnBasedOnVerticalPosition(int column) {
        // make sure vertical position is already calculated.
        assert(verticalPosition > 0);

        switch (shape) {
            case 'Q': // Square
                return new int[]{verticalPosition-1, verticalPosition};
            case 'L': // L shape with short end on the bottom
                if (column == 0)
                    return new int[]{verticalPosition-2, verticalPosition-1, verticalPosition};
                else
                    return new int[]{verticalPosition-2};
            case 'J': // J shape with short end on the bottom
                if (column == 1)
                    return new int[]{verticalPosition-2, verticalPosition-1, verticalPosition};
                else
                    return new int[]{verticalPosition-2};
            case 'Z': // horizontal Z shape
                if (column == 0)
                    return new int[]{verticalPosition};
                else if (column == 1)
                    return new int[]{verticalPosition-1, verticalPosition};
                else
                    return new int[]{verticalPosition-1};
            case 'S': // horizontal S shape
                if (column == 0)
                    return new int[]{verticalPosition-1};
                else if (column == 1)
                    return new int[]{verticalPosition-1, verticalPosition};
                else
                    return new int[]{verticalPosition};
            case 'T': // T shape with the bar on the top
                if (column == 1)
                    return new int[]{verticalPosition-1, verticalPosition};
                else
                    return new int[]{verticalPosition};
            case 'I': // horizontal I shape
                return new int[]{verticalPosition};
            default: throw new RuntimeException("Invalid shape: " + shape);
        }
    }

    /**
     * Returns an array of size 2, where first element is the lowest affected row (0 based)
     * and second element is the number of affected rows.
     *
     * Obviously, a block can affect 1, 2 or 3 lines based on the shape
     *
     * @return
     */
    public int[] affectedLines() {
        switch (shape) {
            case 'Q': // Square
            case 'Z': // horizontal Z shape
            case 'S': // horizontal S shape
            case 'T': // T shape with the bar on the top
                return new int[] {verticalPosition-1, 2};
            case 'L': // L shape with short end on the bottom
            case 'J': // J shape with short end on the bottom
                return new int[]{verticalPosition-2, 3};
            case 'I': // horizontal I shape
                return new int[]{verticalPosition, 1};
            default: throw new RuntimeException("Invalid shape: " + shape);
        }
    }
}
