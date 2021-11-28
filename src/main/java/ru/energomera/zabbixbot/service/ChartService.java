package ru.energomera.zabbixbot.service;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;

public class ChartService {
    public File createPicture() throws IOException {
        DefaultCategoryDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createLineChart("Test",
                "ответ",
                "время",
                dataset);

        String pathToImage = "src/main/resources/picture.png";
        File file = new File(pathToImage);
        ChartUtils.saveChartAsPNG(file, chart, 500, 200);
        return file;
    }


    private DefaultCategoryDataset createDataset() {

        String series1 = "Internet Proxy ЗИП: ICMP response time";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(200, series1, "2016-12-19");
        dataset.addValue(150, series1, "2016-12-20");
        dataset.addValue(100, series1, "2016-12-21");
        dataset.addValue(210, series1, "2016-12-22");
        dataset.addValue(240, series1, "2016-12-23");
        dataset.addValue(195, series1, "2016-12-24");
        dataset.addValue(245, series1, "2016-12-25");


        return dataset;
    }
}