package ru.energomera.zabbixbot.zabbixapi.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ZabbixWebHook {
//    'chat_id', 'parse_mode', 'disable_web_page_preview', 'disable_notification', 'text'

    private String chat_id;
    private String parse_mode;
    private boolean disable_web_page_preview;
    private boolean disable_notification;
    private String subj;
    private String message;

}
