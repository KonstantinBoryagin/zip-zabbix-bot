package ru.energomera.zabbixbot.service;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResult;
import ru.energomera.zabbixbot.zabbixapi.dto.PingResult;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartService {
    public File createPingPicture(PingResult[] pingResults) throws IOException {
        DefaultCategoryDataset dataset = createPingDataset(pingResults);
        JFreeChart chart = ChartFactory.createLineChart("Test",
                "время",
                "ответ",
                dataset);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);


        String pathToImage = "src/main/resources/picture.png";
        File file = new File(pathToImage);
        ChartUtils.saveChartAsPNG(file, chart, 1000, 800);
        return file;
    }

    public File createHistoryPicture(HistoryResult[] historyResults) throws IOException {
        DefaultCategoryDataset dataset = createHistoryDataset(historyResults);
        JFreeChart chart = ChartFactory.createLineChart("Internet Proxy ЗИП: ICMP response time",
                "время",
                "ответ",
                dataset);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);


        String pathToImage = "src/main/resources/picture_history.png";
        File file = new File(pathToImage);
        ChartUtils.saveChartAsPNG(file, chart, 800, 600);
        return file;
    }

    private DefaultCategoryDataset createPingDataset(PingResult[] pingResults) {

        String series1 = "Internet Proxy ЗИП: ICMP response time";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        for (int i = 0; i < pingResults.length; i++) {
            long clock = pingResults[i].getClock();
            double value_max = pingResults[i].getValue_max();

            Date date = new Date(clock * 1000);
//            Date date = Date.from(Instant.ofEpochMilli(clock));
            System.out.println(date);
            DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
            String hour = dateFormatter.format(date);
            System.out.println(hour);
            dataset.addValue(value_max, series1, hour);
        }


        return dataset;
    }

    private DefaultCategoryDataset createHistoryDataset(HistoryResult[] historyResults) {

        String series1 = "Internet Proxy ЗИП: ICMP response time";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        for (int i = 0; i < historyResults.length; i++) {
            long clock = historyResults[i].getClock();
            double value_max = historyResults[i].getValue();

            Date date = new Date(clock * 1000);
//            Date date = Date.from(Instant.ofEpochMilli(clock));
            System.out.println(date);
            DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
            String hour = dateFormatter.format(date);
            System.out.println(hour);
            dataset.addValue(value_max, series1, hour);
        }


        return dataset;
    }


}