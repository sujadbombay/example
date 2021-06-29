package com.relias.smartmatch.user.web.rest.vm;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseBody
    String healthStatus() {
        return "ok";
    }
}
