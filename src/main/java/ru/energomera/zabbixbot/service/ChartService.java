package ru.energomera.zabbixbot.service;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import ru.energomera.zabbixbot.model.zabbix.HistoryResponseResult;

import java.io.IOException;

public interface ChartService {

    byte[] createLineChartPicture(DefaultCategoryDataset dataset, String chartName) throws IOException;

    byte[] createAreaChartPicture(DefaultCategoryDataset dataset, String chartName);

    byte[] createPieChart(DefaultPieDataset dataset, String chartName) throws IOException;

    DefaultCategoryDataset createIcmpPingDataset(DefaultCategoryDataset dataset,
                                                 HistoryResponseResult[] historyResponseResults, String seriesName);

    DefaultPieDataset<String> createDatasetForPieChart(HistoryResponseResult[] historyResponseResults, String seriesName);
}
