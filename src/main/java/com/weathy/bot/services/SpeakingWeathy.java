package com.weathy.bot.services;

import com.weathy.bot.control.Controller;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class SpeakingWeathy {

    @Value("#{${props.mexico.city}}")
    private HashMap<String, List<Integer>> data;

    @RequestMapping("/")
    public  String SayHello() throws JSONException {
       Controller conditions= new Controller(data);

       return "hola";
    }
}
