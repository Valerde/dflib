package com.nhl.dflib.print;

import com.nhl.dflib.DataFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataFrameInlinePrintWorkerTest {

    private DataFrame df;

    @BeforeEach
    public void initDataFrameParts() {
        this.df = DataFrame.newFrame("col1", "column2").foldByRow(
                "one", 1,
                "two", 2,
                "three", 3,
                "four", 4);
    }

    @Test
    public void testToString_NoRows() {
        DataFrameInlinePrintWorker w = new DataFrameInlinePrintWorker(new StringBuilder(), 5, 10);
        assertEquals("a:,b:", w.print(DataFrame.newFrame("a", "b").empty()).toString());
    }

    @Test
    public void testToString() {
        DataFrameInlinePrintWorker w = new DataFrameInlinePrintWorker(new StringBuilder(), 5, 10);

        assertEquals("{col1:one,column2:1},{col1:two,column2:2},{col1:three,column2:3},{col1:four,column2:4}",
                w.print(df).toString());
    }

    @Test
    public void testToString_TruncateRows() {
        DataFrameInlinePrintWorker w = new DataFrameInlinePrintWorker(new StringBuilder(), 2, 10);
        assertEquals("{col1:one,column2:1},...,{col1:four,column2:4}", w.print(df).toString());
    }

    @Test
    public void testToString_TruncateColumns() {
        DataFrameInlinePrintWorker w = new DataFrameInlinePrintWorker(new StringBuilder(), 5, 4);
        assertEquals("{col1:one,c..2:1},{col1:two,c..2:2},{col1:t..e,c..2:3},{col1:four,c..2:4}",
                w.print(df).toString());
    }
}
