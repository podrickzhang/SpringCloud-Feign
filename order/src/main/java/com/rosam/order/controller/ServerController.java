package com.rosam.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ServerController {

    @GetMapping("/msg")
    public String msg(){
        return  "this is product's  message";
    }

}
