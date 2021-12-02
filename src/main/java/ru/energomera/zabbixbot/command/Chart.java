package ru.energomera.zabbixbot.command;

public interface Chart {

    void sendChart(String chatId, String subject, String message);

}
