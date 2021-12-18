package ru.energomera.zabbixbot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HerokuMonitoring {

    @GetMapping(value = "/monitoring")
    public ResponseEntity<?> answerForMonitoring() {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
