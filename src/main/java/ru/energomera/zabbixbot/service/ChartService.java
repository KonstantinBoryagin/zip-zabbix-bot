package ru.energomera.zabbixbot.service;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResponseResult;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartService {

    private final String axisXNameForPingChart = "время";
    private final String axisYNameForPingChart = "ответ(мс)";

    public File createIcmpPingLineChartPicture(DefaultCategoryDataset dataset, String chartName) throws IOException {

        JFreeChart chart = ChartFactory.createLineChart(chartName,
                axisXNameForPingChart,
                axisYNameForPingChart,
                dataset);

        //делает горизонтальными подписи делений на оси Х
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);


        //устанавливаем цвета
        plot.setBackgroundPaint(new Color(51, 51, 51));  //график
//        chart.setBackgroundPaint(new Color(204, 204, 204)); //картинка вокруг

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesStroke(1, new BasicStroke(3.5f));

        String pathToImage = "src/main/resources/picture_history.png";
        File file = new File(pathToImage);
        ChartUtils.saveChartAsPNG(file, chart, 600, 400);
        return file;
    }

    public DefaultCategoryDataset createIcmpPingDataset(DefaultCategoryDataset dataset,
                                                         HistoryResponseResult[] historyResponseResults, String seriesName) {

        String series = seriesName;

        for (int i = (historyResponseResults.length - 1); i >= 0; i--) {
            long clock = historyResponseResults[i].getClock();
            double value = historyResponseResults[i].getValue() * 1000; //превращаем в мили сек.

            Date date = new Date(clock * 1000);
            System.out.println(date);
            DateFormat dateFormatter = new SimpleDateFormat("HH:mm");
            String hour = dateFormatter.format(date);
            System.out.println(hour);
            dataset.addValue(value, series, hour);
        }

        return dataset;
    }
}