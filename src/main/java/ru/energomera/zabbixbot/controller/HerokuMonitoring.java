package ru.energomera.zabbixbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Служит для ответа на пинг New Relic на heroku
 * (мониторинг и что бы dyno не останавливался)
 */
@RestController
public class HerokuMonitoring {

    @GetMapping(value = "/monitoring")
    public String answerForMonitoring() {

        return "I'm alive";
    }
}
