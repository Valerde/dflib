package org.dflib;

import org.dflib.row.RowProxy;
import org.dflib.row.RowBuilder;

/**
 * Maps source row value to target row values.
 */
@FunctionalInterface
public interface RowMapper {

    static RowMapper copy() {
        return (s, t) -> s.copy(t, 0);
    }

    void map(RowProxy from, RowBuilder to);

    default RowMapper and(RowMapper m) {
        return (s, t) -> {
            this.map(s, t);
            m.map(s, t);
        };
    }
}
