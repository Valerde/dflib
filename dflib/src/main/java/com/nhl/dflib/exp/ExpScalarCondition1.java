package com.nhl.dflib.exp;

import com.nhl.dflib.BooleanSeries;
import com.nhl.dflib.Condition;
import com.nhl.dflib.DataFrame;
import com.nhl.dflib.Series;

/**
 * A unary condition with a single scalar argument.
 *
 * @since 1.0.0-M19
 */
public abstract class ExpScalarCondition1<T> implements Condition {

    protected final T value;

    public ExpScalarCondition1(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return toQL();
    }

    @Override
    public String toQL() {
        boolean quotes = value != null && !(value instanceof Number);
        String unquoted = String.valueOf(value);
        return quotes ? "'" + unquoted + "'" : unquoted;
    }

    @Override
    public String toQL(DataFrame df) {
        return toQL();
    }

    @Override
    public BooleanSeries eval(DataFrame df) {
        return doEval(df.height());
    }

    @Override
    public BooleanSeries eval(Series<?> s) {
        return doEval(s.size());
    }

    protected abstract BooleanSeries doEval(int height);
}
