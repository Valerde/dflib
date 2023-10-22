package com.nhl.dflib;

import com.nhl.dflib.unit.DoubleSeriesAsserts;
import org.junit.jupiter.api.Test;

public class DoubleSeries_TailDoubleTest {

    @Test
    public void test() {
        DoubleSeries s = Series.ofDouble(3, 4, 2).tailDouble(2);
        new DoubleSeriesAsserts(s).expectData(4, 2);
    }

    @Test
    public void test_Zero() {
        DoubleSeries s = Series.ofDouble(3, 4, 2).tailDouble(0);
        new DoubleSeriesAsserts(s).expectData();
    }

    @Test
    public void test_OutOfBounds() {
        DoubleSeries s = Series.ofDouble(3, 4, 2).tailDouble(4);
        new DoubleSeriesAsserts(s).expectData(3, 4, 2);
    }

    @Test
    public void test_Negative() {
        DoubleSeries s = Series.ofDouble(3, 4, 2).tailDouble(-2);
        new DoubleSeriesAsserts(s).expectData(3);
    }
}
