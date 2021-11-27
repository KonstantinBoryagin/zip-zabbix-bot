package ru.energomera.zabbixbot.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.energomera.zabbixbot.zabbixapi.User;
import ru.energomera.zabbixbot.zabbixapi.UserResponse;

import java.util.Collections;

@RestController
public class ZabbixRestService {

    private final RestTemplate restTemplate;

    public ZabbixRestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public UserResponse createPostWithObject() {
        String url = "https://jsonplaceholder.typicode.com/posts";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create a post object
        User user = new User();

        // build the request
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        // send POST request
        return restTemplate.postForObject(url, entity, UserResponse.class);
    }
}
