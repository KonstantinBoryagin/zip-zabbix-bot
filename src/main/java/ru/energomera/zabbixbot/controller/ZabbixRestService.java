package ru.energomera.zabbixbot.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.energomera.zabbixbot.zabbixapi.dto.*;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryRequest;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryResponse;
import ru.energomera.zabbixbot.zabbixapi.dto.ping.PingRequest;
import ru.energomera.zabbixbot.zabbixapi.dto.ping.PingResponse;

import java.util.Collections;

@RestController
public class ZabbixRestService {

    private final RestTemplate restTemplate;

    public ZabbixRestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public UserResponse createPostWithObject(int id) {
        String url = "http://10.6.4.7:81/zabbix/api_jsonrpc.php";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create a post object
        User user = new User();
        user.init(id);

        // build the request
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        // send POST request
        return restTemplate.postForObject(url, entity, UserResponse.class);
    }

    public PingResponse createPostWithObjects() {
        String url = "http://10.6.4.7:81/zabbix/api_jsonrpc.php";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create a post object
        PingRequest pingRequest = new PingRequest();

        // build the request
        HttpEntity<PingRequest> entity = new HttpEntity<>(pingRequest, headers);

        // send POST request
        ResponseEntity<PingResponse> response = this.restTemplate.postForEntity(url, entity, PingResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(HttpStatus.OK);
            return response.getBody();
        } else {
            System.out.println("null from createPostWithObjects");
            return null;
        }
    }

    public HistoryResponse createPostWithHistoryObject(Request request) {
        String url = "http://10.6.4.7:81/zabbix/api_jsonrpc.php";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create a post object
//        HistoryRequest historyRequest = new HistoryRequest();

        // build the request
        HttpEntity<Request> entity = new HttpEntity<>(request, headers);

        // send POST request
        ResponseEntity<HistoryResponse> response = this.restTemplate.postForEntity(url, entity, HistoryResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(HttpStatus.OK);
            return response.getBody();
        } else {
            System.out.println("null from createPostWithHistoryObject");
            return null;
        }
    }
}
