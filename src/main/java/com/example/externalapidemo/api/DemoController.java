package com.example.externalapidemo.api;

import com.example.externalapidemo.dto.NameData;
import com.example.externalapidemo.service.NameDataService;
import jakarta.websocket.server.PathParam;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
public class DemoController {

    NameDataService nameDataService;

    public DemoController(NameDataService nameDataService) {
        this.nameDataService = nameDataService;
    }

    private final int SLEEP_TIME = 1000*3;

    @GetMapping(value = "/random-string-slow")
    public String slowEndpoint() throws InterruptedException {
        Thread.sleep(SLEEP_TIME);
        return RandomStringUtils.randomAlphanumeric(10);
    }

    @GetMapping(value = "/name-info/{name}")
    public Mono<NameData> nameInfo(@PathVariable String name) throws InterruptedException {
        return nameDataService.findNameDataMono(name);
    }

}
