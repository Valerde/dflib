package org.dflib.echarts;

/**
 * Enumerates supported chart types.
 */
public enum ChartType {
    line, bar, scatter, pie, candlestick, boxplot,

    /**
     * @since 2.0.0
     */
    heatmap;

    /**
     * @deprecated in favor of {@link CoordinateSystemType#isCartesian()}, as many chart types can be plotted over
     * more than one coordinate system.
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    public boolean isCartesian() {
        switch (this) {
            case bar:
            case boxplot:
            case candlestick:
            case line:
            case scatter:
                return true;
            case pie:
                return false;
            default:
                throw new UnsupportedOperationException("Unexpected ChartType: " + this);
        }
    }

    /**
     * @since 2.0.0
     */
    public boolean supportsDataset() {
        switch (this) {
            // TODO: dataset / encode kinda works with heatmaps, but is very unreliable, so should encode data
            //  per Series for those
            case heatmap:
                return false;
            default:
                return true;
        }
    }
}
