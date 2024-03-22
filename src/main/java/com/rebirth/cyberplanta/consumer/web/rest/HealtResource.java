package com.rebirth.cyberplanta.consumer.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HealtResource {

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        log.info("Someone has requested a ping to the service.");
        return ResponseEntity.ok("OK!");
    }

}
