package io.cjf.jpaywepayback.controller;

import io.cjf.jpaywepayback.dto.PrepayDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/getPrepay")
    public PrepayDTO getPrepay(){
        return null;
    }

}
