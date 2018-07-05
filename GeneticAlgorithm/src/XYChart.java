import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.Range;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;

class XYChart extends ApplicationFrame {
    private final YIntervalSeriesCollection dataSet;
    private final YIntervalSeries theBestChromosomeSeries;
    private final YIntervalSeries averageValueInPopulationSeries;
    private final YIntervalSeries theWorstChromosomeSeries;
    private String chartTitle;

    XYChart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        this.chartTitle = chartTitle;
        dataSet = new YIntervalSeriesCollection();
        theBestChromosomeSeries = new YIntervalSeries("Najlepszy osobnik");
        averageValueInPopulationSeries = new YIntervalSeries("Średnia wartość w populacji");
        theWorstChromosomeSeries = new YIntervalSeries("Najgorszy osobnik");
    }

    YIntervalSeries getTheBestChromosomeSeries() {
        return theBestChromosomeSeries;
    }

    YIntervalSeries getAverageValueInPopulationSeries() {
        return averageValueInPopulationSeries;
    }

    YIntervalSeries getTheWorstChromosomeSeries() {
        return theWorstChromosomeSeries;
    }

    void generateChart() {
        dataSet.addSeries(theBestChromosomeSeries);
        dataSet.addSeries(averageValueInPopulationSeries);
        dataSet.addSeries(theWorstChromosomeSeries);
        JFreeChart xyChart = ChartFactory.createXYLineChart(chartTitle, "Populacja", "Wartość przystosowania", dataSet, PlotOrientation.VERTICAL, true, true, false);
        xyChart.setTitle(new TextTitle(chartTitle,
                new Font("Arial", java.awt.Font.BOLD, 12)));
        ChartPanel chartPanel = new ChartPanel(xyChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1152, 648));
        final XYPlot plot = xyChart.getXYPlot();
        Range range = new Range(plot.getDataRange(plot.getRangeAxis()).getLowerBound() * 0.985, plot.getDataRange(plot.getRangeAxis()).getUpperBound() * 1.015);
        plot.getRangeAxis().setRange(range);
        plot.getDomainAxis().setRange(new Range(1, Parameters.NUMBER_OF_POPULATIONS));
        DeviationRenderer renderer = new DeviationRenderer(true, false);
        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setSeriesFillPaint(0, Color.GREEN);
        renderer.setSeriesPaint(1, Color.YELLOW);
        renderer.setSeriesFillPaint(1, Color.YELLOW);
        renderer.setSeriesPaint(2, Color.RED);
        renderer.setSeriesFillPaint(2, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(4));
        renderer.setSeriesStroke(1, new BasicStroke(4));
        renderer.setSeriesStroke(2, new BasicStroke(4));
        renderer.setAlpha(0.2f);
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
}