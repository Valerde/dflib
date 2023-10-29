package com.nhl.dflib.series;

import com.nhl.dflib.IntSeries;
import com.nhl.dflib.LongSeries;
import com.nhl.dflib.agg.PrimitiveSeriesAvg;
import com.nhl.dflib.agg.PrimitiveSeriesMedian;
import com.nhl.dflib.agg.PrimitiveSeriesMinMax;
import com.nhl.dflib.agg.PrimitiveSeriesSum;
import com.nhl.dflib.range.Range;

/**
 * @since 0.6
 */
// TODO: should we split that into a fast IntArraySeries that matches exactly data[], and a slower IntArrayRangeSeries,
//  that has offset and size? Need to measure the performance gain of not having to calculate offset
public class IntArraySeries extends IntBaseSeries {

    private final int[] data;
    private final int offset;
    private final int size;

    public IntArraySeries(int... data) {
        this(data, 0, data.length);
    }

    public IntArraySeries(int[] data, int offset, int size) {

        Range.checkRange(offset, size, data.length);

        this.data = data;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public int size() {
        return size;
    }

    // oddly enough, the same optimization of the "eq" method with IntArraySeries cast only shows about 3% speed
    // improvement, so we are ignoring it. While reimplementing "add" gives 33% improvement vs super.

    @Override
    public IntSeries add(IntSeries s) {

        if (!(s instanceof IntArraySeries)) {
            return super.add(s);
        }

        int len = size();
        if (len != s.size()) {
            throw new IllegalArgumentException("Another Series size " + s.size() + " is not the same as this size " + len);
        }

        IntArraySeries as = (IntArraySeries) s;

        // storing ivars in the local vars for performance
        int[] l = this.data;
        int[] r = as.data;

        int lo = this.offset;
        int ro = as.offset;

        int[] data = new int[len];

        if (lo > 0 || ro > 0) {
            for (int i = 0; i < len; i++) {
                data[i] = l[lo + i] + r[ro + i];
            }
        } else {
            // not having to calculate offset (the most common case) results in a performance boost
            // (due to HotSpot vectorization?)
            for (int i = 0; i < len; i++) {
                data[i] = l[i] + r[i];
            }
        }

        return new IntArraySeries(data);
    }

    @Override
    public IntSeries sub(IntSeries s) {
        if (!(s instanceof IntArraySeries)) {
            return super.sub(s);
        }

        int len = size();
        if (len != s.size()) {
            throw new IllegalArgumentException("Another Series size " + s.size() + " is not the same as this size " + len);
        }

        IntArraySeries as = (IntArraySeries) s;

        // storing ivars in the local vars for performance
        int[] l = this.data;
        int[] r = as.data;

        int lo = this.offset;
        int ro = as.offset;

        int[] data = new int[len];

        if (lo > 0 || ro > 0) {
            for (int i = 0; i < len; i++) {
                data[i] = l[lo + i] - r[ro + i];
            }
        } else {
            // not having to calculate offset (the most common case) results in a performance boost
            // (due to HotSpot vectorization?)
            for (int i = 0; i < len; i++) {
                data[i] = l[i] - r[i];
            }
        }

        return new IntArraySeries(data);
    }

    @Override
    public IntSeries mul(IntSeries s) {
        if (!(s instanceof IntArraySeries)) {
            return super.mul(s);
        }

        int len = size();
        if (len != s.size()) {
            throw new IllegalArgumentException("Another Series size " + s.size() + " is not the same as this size " + len);
        }

        IntArraySeries as = (IntArraySeries) s;

        // storing ivars in the local vars for performance
        int[] l = this.data;
        int[] r = as.data;

        int lo = this.offset;
        int ro = as.offset;

        int[] data = new int[len];

        if (lo > 0 || ro > 0) {
            for (int i = 0; i < len; i++) {
                data[i] = l[lo + i] * r[ro + i];
            }
        } else {
            // not having to calculate offset (the most common case) results in a performance boost
            // (due to HotSpot vectorization?)
            for (int i = 0; i < len; i++) {
                data[i] = l[i] * r[i];
            }
        }

        return new IntArraySeries(data);
    }

    @Override
    public IntSeries div(IntSeries s) {
        if (!(s instanceof IntArraySeries)) {
            return super.div(s);
        }

        int len = size();
        if (len != s.size()) {
            throw new IllegalArgumentException("Another Series size " + s.size() + " is not the same as this size " + len);
        }

        IntArraySeries as = (IntArraySeries) s;

        // storing ivars in the local vars for performance
        int[] l = this.data;
        int[] r = as.data;

        int lo = this.offset;
        int ro = as.offset;

        int[] data = new int[len];

        if (lo > 0 || ro > 0) {
            for (int i = 0; i < len; i++) {
                data[i] = l[lo + i] / r[ro + i];
            }
        } else {
            // not having to calculate offset (the most common case) results in a performance boost
            // (due to HotSpot vectorization?)
            for (int i = 0; i < len; i++) {
                data[i] = l[i] / r[i];
            }
        }

        return new IntArraySeries(data);
    }

    @Override
    public IntSeries mod(IntSeries s) {
        if (!(s instanceof IntArraySeries)) {
            return super.mod(s);
        }

        int len = size();
        if (len != s.size()) {
            throw new IllegalArgumentException("Another Series size " + s.size() + " is not the same as this size " + len);
        }

        IntArraySeries as = (IntArraySeries) s;

        // storing ivars in the local vars for performance
        int[] l = this.data;
        int[] r = as.data;

        int lo = this.offset;
        int ro = as.offset;

        int[] data = new int[len];

        if (lo > 0 || ro > 0) {
            for (int i = 0; i < len; i++) {
                data[i] = l[lo + i] % r[ro + i];
            }
        } else {
            // not having to calculate offset (the most common case) results in a performance boost
            // (due to HotSpot vectorization?)
            for (int i = 0; i < len; i++) {
                data[i] = l[i] % r[i];
            }
        }

        return new IntArraySeries(data);
    }

    @Override
    public int getInt(int index) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }

        return data[offset + index];
    }

    @Override
    public void copyToInt(int[] to, int fromOffset, int toOffset, int len) {
        if (fromOffset + len > size) {
            throw new ArrayIndexOutOfBoundsException(fromOffset + len);
        }

        System.arraycopy(data, offset + fromOffset, to, toOffset, len);
    }

    @Override
    public IntSeries head(int len) {

        if (Math.abs(len) >= size) {
            return this;
        }

        return len < 0 ? tail(size + len) : new IntArraySeries(data, offset, len);
    }

    @Override
    public IntSeries tail(int len) {

        if (Math.abs(len) >= size) {
            return this;
        }

        return len < 0 ? head(size + len) : new IntArraySeries(data, offset + size - len, len);
    }

    @Override
    public IntSeries rangeOpenClosedInt(int fromInclusive, int toExclusive) {
        return fromInclusive == 0 && toExclusive == size()
                ? this
                : new IntArraySeries(data, offset + fromInclusive, toExclusive - fromInclusive);
    }

    @Override
    public IntSeries materialize() {
        if (offset > 0 || size + offset < this.data.length) {
            int[] data = new int[size];
            copyToInt(data, 0, 0, size);
            return new IntArraySeries(data);
        }

        return this;
    }

    @Override
    public int max() {
        return PrimitiveSeriesMinMax.maxOfArray(data, offset, size);
    }

    @Override
    public int min() {
        return PrimitiveSeriesMinMax.minOfArray(data, offset, size);
    }

    @Override
    public long sum() {
        return PrimitiveSeriesSum.sumOfArray(data, offset, size);
    }

    @Override
    public double avg() {
        return PrimitiveSeriesAvg.avgOfArray(data, offset, size);
    }

    @Override
    public double median() {
        return PrimitiveSeriesMedian.medianOfArray(data, offset, size);
    }

    @Override
    public LongSeries cumSum() {
        long[] cumSum = PrimitiveSeriesSum.cumSumOfArray(data, offset, size);
        return new LongArraySeries(cumSum);
    }
}
