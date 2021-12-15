package ru.energomera.zabbixbot.controller;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.energomera.zabbixbot.service.MessageFromWebHookHandler;
import ru.energomera.zabbixbot.zabbixapi.dto.ZabbixWebHook;

@RestController
@RequestMapping("/api/public/zabbix")
@Slf4j
public class WebHook {

    @Value("${bot.adminGroupChatId}")
    private String adminGroupChatId;

    @Value("${bot.group25_chatId}")
    private String group25ChatId;

    @Value("${bot.group7_chatId}")
    private String group7ChatId;

    @Value("${bot.group5_chatId}")
    private String group5ChatId;

    private final MessageFromWebHookHandler webHookHandler;

    @Autowired
    public WebHook(MessageFromWebHookHandler webHookHandler) {
        this.webHookHandler = webHookHandler;
    }

    @PostMapping(value = "/webhook")
    public ResponseEntity<?> webhook(@RequestBody String json) {
        Gson gson = new Gson();
        ZabbixWebHook zabbixWebHook = null;
        try {
            zabbixWebHook = gson.fromJson(json, ZabbixWebHook.class);

            String messageChatId = zabbixWebHook.getChat_id();

            log.info("Received message from Zabbix with text: {} {};\nwith ChatId: {}",
                    zabbixWebHook.getSubj(), zabbixWebHook.getMessage(), zabbixWebHook.getChat_id());

            //проверяем что это нужное сообщение
            if (messageChatId.equals(adminGroupChatId)) {

                webHookHandler.processMessageForAdminGroup(zabbixWebHook);

            } else if (messageChatId.equals(group25ChatId)
                    || messageChatId.equals(group7ChatId)
                    || messageChatId.equals(group5ChatId)) {
                webHookHandler.processMessageForDepartmentNotifications(zabbixWebHook);
            } else {
                log.warn("Message had wrong chatId {}", messageChatId);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


        } catch (JsonSyntaxException e) {
            log.error("Can't serializable JSON from Zabbix", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        return new ResponseEntity<>(headers, HttpStatus.OK);

    }


}
