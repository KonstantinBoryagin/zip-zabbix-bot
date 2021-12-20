package ru.energomera.zabbixbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HerokuMonitoring {

    @GetMapping(value = "/monitoring")
    public String answerForMonitoring() {

        return "I'm alive";
    }
}
