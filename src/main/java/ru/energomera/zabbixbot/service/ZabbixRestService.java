package ru.energomera.zabbixbot.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.energomera.zabbixbot.zabbixapi.dto.PingRequest;
import ru.energomera.zabbixbot.zabbixapi.dto.PingResponse;
import ru.energomera.zabbixbot.zabbixapi.dto.User;
import ru.energomera.zabbixbot.zabbixapi.dto.UserResponse;

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

    public PingResponse[] createPostWithObjects() {
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
        return restTemplate.postForObject(url, entity, PingResponse[].class);
    }
}
