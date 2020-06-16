package com.weathy.bot.control;


import com.weathy.bot.model.City;
import com.weathy.bot.model.Country;
import com.weathy.bot.model.State;
import com.weathy.bot.model.Weather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class Controller {

    private HashMap<String, List<Integer>> data;
    private Country country;
    public static String accuwatherApiKey;

    public Controller(HashMap<String, List<Integer>> data) throws JSONException, UnsupportedEncodingException {
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
        JSONObject propsHairQuality= null;
        JSONObject propsTemperature= null;
        JSONObject jsonObject = null;
        JSONArray weatherPropsArray=null;
        JSONObject weatherProps=null;

        try {
            URL path = new URL("http://dataservice.accuweather.com/forecasts/v1/daily/1day/"+cityWithWeather.getId()+"?apikey="+accuwatherApiKey+"&language=es-mx&details=true&metric=true");
            HttpURLConnection conn = (HttpURLConnection) path.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());

            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);


            jsonObject = new JSONObject(br.readLine());
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
         * set the min and max temperature
         */
        weatherPropsArray =  (JSONArray) jsonObject.get("DailyForecasts");
        weatherProps = (JSONObject) weatherPropsArray.get(0);

        weatherProps = weatherProps.getJSONObject("Temperature");
        propsTemperature  = weatherProps.getJSONObject("Minimum");
        temperature = "Min: "+propsTemperature.getString("Value")+""+propsTemperature.getString("Unit");
        propsTemperature  = weatherProps.getJSONObject("Maximum");
        temperature += "  Max: "+propsTemperature.getString("Value")+""+propsTemperature.getString("Unit");
        weather.setTemperature(temperature);

        /**
         * set day conditions
         */
        weatherProps = (JSONObject) weatherPropsArray.get(0);
        weatherProps =weatherProps.getJSONObject("Day");
        weather.setWeatherDay(weatherProps.getString("ShortPhrase"));

        /**
         * set night conditions
         */
        weatherProps = (JSONObject) weatherPropsArray.get(0);
        weatherProps =weatherProps.getJSONObject("Night");
        weather.setWeatherNight(weatherProps.getString("ShortPhrase"));

        /**
         * set date
         */
        weatherProps = (JSONObject) weatherPropsArray.get(0);
        String fullDate =weatherProps.getString("Date");
        String[] date = fullDate.split(":");
        date = date[0].split("T");
        Date hour = new Date();
        String strDateFormat = "hh:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(hour);
        date[0] = " "+date[0] +"   "+formattedDate;
        weather.setDate(date[0]);

        cityWithWeather.setWeather(weather);

       return cityWithWeather;
    }

    private void setTweet() throws UnsupportedEncodingException {
        String tweetText="";
        TwitterBot bot = TwitterBot.newBot();
       for (State state : country.getStates()){
           for (City city: state.getCities()){
               tweetText+= city.getName()+" "+city.getWeather().getDate()+"\n"+
                       "Temperatura: "+city.getWeather().getTemperature()+"\n"+
                       "Día: "+city.getWeather().getWeatherDay()+"\n"+
                       "Noche: "+city.getWeather().getWeatherNight();
               bot.newTweet(tweetText);
           }
       }
    }

}
