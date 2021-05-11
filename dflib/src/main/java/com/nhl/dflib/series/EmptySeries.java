package com.nhl.dflib.series;

import com.nhl.dflib.BooleanSeries;
import com.nhl.dflib.Series;
import com.nhl.dflib.Condition;
import com.nhl.dflib.ValuePredicate;

import java.util.Comparator;

public class EmptySeries<T> extends ObjectSeries<T> {

    public EmptySeries() {
        this(Object.class);
    }

    public EmptySeries(Class<?> nominalType) {
        super(nominalType);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T get(int index) {
        throw new ArrayIndexOutOfBoundsException(index);
    }

    @Override
    public void copyTo(Object[] to, int fromOffset, int toOffset, int len) {
        if (fromOffset + len > 0) {
            throw new ArrayIndexOutOfBoundsException(fromOffset + len);
        }
    }

    @Override
    public Series<T> select(Condition condition) {
        return this;
    }

    @Override
    public Series<T> select(ValuePredicate<T> p) {
        return this;
    }

    @Override
    public Series<T> select(BooleanSeries positions) {
        return this;
    }

    @Override
    public Series<T> sort(Comparator<? super T> comparator) {
        return this;
    }

    @Override
    public Series<T> materialize() {
        return this;
    }

    @Override
    public Series<T> fillNulls(T value) {
        return this;
    }

    @Override
    public Series<T> fillNullsFromSeries(Series<? extends T> values) {
        return this;
    }

    @Override
    public Series<T> fillNullsBackwards() {
        return this;
    }

    @Override
    public Series<T> fillNullsForward() {
        return this;
    }
}
