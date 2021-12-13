package ru.energomera.zabbixbot.zabbixapi.dto;

import lombok.Data;
import lombok.ToString;

/**
 * DTO для сообщений полученных при рассылке от Zabbix
 */
@Data
@ToString
public class ZabbixWebHook {

    private String chat_id;
    private String parse_mode;
    private boolean disable_web_page_preview;
    private boolean disable_notification;
    private String subj;
    private String message;

}
