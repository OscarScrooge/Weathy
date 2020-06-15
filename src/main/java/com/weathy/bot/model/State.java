package com.weathy.bot.model;

import java.util.ArrayList;
import java.util.List;

public class State {

    private String name;
    private List<City> cities = new ArrayList<>();

    public State(String name, List<Integer> idCities){
         this.name = name;
        setCitiesAtBeginig(idCities);
    }

    private void setCitiesAtBeginig(List<Integer> idCities){
        for(int id:idCities){
            City city = new City(id);
            city.setName(this.name);
            cities.add(city);
        }
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
