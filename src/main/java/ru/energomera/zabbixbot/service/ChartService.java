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
//    public File createPingPicture(PingResult[] pingResults) throws IOException {
//        DefaultCategoryDataset dataset = createPingDataset(pingResults);
//        JFreeChart chart = ChartFactory.createLineChart("Test",
//                "время",
//                "ответ",
//                dataset);
//
//        CategoryPlot plot = (CategoryPlot) chart.getPlot();
//        CategoryAxis domainAxis = plot.getDomainAxis();
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
//
//
//        String pathToImage = "src/main/resources/picture.png";
//        File file = new File(pathToImage);
//        ChartUtils.saveChartAsPNG(file, chart, 500, 300);
//        return file;
//    }

    public File createHistoryChartPicture(HistoryResponseResult[] historyResponseResults, String chartName,
                                          String axisXName, String axisYName, String seriesName) throws IOException {
        DefaultCategoryDataset dataset = createHistoryDataset(historyResponseResults, seriesName);
        JFreeChart chart = ChartFactory.createLineChart(chartName,
                axisXName,
                axisYName,
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

//    private DefaultCategoryDataset createPingDataset(PingResult[] pingResults) {
//
//        String series1 = "Internet Proxy ЗИП: ICMP response time";
//
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//
//        for (int i = 0; i < pingResults.length; i++) {
//            long clock = pingResults[i].getClock();
//            double value_max = pingResults[i].getValue_max();
//
//            Date date = new Date(clock * 1000);
////            Date date = Date.from(Instant.ofEpochMilli(clock));
//            System.out.println(date);
//            DateFormat dateFormatter = new SimpleDateFormat("HH:mm");
//            String hour = dateFormatter.format(date);
//            System.out.println(hour);
//            dataset.addValue(value_max, series1, hour);
//        }
//
//
//        return dataset;
//    }

    private DefaultCategoryDataset createHistoryDataset(HistoryResponseResult[] historyResponseResults, String seriesName) {

        String series1 = seriesName;

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        for (int i = (historyResponseResults.length - 1); i >= 0 ; i--) {
            long clock = historyResponseResults[i].getClock();
            double value = historyResponseResults[i].getValue();

            Date date = new Date(clock * 1000);
//            Date date = Date.from(Instant.ofEpochMilli(clock));
            System.out.println(date);
            DateFormat dateFormatter = new SimpleDateFormat("HH:mm");
            String hour = dateFormatter.format(date);
            System.out.println(hour);
            dataset.addValue(value, series1, hour);
        }


        return dataset;
    }


}