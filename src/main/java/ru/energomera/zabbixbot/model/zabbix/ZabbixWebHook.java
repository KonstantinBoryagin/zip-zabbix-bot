package ru.energomera.zabbixbot.model.zabbix;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * DTO для сообщений полученных при рассылке от Zabbix
 */
@Data
@ToString
//@JsonIgnoreProperties(ignoreUnknown = true)
public class ZabbixWebHook {

    @SerializedName("chat_id")
    private String chatId;

//    @SerializedName("parse_mode")
//    private String parseMode;
//
//    @SerializedName("disable_web_page_preview")
//    private boolean disableWebPagePreview;
//
//    @SerializedName("disable_notification")
//    private boolean disableNotification;

    @SerializedName("subj")
    private String subject;

    private String message;
}
