package ru.energomera.zabbixbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.energomera.zabbixbot.model.weather.CurrentWeatherResponse;

import java.util.Collections;

@RestController
@Slf4j
public class WeatherRestController {
    private final RestTemplate restTemplate;
    private final int nevinnomysskId = 522377;
//    @Value("${weather.api.token}")
    private String weatherApiToken = "2c68ec3108883221a728b4075d68b407";

    private final String url = "https://api.openweathermap.org/data/2.5/weather?id={id}&" +
            "appid={appid}&units=metric&lang=ru";

    public WeatherRestController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public CurrentWeatherResponse createGetToWeatherApi() {

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        HttpEntity request = new HttpEntity(headers);

        // make an HTTP GET request with headers
        ResponseEntity<CurrentWeatherResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                CurrentWeatherResponse.class,
                nevinnomysskId,
                weatherApiToken
        );
        System.out.println(response.getBody());
        System.out.println(weatherApiToken);

// check response
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            System.out.println(response.getBody());
            log.info("request for weather api {}", HttpStatus.OK);
            return response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
            log.error("Failed to complete the request to weather api for current weather");
            return null;
        }

    }
}
