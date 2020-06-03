package com.weathy.bot.model;

public class Weather {

    private String temperature;
    private String weatherPhrase;
    private String dayLight;
    private String precipitation;

    public Weather(){}

    public Weather(String temperature,String weatherPhrase,String dayLight,String precipitation){
          this.temperature = temperature;
          this.weatherPhrase = weatherPhrase;
          this.dayLight = dayLight;
          this.precipitation = precipitation;
    }

    public void setTemperature(String temperature){
        this.temperature = temperature;
    }

    public void setWeatherPhrase(String weatherPhrase){
        this.weatherPhrase = weatherPhrase;
    }

    public void setDayLight(String dayLight){
        this.dayLight = dayLight;
    }

    public void setPrecipitation(String precipitation1){
        this.precipitation = precipitation1;
    }

    public String getTemperature(){
        return this.temperature;
    }

    public String getWeatherPhrase(){
        return this.weatherPhrase;
    }

    public String getDayLight(){
        return this.dayLight;
    }

    public String getPrecipitation(){
        return this.precipitation;
    }
}
