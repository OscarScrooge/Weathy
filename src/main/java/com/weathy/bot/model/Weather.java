package com.weathy.bot.model;

public class Weather {

    private String temperature;
    private String weatherPhrase;
    private String dayLight;
    private String precipitation;
    private String hairQuality;
    private String hoursOfSun;

    public Weather(){}


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

    public void setHairQuality(String hairQuality) {
        this.hairQuality = hairQuality;
    }

    public void setHoursOfSun(String hoursOfSun) {
        this.hoursOfSun = hoursOfSun;
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

    public String getHairQuality() {
        return hairQuality;
    }

    public String getHoursOfSun() {
        return hoursOfSun;
    }
}
