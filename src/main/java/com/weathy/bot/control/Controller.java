package com.weathy.bot.control;


import com.weathy.bot.model.City;
import com.weathy.bot.model.Country;
import com.weathy.bot.model.State;
import com.weathy.bot.model.Weather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class Controller {

    private HashMap<String, List<Integer>> data;

    public Controller( HashMap<String, List<Integer>> data) throws JSONException {

        this.data = data;
        setCountry();

    }


    private void setCountry() throws JSONException {

        Country country = new Country("México",this.data);
        List<State> statesList = country.getStates(); //LISTA DE ESTADOS

        for (int i=0; i<statesList.size();i++) {

             List<City> citiesList = statesList.get(i).getCities(); // CONTIENE CIUDADES POR ESTADO

             for (int j = 0; j< citiesList.size();j++){   // SETEA EL CLIMA PARA CADA CIUDAD

                  City city = setWeather(citiesList.get(j));
                 citiesList.set(j,city);

             }
             statesList.get(i).setCities(citiesList);
        }
        country.setStates(statesList);
    }


    public City setWeather(City city) throws JSONException {

        City cityWithWeather = city;
        JSONObject weatherProps= null;
        JSONArray jsonArray = null;


        try {
            URL path = new URL("http://dataservice.accuweather.com/forecasts/v1/hourly/1hour/"+cityWithWeather.getId()+"?apikey=i8cLO8jDZn2ZE8PiVfXTb17btelOTOjI");
            HttpURLConnection conn = (HttpURLConnection) path.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());

            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);


            jsonArray = new JSONArray(br.readLine()) ;
            conn.disconnect();
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Weather weather  = new Weather();
        weatherProps = (JSONObject) jsonArray.getJSONObject(0).get("Temperature");
        weather.setTemperature(""+weatherProps.get("Value")+" "+weatherProps.get("Unit"));
        weatherProps = (JSONObject) jsonArray.getJSONObject(0);
        weather.setDayLight(weatherProps.getString("IsDaylight"));
        weather.setPrecipitation(weatherProps.getString("PrecipitationProbability"));
        weather.setWeatherPhrase(weatherProps.getString("IconPhrase"));
        cityWithWeather.setWeather(weather);

       return cityWithWeather;
    }

}
