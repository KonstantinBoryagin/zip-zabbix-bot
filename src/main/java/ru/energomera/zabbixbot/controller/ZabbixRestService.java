package ru.energomera.zabbixbot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.energomera.zabbixbot.zabbixapi.dto.RequestToZabbixHistory;
import ru.energomera.zabbixbot.zabbixapi.dto.ResponseFromZabbixHistory;

import java.util.Collections;

@RestController
public class ZabbixRestService {

    private final RestTemplate restTemplate;

    @Value("${bot.url}")
    private String url;

    public ZabbixRestService(RestTemplateBuilder restTemplateBuilder) {
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
        ResponseEntity<ResponseFromZabbixHistory> response = this.restTemplate.postForEntity(url, entity, ResponseFromZabbixHistory.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(HttpStatus.OK);
            return response.getBody();
        } else {
            System.out.println("null from createPostWithHistoryObject");
            return null;
        }
    }
}
