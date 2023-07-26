package ru.sagiem.whattobuy.controller.rest;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/demo-controller")
@Hidden
public class DemoController {

    @GetMapping
    public ResponseEntity<String> sayHello(){

        return ok("Привет ! Я демо контроллер");
    }

    


}


