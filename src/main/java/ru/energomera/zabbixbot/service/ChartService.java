package ru.energomera.zabbixbot.service;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.energomera.zabbixbot.zabbixapi.dto.Result;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class ChartService {
    public File createPicture(Result[] results) throws IOException {
        DefaultCategoryDataset dataset = createDataset(results);
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


    private DefaultCategoryDataset createDataset(Result[] results) {

        String series1 = "Internet Proxy ЗИП: ICMP response time";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        for (int i = 0; i < results.length; i++) {
            long clock = results[i].getClock();
            double value_max = results[i].getValue_max();

//            Date date = new Date(clock * 1000);
            Date date = Date.from(Instant.ofEpochMilli(clock));
            DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
            String hour = dateFormatter.format(date);
            System.out.println(hour);
            dataset.addValue(value_max, series1, hour);
        }


        return dataset;
    }

}