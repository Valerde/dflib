package com.nhl.dflib.series.builder;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 0.6
 */
public class UniqueIntAccumulator extends IntAccumulator {

    private Set<Integer> seen;

    public UniqueIntAccumulator() {
        this(10);
    }

    public UniqueIntAccumulator(int capacity) {
        super(capacity);
        this.seen = new HashSet<>(capacity);
    }

    @Override
    public void addInt(int value) {

        if (seen.add(value)) {
            super.addInt(value);
        }
    }

    @Override
    public void setInt(int pos, int value) {
        throw new UnsupportedOperationException("'set' operation is undefined for unique accumulator");
    }
}
