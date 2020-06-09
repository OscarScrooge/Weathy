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
    private Country country;
    private static Controller controller = null;

    public Controller(HashMap<String, List<Integer>> data) throws JSONException {
        this.data = data;
        setCountry();
        setTweet();
    }


    /**
     * this methoth set the cities in a state and the states in a country
     * @throws JSONException
     */
    private void setCountry() throws JSONException {

        country = new Country("México",this.data);
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

    /**
     * it sets the weather by city
     * @param city
     * @return city whit weather
     * @throws JSONException
     */
    private City setWeather(City city) throws JSONException {

        City cityWithWeather = city;
        JSONObject weatherProps= null;
        JSONArray jsonArray = null;


        try {
            URL path = new URL("http://dataservice.accuweather.com/forecasts/v1/daily/1day/"+cityWithWeather.getId()+"?apikey=i8cLO8jDZn2ZE8PiVfXTb17btelOTOjI&language=es-mx&details=true&metric=true");
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

        String temperature;

        Weather weather  = new Weather();

        /**
         * set the hair quality
         */
        weatherProps = (JSONObject) jsonArray.getJSONObject(0).get("Headline");
        weather.setHairQuality(weatherProps.getString("Text"));

        /**
         * set the min and max temperature
         */
        weatherProps = (JSONObject) jsonArray.getJSONObject(0).get("DailyForecasts");
        weatherProps =weatherProps.getJSONObject("Temperature");
        weatherProps  = weatherProps.getJSONObject("Minimum");
        temperature = "Min: "+weatherProps.getString("Value")+""+weatherProps.getString("Unit");
        weatherProps  = weatherProps.getJSONObject("Maximum");
        temperature += "  Max: "+weatherProps.getString("Value")+""+weatherProps.getString("Unit");
        weather.setTemperature(temperature);

        /**
         * set day conditions
         */
        weatherProps = (JSONObject) jsonArray.getJSONObject(0).get("DailyForecasts");
        weatherProps =weatherProps.getJSONObject("Day");
        weather.setWeatherPhrase(weatherProps.getString("IconPhrase"));

        /*weather.setTemperature(""+weatherProps.get("Value")+" "+weatherProps.get("Unit"));
        weatherProps = (JSONObject) jsonArray.getJSONObject(0);
        weather.setDayLight(weatherProps.getString("IsDaylight"));
        weather.setPrecipitation(weatherProps.getString("PrecipitationProbability"));
        weather.setWeatherPhrase(weatherProps.getString("IconPhrase"));*/
        cityWithWeather.setWeather(weather);

       return cityWithWeather;
    }

    private void setTweet(){
        String tweetText="";
        TwitterBot bot = TwitterBot.newBot();
       for (State state : country.getStates()){
           for (City city: state.getCities()){
               tweetText+= city.getName()+"\n"+
                       city.getWeather().getWeatherPhrase()+"\n"+
                       city.getWeather().getHairQuality()+"\n"+
                       city.getWeather().getTemperature();
               bot.newTweet(tweetText);
           }
       }
    }

}
