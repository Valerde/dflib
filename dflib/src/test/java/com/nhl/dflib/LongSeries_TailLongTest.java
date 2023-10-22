package com.nhl.dflib;

import com.nhl.dflib.unit.LongSeriesAsserts;
import org.junit.jupiter.api.Test;

public class LongSeries_TailLongTest {

    @Test
    public void test0() {
        LongSeries s = Series.ofLong(3, 4, 2).tailLong(2);
        new LongSeriesAsserts(s).expectData(4L, 2L);
    }

    @Test
    public void test1() {
        LongSeries s = Series.ofLong(3, 4, 2).tailLong(1);
        new LongSeriesAsserts(s).expectData(2L);
    }

    @Test
    public void test_Zero() {
        LongSeries s = Series.ofLong(3, 4, 2).tailLong(0);
        new LongSeriesAsserts(s).expectData();
    }

    @Test
    public void test_OutOfBounds() {
        LongSeries s = Series.ofLong(3, 4, 2).tailLong(4);
        new LongSeriesAsserts(s).expectData(3L, 4L, 2L);
    }

    @Test
    public void test_Negative() {
        LongSeries s = Series.ofLong(3, 4, 2).tailLong(-2);
        new LongSeriesAsserts(s).expectData(3L);
    }
}
