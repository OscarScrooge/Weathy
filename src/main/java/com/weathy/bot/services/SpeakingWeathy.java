package com.weathy.bot.services;

import com.weathy.bot.consume.AtmosphericConditions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpeakingWeathy {

    @RequestMapping("/")
    public String SayHello(){
        AtmosphericConditions conditions= new AtmosphericConditions();
        String json =  conditions.getConditions();
       return conditions.getConditions();
    }
}
