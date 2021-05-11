package com.nhl.dflib.aggregate;

import com.nhl.dflib.*;
import com.nhl.dflib.accumulator.Accumulator;
import com.nhl.dflib.accumulator.ObjectAccumulator;

/**
 * @since 0.6
 */
public class SeriesAggregation {

    public static DataFrame aggAsDataFrame(Series<?> series, SeriesExp<?>... aggregators) {

        int aggW = aggregators.length;
        Series<?>[] aggColumns = new Series[aggW];
        String[] aggLabels = new String[aggW];

        for (int i = 0; i < aggW; i++) {
            aggColumns[i] = aggregators[i].eval(series);
            aggLabels[i] = aggregators[i].getName();
        }

        return DataFrame.newFrame(Index.forLabelsDeduplicate(aggLabels)).columns(aggColumns);
    }

    public static <T, R> Series<R> aggGroupBy(SeriesGroupBy<T> groupBy, SeriesExp<R> aggregator) {

        // TODO: let Aggregator generate and fill SeriesBuilder, as it can use primitive collections
        ObjectAccumulator<R> columnBuilder = new ObjectAccumulator<>(groupBy.size());

        for (Object key : groupBy.getGroups()) {
            Series<T> group = groupBy.getGroup(key);
            columnBuilder.add(aggregator.eval(group).get(0));
        }

        return columnBuilder.toSeries();
    }

    public static <T> DataFrame aggGroupMultiple(SeriesGroupBy<T> groupBy, SeriesExp<?>... aggregators) {

        int aggW = aggregators.length;
        int aggH = groupBy.size();

        Series[] aggColumns = new Series[aggW];
        String[] aggLabels = new String[aggW];

        for (int i = 0; i < aggW; i++) {

            SeriesExp<?> agg = aggregators[i];

            // TODO: let Aggregator fill Accumulator, as it can use primitive collections
            Accumulator columnBuilder = new ObjectAccumulator(aggH);

            for (Object key : groupBy.getGroups()) {
                Series<T> group = groupBy.getGroup(key);
                columnBuilder.add(agg.eval(group).get(0));
            }

            aggColumns[i] = columnBuilder.toSeries();
            aggLabels[i] = agg.getName();
        }

        return DataFrame.newFrame(Index.forLabelsDeduplicate(aggLabels)).columns(aggColumns);
    }
}
