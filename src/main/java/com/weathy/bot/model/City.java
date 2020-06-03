package com.weathy.bot.model;

public class City {

    private String name;
    private int id;
    private Weather weather;

    public City(){
    }
    public City(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setWeather(Weather weather){
        this.weather = weather;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }

    public Weather getWeather(){
        return this.weather;
    }

}
