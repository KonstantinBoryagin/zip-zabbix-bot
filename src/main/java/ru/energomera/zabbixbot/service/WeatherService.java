package ru.energomera.zabbixbot.service;

public interface WeatherService {

    String formCurrentWeatherMessage();

    String[] formatWeeklyWeatherMessage();

    String formatDailyWeatherMessage();
}
