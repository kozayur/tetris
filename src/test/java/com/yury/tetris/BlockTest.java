package com.yury.tetris;

import org.junit.Test;
import static org.junit.Assert.*;

public class BlockTest {

    @Test
    public void testParseFromString() {
        String blockCode1 = "Q1";

        Block result1 = Block.parse(blockCode1);

        assertEquals("Block parse is not correct for column", 1, result1.getColumn());
        assertEquals("Block parse is not correct for shape", 'Q', result1.getShape());

        String blockCode2 = "Z5";

        Block result2 = Block.parse(blockCode2);

        assertEquals("Block parse is not correct for column", 5, result2.getColumn());
        assertEquals("Block parse is not correct for shape", 'Z', result2.getShape());
    }

    @Test
    public void testRequiredColumnSpace() {
        Block blockQ = new Block(1, 'Q');
        assertArrayEquals("Q block columns incorrect", new int[]{1, 2}, blockQ.requiredColumnSpace());

        Block blockZ = new Block(2, 'Z');
        assertArrayEquals("Z block columns incorrect", new int[]{2, 3, 4}, blockZ.requiredColumnSpace());

        Block blockS = new Block(3, 'S');
        assertArrayEquals("S block columns incorrect", new int[]{3, 4, 5}, blockS.requiredColumnSpace());

        Block blockT = new Block(4, 'T');
        assertArrayEquals("T block columns incorrect", new int[]{4, 5, 6}, blockT.requiredColumnSpace());

        Block blockI = new Block(5, 'I');
        assertArrayEquals("I block columns incorrect", new int[]{5, 6, 7, 8}, blockI.requiredColumnSpace());

        Block blockL = new Block(6, 'L');
        assertArrayEquals("L block columns incorrect", new int[]{6, 7}, blockL.requiredColumnSpace());

        Block blockJ = new Block(7, 'J');
        assertArrayEquals("J block columns incorrect", new int[]{7, 8}, blockJ.requiredColumnSpace());
    }

    @Test
    public void testPositionIntoGrid() {
        Block blockQ = new Block(1, 'Q');
        blockQ.positionIntoGrid(new int[]{3, 3});
        assertEquals("Q block is not vertically positioned correctly", 5, blockQ.getVerticalPosition());
        blockQ.positionIntoGrid(new int[]{4, 6});
        assertEquals("Q block is not vertically positioned correctly", 8, blockQ.getVerticalPosition());

        Block blockZ = new Block(2, 'Z');
        blockZ.positionIntoGrid(new int[]{-1, -1, -1});
        assertEquals("Z block is not vertically positioned correctly", 1, blockZ.getVerticalPosition());
        blockZ.positionIntoGrid(new int[]{0, -1, -1});
        assertEquals("Z block is not vertically positioned correctly", 1, blockZ.getVerticalPosition());

        Block blockS = new Block(3, 'S');
        blockS.positionIntoGrid(new int[]{-1, -1, -1});
        assertEquals("S block is not vertically positioned correctly", 1, blockS.getVerticalPosition());
        blockS.positionIntoGrid(new int[]{-1, -1, 0});
        assertEquals("S block is not vertically positioned correctly", 1, blockS.getVerticalPosition());

        Block blockT = new Block(4, 'T');
        blockT.positionIntoGrid(new int[]{-1, -1, -1});
        assertEquals("T block is not vertically positioned correctly", 1, blockT.getVerticalPosition());
        blockT.positionIntoGrid(new int[]{-1, -1, 0});
        assertEquals("T block is not vertically positioned correctly", 1, blockT.getVerticalPosition());
        blockT.positionIntoGrid(new int[]{0, -1, 0});
        assertEquals("T block is not vertically positioned correctly", 1, blockT.getVerticalPosition());

        Block blockI = new Block(5, 'I');
        blockI.positionIntoGrid(new int[]{-1, -1, -1, -1});
        assertEquals("I block is not vertically positioned correctly", 0, blockI.getVerticalPosition());
        blockI.positionIntoGrid(new int[]{-1, 0, -1, -1});
        assertEquals("I block is not vertically positioned correctly", 1, blockI.getVerticalPosition());

        Block blockL = new Block(6, 'L');
        blockL.positionIntoGrid(new int[]{-1, -1});
        assertEquals("L block is not vertically positioned correctly", 2, blockL.getVerticalPosition());
        blockL.positionIntoGrid(new int[]{-1, 0});
        assertEquals("L block is not vertically positioned correctly", 3, blockL.getVerticalPosition());

        Block blockJ = new Block(7, 'J');
        blockJ.positionIntoGrid(new int[]{-1, -1});
        assertEquals("J block is not vertically positioned correctly", 2, blockJ.getVerticalPosition());
        blockJ.positionIntoGrid(new int[]{-1, 0});
        assertEquals("J block is not vertically positioned correctly", 3, blockJ.getVerticalPosition());
    }

    @Test
    public void testPositionIntoGridColumnBasedOnVerticalPosition() {
        Block blockQ = new Block(1, 'Q');
        blockQ.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("Q block positions in column are not correct", new int[]{2, 3}, blockQ.positionIntoGridColumnBasedOnVerticalPosition(0));
        assertArrayEquals("Q block positions in column are not correct", new int[]{2, 3}, blockQ.positionIntoGridColumnBasedOnVerticalPosition(1));

        Block blockZ = new Block(1, 'Z');
        blockZ.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("Z block positions in column are not correct", new int[]{3}, blockZ.positionIntoGridColumnBasedOnVerticalPosition(0));
        assertArrayEquals("Z block positions in column are not correct", new int[]{2, 3}, blockZ.positionIntoGridColumnBasedOnVerticalPosition(1));
        assertArrayEquals("Z block positions in column are not correct", new int[]{2}, blockZ.positionIntoGridColumnBasedOnVerticalPosition(2));

        Block blockS = new Block(1, 'S');
        blockS.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("S block positions in column are not correct", new int[]{2}, blockS.positionIntoGridColumnBasedOnVerticalPosition(0));
        assertArrayEquals("S block positions in column are not correct", new int[]{2, 3}, blockS.positionIntoGridColumnBasedOnVerticalPosition(1));
        assertArrayEquals("S block positions in column are not correct", new int[]{3}, blockS.positionIntoGridColumnBasedOnVerticalPosition(2));

        Block blockT = new Block(1, 'T');
        blockT.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("T block positions in column are not correct", new int[]{3}, blockT.positionIntoGridColumnBasedOnVerticalPosition(0));
        assertArrayEquals("T block positions in column are not correct", new int[]{2, 3}, blockT.positionIntoGridColumnBasedOnVerticalPosition(1));
        assertArrayEquals("T block positions in column are not correct", new int[]{3}, blockT.positionIntoGridColumnBasedOnVerticalPosition(2));

        Block blockI = new Block(1, 'I');
        blockI.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("I block positions in column are not correct", new int[]{2}, blockI.positionIntoGridColumnBasedOnVerticalPosition(0));
        assertArrayEquals("I block positions in column are not correct", new int[]{2}, blockI.positionIntoGridColumnBasedOnVerticalPosition(1));
        assertArrayEquals("I block positions in column are not correct", new int[]{2}, blockI.positionIntoGridColumnBasedOnVerticalPosition(2));
        assertArrayEquals("I block positions in column are not correct", new int[]{2}, blockI.positionIntoGridColumnBasedOnVerticalPosition(3));

        Block blockL = new Block(1, 'L');
        blockL.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("L block positions in column are not correct", new int[]{2, 3, 4}, blockL.positionIntoGridColumnBasedOnVerticalPosition(0));
        assertArrayEquals("L block positions in column are not correct", new int[]{2}, blockL.positionIntoGridColumnBasedOnVerticalPosition(1));

        Block blockJ = new Block(1, 'J');
        blockJ.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("J block positions in column are not correct", new int[]{2}, blockJ.positionIntoGridColumnBasedOnVerticalPosition(0));
        assertArrayEquals("J block positions in column are not correct", new int[]{2, 3, 4}, blockJ.positionIntoGridColumnBasedOnVerticalPosition(1));
    }

    @Test
    public void testAffectedLines() {
        Block blockQ = new Block(1, 'Q');
        blockQ.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("Affected lines are not correct", new int[]{2, 2}, blockQ.affectedLines());

        Block blockZ = new Block(1, 'Z');
        blockZ.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("Affected lines are not correct", new int[]{2, 2}, blockZ.affectedLines());

        Block blockS = new Block(1, 'S');
        blockS.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("Affected lines are not correct", new int[]{2, 2}, blockS.affectedLines());

        Block blockT = new Block(1, 'T');
        blockT.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("Affected lines are not correct", new int[]{2, 2}, blockT.affectedLines());

        Block blockI = new Block(1, 'I');
        blockI.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("Affected lines are not correct", new int[]{2, 1}, blockI.affectedLines());

        Block blockL = new Block(1, 'L');
        blockL.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("Affected lines are not correct", new int[]{2, 3}, blockL.affectedLines());

        Block blockJ = new Block(1, 'J');
        blockJ.positionIntoGrid(new int[]{1, 1});
        assertArrayEquals("Affected lines are not correct", new int[]{2, 3}, blockJ.affectedLines());
    }
}
