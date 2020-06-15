package com.weathy.bot.model;

public class Weather {

    private String temperature;
    private String weatherDay;
    private String weatherNight;
    private String date;

    public Weather(){}


    public void setTemperature(String temperature){
        this.temperature = temperature;
    }

    public void setWeatherDay(String weatherDay) {
        this.weatherDay = weatherDay;
    }

    public String getWeatherNight() {
        return weatherNight;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature(){
        return this.temperature;
    }

    public String getDate() {
        return date;
    }

    public String getWeatherDay() {
        return weatherDay;
    }

    public void setWeatherNight(String weatherNight) {
        this.weatherNight = weatherNight;
    }
}
