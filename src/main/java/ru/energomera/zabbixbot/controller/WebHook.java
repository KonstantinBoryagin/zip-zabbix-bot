package ru.energomera.zabbixbot.controller;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.energomera.zabbixbot.bot.ZabbixTelegramBot;
import ru.energomera.zabbixbot.service.MessageFromWebHookHandler;
import ru.energomera.zabbixbot.zabbixapi.dto.ZabbixWebHook;

@RestController
@RequestMapping("/api/public/zabbix")
public class WebHook {

    @Value("${bot.adminGroupChatId}")
    private String adminGroupChatId;

    @Value("${bot.group25_chatId}")
    private String group25ChatId;

    @Value("${bot.group7_chatId}")
    private String group7ChatId;

    @Value("${bot.group5_chatId}")
    private String group5ChatId;

    private ZabbixTelegramBot zabbixTelegramBot;
    private MessageFromWebHookHandler webHookHandler;

    @Autowired
    public WebHook(ZabbixTelegramBot zabbixTelegramBot, MessageFromWebHookHandler webHookHandler) {
        this.zabbixTelegramBot = zabbixTelegramBot;
        this.webHookHandler = webHookHandler;
    }

    @PostMapping(value = "/webhook")
    public ResponseEntity<?> webhook(@RequestBody String json) {
        Gson gson = new Gson();
        ZabbixWebHook zabbixWebHook = null;
        try {
            zabbixWebHook = gson.fromJson(json, ZabbixWebHook.class);

            String subj = zabbixWebHook.getSubj();
            System.out.println("input from zabbix hook-------");
            System.out.println(subj);
            System.out.println("input from zabbix hook-------");
            String line = zabbixWebHook.getMessage();
//            String[] split = line.split(System.getProperty("line.separator"));
//            for (int i = 0; i < split.length; i++) {
//                System.out.println(split[i] + "   " + (i + 1) + " line");
//                System.out.println("------------------");
//            }
            System.out.println(line);
            System.out.println("input from zabbix hook-------");
            String messageChatId = zabbixWebHook.getChat_id();
            System.out.println("messageChatId --->  " + messageChatId);

            //проверяем что это нужное сообщение
            if (messageChatId.equals(adminGroupChatId)) {

                webHookHandler.processMessageForAdminGroup(zabbixWebHook);

            } else if (messageChatId.equals(group25ChatId)
                    || messageChatId.equals(group7ChatId)
                    || messageChatId.equals(group5ChatId)) {
                webHookHandler.processMessageForDepartmentNotifications(zabbixWebHook);
            } else {
                //log
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


        } catch (JsonSyntaxException e) {
            System.out.println("wrong serialible input JSON");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        System.out.println("\n response sending \n"); //temp

        return new ResponseEntity<>(headers, HttpStatus.OK);

    }


}
