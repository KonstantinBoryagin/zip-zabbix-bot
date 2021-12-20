package ru.energomera.zabbixbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.energomera.zabbixbot.model.zabbix.RequestToZabbixHistory;
import ru.energomera.zabbixbot.model.zabbix.ResponseFromZabbixHistory;

import java.util.Collections;

/**
 * Rest Controller для обращения к Zabbix Server API
 */
@RestController
@Slf4j
public class ZabbixRestController {

    private final RestTemplate restTemplate;

    private final String url = "http://10.6.4.7:81/zabbix/api_jsonrpc.php";

    public ZabbixRestController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseFromZabbixHistory createPostWithHistoryObject(RequestToZabbixHistory requestToZabbixHistory) {


        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create a post object
//        HistoryRequest historyRequest = new HistoryRequest();

        // build the request
        HttpEntity<RequestToZabbixHistory> entity = new HttpEntity<>(requestToZabbixHistory, headers);

        // send POST request
        ResponseEntity<ResponseFromZabbixHistory> response = this.restTemplate.postForEntity(url,
                entity, ResponseFromZabbixHistory.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("request for Zabbix server {}", HttpStatus.OK);
            return response.getBody();
        } else {
            log.error("Failed to complete the request to Zabbix server with {}", requestToZabbixHistory);
            return null;
        }
    }
}
