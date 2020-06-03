package com.weathy.bot.model;

import java.util.ArrayList;
import java.util.List;

public class State {

    private String name;
    private List<City> cities = new ArrayList<>();

    public State(String name, List<Integer> idCities){
         this.name = name;
         setCities(idCities);
    }

    private void setCities(List<Integer> idCities){
        for(int id:idCities){
            City city = new City(id);
            cities.add(city);
        }
    }
}
