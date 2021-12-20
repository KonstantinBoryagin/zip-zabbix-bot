package ru.energomera.zabbixbot.service;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;
import ru.energomera.zabbixbot.model.zabbix.HistoryResponseResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Сервис для создания графиков
 */
@Service
@Slf4j
public class ChartService {

    private final String axisXNameForPingChart = "время";
    private final String axisYNameForPingChart = "ответ(мс)";

    public byte[] createLineChartPicture(DefaultCategoryDataset dataset, String chartName) throws IOException{

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

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesStroke(1, new BasicStroke(3.5f));

        BufferedImage objBufferedImage = chart.createBufferedImage(600, 400);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(objBufferedImage, "png", byteArrayOutputStream);
            log.info("createLineChartPicture worked well");
        } catch (IOException e) {
            log.error("could not create byteArrayOutputStream: ", e);
        }

        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return byteArray;
    }

    public byte[] createAreaChartPicture(DefaultCategoryDataset dataset, String chartName) {

        JFreeChart chart = ChartFactory.createAreaChart(chartName,
                axisXNameForPingChart,
                axisYNameForPingChart,
                dataset);

        //делает горизонтальными подписи делений на оси Х
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);

        //устанавливаем цвета
//        plot.setBackgroundPaint(new Color(51, 51, 51));  //график
//        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//        renderer.setSeriesStroke(1, new BasicStroke(3.5f));

        BufferedImage objBufferedImage = chart.createBufferedImage(600, 400);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(objBufferedImage, "png", byteArrayOutputStream);
            log.info("createAreaChartPicture worked well");
        } catch (IOException e) {
            log.error("could not create byteArrayOutputStream: ", e);
        }

        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return byteArray;
    }

    public byte[] createPieChart(DefaultPieDataset dataset, String chartName) throws IOException {

        JFreeChart chart = ChartFactory.createPieChart(chartName,
                dataset,
                true,
                true,
                false);

        BufferedImage objBufferedImage = chart.createBufferedImage(600, 400);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(objBufferedImage, "png", byteArrayOutputStream);
            log.info("createPieChart worked well");
        } catch (IOException e) {
            log.error("could not create byteArrayOutputStream: ", e);
        }

        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return byteArray;
    }

    public DefaultCategoryDataset createIcmpPingDataset(DefaultCategoryDataset dataset,
                                                        HistoryResponseResult[] historyResponseResults, String seriesName) {

        String series = seriesName;

        for (int i = (historyResponseResults.length - 1); i >= 0; i--) {
            long clock = historyResponseResults[i].getClock();
            double value = historyResponseResults[i].getValue() * 1000; //превращаем в мили сек.

            String formatTime = Instant.ofEpochSecond(clock)
                    .atZone(ZoneId.of("Europe/Moscow"))
                    .format(DateTimeFormatter.ofPattern("HH:mm"));

            dataset.addValue(value, series, formatTime);
        }

        return dataset;
    }

    public DefaultPieDataset<String> createDatasetForPieChart(HistoryResponseResult[] historyResponseResults, String seriesName) {
        double value = historyResponseResults[0].getValue();
        double free = 100 - value;

        String formatSeries = String.format("%s - %.2f%%", seriesName, value);
        String formatSeries2 = String.format("Free space - %.2f%%", free);

        DefaultPieDataset<String> pieDataSet = new DefaultPieDataset<>();
        pieDataSet.setValue(formatSeries, value);
        pieDataSet.setValue(formatSeries2, free);

        return pieDataSet;
    }
}