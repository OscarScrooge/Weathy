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
        JSONObject propsHairQuality= null;
        JSONObject propsTemperature= null;
        JSONObject jsonObject = null;
        JSONArray weatherPropsArray=null;
        JSONObject weatherProps=null;

       /* String object ="{\n" +
                "    \"Headline\": {\n" +
                "        \"EffectiveDate\": \"2020-06-17T01:00:00+05:30\",\n" +
                "        \"EffectiveEpochDate\": 1592335800,\n" +
                "        \"Severity\": 2,\n" +
                "        \"Text\": \"Tormentas eléctricas en el área desde el martes a última hora de la noche hasta el miércoles por la tarde\",\n" +
                "        \"Category\": \"thunderstorm\",\n" +
                "        \"EndDate\": \"2020-06-17T19:00:00+05:30\",\n" +
                "        \"EndEpochDate\": 1592400600,\n" +
                "        \"MobileLink\": \"http://m.accuweather.com/es/in/gobindapur/2920226/extended-weather-forecast/2920226?unit=c&lang=es-mx\",\n" +
                "        \"Link\": \"http://www.accuweather.com/es/in/gobindapur/2920226/daily-weather-forecast/2920226?unit=c&lang=es-mx\"\n" +
                "    },\n" +
                "    \"DailyForecasts\": [\n" +
                "        {\n" +
                "            \"Date\": \"2020-06-12T07:00:00+05:30\",\n" +
                "            \"EpochDate\": 1591925400,\n" +
                "            \"Sun\": {\n" +
                "                \"Rise\": \"2020-06-12T05:05:00+05:30\",\n" +
                "                \"EpochRise\": 1591918500,\n" +
                "                \"Set\": \"2020-06-12T18:26:00+05:30\",\n" +
                "                \"EpochSet\": 1591966560\n" +
                "            },\n" +
                "            \"Moon\": {\n" +
                "                \"Rise\": \"2020-06-12T23:54:00+05:30\",\n" +
                "                \"EpochRise\": 1591986240,\n" +
                "                \"Set\": \"2020-06-13T11:47:00+05:30\",\n" +
                "                \"EpochSet\": 1592029020,\n" +
                "                \"Phase\": \"WaningGibbous\",\n" +
                "                \"Age\": 21\n" +
                "            },\n" +
                "            \"Temperature\": {\n" +
                "                \"Minimum\": {\n" +
                "                    \"Value\": 28.4,\n" +
                "                    \"Unit\": \"C\",\n" +
                "                    \"UnitType\": 17\n" +
                "                },\n" +
                "                \"Maximum\": {\n" +
                "                    \"Value\": 31.2,\n" +
                "                    \"Unit\": \"C\",\n" +
                "                    \"UnitType\": 17\n" +
                "                }\n" +
                "            },\n" +
                "            \"RealFeelTemperature\": {\n" +
                "                \"Minimum\": {\n" +
                "                    \"Value\": 33.8,\n" +
                "                    \"Unit\": \"C\",\n" +
                "                    \"UnitType\": 17\n" +
                "                },\n" +
                "                \"Maximum\": {\n" +
                "                    \"Value\": 38.5,\n" +
                "                    \"Unit\": \"C\",\n" +
                "                    \"UnitType\": 17\n" +
                "                }\n" +
                "            },\n" +
                "            \"RealFeelTemperatureShade\": {\n" +
                "                \"Minimum\": {\n" +
                "                    \"Value\": 33.8,\n" +
                "                    \"Unit\": \"C\",\n" +
                "                    \"UnitType\": 17\n" +
                "                },\n" +
                "                \"Maximum\": {\n" +
                "                    \"Value\": 38.1,\n" +
                "                    \"Unit\": \"C\",\n" +
                "                    \"UnitType\": 17\n" +
                "                }\n" +
                "            },\n" +
                "            \"HoursOfSun\": 1.8,\n" +
                "            \"DegreeDaySummary\": {\n" +
                "                \"Heating\": {\n" +
                "                    \"Value\": 0.0,\n" +
                "                    \"Unit\": \"C\",\n" +
                "                    \"UnitType\": 17\n" +
                "                },\n" +
                "                \"Cooling\": {\n" +
                "                    \"Value\": 12.0,\n" +
                "                    \"Unit\": \"C\",\n" +
                "                    \"UnitType\": 17\n" +
                "                }\n" +
                "            },\n" +
                "            \"AirAndPollen\": [\n" +
                "                {\n" +
                "                    \"Name\": \"AirQuality\",\n" +
                "                    \"Value\": 68,\n" +
                "                    \"Category\": \"Moderado\",\n" +
                "                    \"CategoryValue\": 2,\n" +
                "                    \"Type\": \"Ozono\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"Name\": \"Grass\",\n" +
                "                    \"Value\": 0,\n" +
                "                    \"Category\": \"Bajo\",\n" +
                "                    \"CategoryValue\": 1\n" +
                "                },\n" +
                "                {\n" +
                "                    \"Name\": \"Mold\",\n" +
                "                    \"Value\": 0,\n" +
                "                    \"Category\": \"Bajo\",\n" +
                "                    \"CategoryValue\": 1\n" +
                "                },\n" +
                "                {\n" +
                "                    \"Name\": \"Ragweed\",\n" +
                "                    \"Value\": 0,\n" +
                "                    \"Category\": \"Bajo\",\n" +
                "                    \"CategoryValue\": 1\n" +
                "                },\n" +
                "                {\n" +
                "                    \"Name\": \"Tree\",\n" +
                "                    \"Value\": 0,\n" +
                "                    \"Category\": \"Bajo\",\n" +
                "                    \"CategoryValue\": 1\n" +
                "                },\n" +
                "                {\n" +
                "                    \"Name\": \"UVIndex\",\n" +
                "                    \"Value\": 4,\n" +
                "                    \"Category\": \"Moderado\",\n" +
                "                    \"CategoryValue\": 2\n" +
                "                }\n" +
                "            ],\n" +
                "            \"Day\": {\n" +
                "                \"Icon\": 12,\n" +
                "                \"IconPhrase\": \"Chubascos\",\n" +
                "                \"HasPrecipitation\": true,\n" +
                "                \"PrecipitationType\": \"Rain\",\n" +
                "                \"PrecipitationIntensity\": \"Moderate\",\n" +
                "                \"ShortPhrase\": \"Lluvias ocasionales y tormenta eléctrica\",\n" +
                "                \"LongPhrase\": \"Lluvias ocasionales y tormenta eléctrica\",\n" +
                "                \"PrecipitationProbability\": 57,\n" +
                "                \"ThunderstormProbability\": 60,\n" +
                "                \"RainProbability\": 57,\n" +
                "                \"SnowProbability\": 0,\n" +
                "                \"IceProbability\": 0,\n" +
                "                \"Wind\": {\n" +
                "                    \"Speed\": {\n" +
                "                        \"Value\": 5.6,\n" +
                "                        \"Unit\": \"km/h\",\n" +
                "                        \"UnitType\": 7\n" +
                "                    },\n" +
                "                    \"Direction\": {\n" +
                "                        \"Degrees\": 146,\n" +
                "                        \"Localized\": \"SE\",\n" +
                "                        \"English\": \"SE\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"WindGust\": {\n" +
                "                    \"Speed\": {\n" +
                "                        \"Value\": 18.5,\n" +
                "                        \"Unit\": \"km/h\",\n" +
                "                        \"UnitType\": 7\n" +
                "                    },\n" +
                "                    \"Direction\": {\n" +
                "                        \"Degrees\": 172,\n" +
                "                        \"Localized\": \"S\",\n" +
                "                        \"English\": \"S\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"TotalLiquid\": {\n" +
                "                    \"Value\": 5.6,\n" +
                "                    \"Unit\": \"mm\",\n" +
                "                    \"UnitType\": 3\n" +
                "                },\n" +
                "                \"Rain\": {\n" +
                "                    \"Value\": 5.6,\n" +
                "                    \"Unit\": \"mm\",\n" +
                "                    \"UnitType\": 3\n" +
                "                },\n" +
                "                \"Snow\": {\n" +
                "                    \"Value\": 0.0,\n" +
                "                    \"Unit\": \"cm\",\n" +
                "                    \"UnitType\": 4\n" +
                "                },\n" +
                "                \"Ice\": {\n" +
                "                    \"Value\": 0.0,\n" +
                "                    \"Unit\": \"mm\",\n" +
                "                    \"UnitType\": 3\n" +
                "                },\n" +
                "                \"HoursOfPrecipitation\": 2.0,\n" +
                "                \"HoursOfRain\": 2.0,\n" +
                "                \"HoursOfSnow\": 0.0,\n" +
                "                \"HoursOfIce\": 0.0,\n" +
                "                \"CloudCover\": 94\n" +
                "            },\n" +
                "            \"Night\": {\n" +
                "                \"Icon\": 15,\n" +
                "                \"IconPhrase\": \"Tormentas eléctricas\",\n" +
                "                \"HasPrecipitation\": true,\n" +
                "                \"PrecipitationType\": \"Rain\",\n" +
                "                \"PrecipitationIntensity\": \"Light\",\n" +
                "                \"ShortPhrase\": \"Tormenta eléctrica en partes del área\",\n" +
                "                \"LongPhrase\": \"Tormenta eléctrica en partes del área\",\n" +
                "                \"PrecipitationProbability\": 40,\n" +
                "                \"ThunderstormProbability\": 60,\n" +
                "                \"RainProbability\": 40,\n" +
                "                \"SnowProbability\": 0,\n" +
                "                \"IceProbability\": 0,\n" +
                "                \"Wind\": {\n" +
                "                    \"Speed\": {\n" +
                "                        \"Value\": 9.3,\n" +
                "                        \"Unit\": \"km/h\",\n" +
                "                        \"UnitType\": 7\n" +
                "                    },\n" +
                "                    \"Direction\": {\n" +
                "                        \"Degrees\": 184,\n" +
                "                        \"Localized\": \"S\",\n" +
                "                        \"English\": \"S\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"WindGust\": {\n" +
                "                    \"Speed\": {\n" +
                "                        \"Value\": 16.7,\n" +
                "                        \"Unit\": \"km/h\",\n" +
                "                        \"UnitType\": 7\n" +
                "                    },\n" +
                "                    \"Direction\": {\n" +
                "                        \"Degrees\": 181,\n" +
                "                        \"Localized\": \"S\",\n" +
                "                        \"English\": \"S\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"TotalLiquid\": {\n" +
                "                    \"Value\": 1.0,\n" +
                "                    \"Unit\": \"mm\",\n" +
                "                    \"UnitType\": 3\n" +
                "                },\n" +
                "                \"Rain\": {\n" +
                "                    \"Value\": 1.0,\n" +
                "                    \"Unit\": \"mm\",\n" +
                "                    \"UnitType\": 3\n" +
                "                },\n" +
                "                \"Snow\": {\n" +
                "                    \"Value\": 0.0,\n" +
                "                    \"Unit\": \"cm\",\n" +
                "                    \"UnitType\": 4\n" +
                "                },\n" +
                "                \"Ice\": {\n" +
                "                    \"Value\": 0.0,\n" +
                "                    \"Unit\": \"mm\",\n" +
                "                    \"UnitType\": 3\n" +
                "                },\n" +
                "                \"HoursOfPrecipitation\": 0.5,\n" +
                "                \"HoursOfRain\": 0.5,\n" +
                "                \"HoursOfSnow\": 0.0,\n" +
                "                \"HoursOfIce\": 0.0,\n" +
                "                \"CloudCover\": 98\n" +
                "            },\n" +
                "            \"Sources\": [\n" +
                "                \"AccuWeather\"\n" +
                "            ],\n" +
                "            \"MobileLink\": \"http://m.accuweather.com/es/in/gobindapur/2920226/daily-weather-forecast/2920226?day=1&unit=c&lang=es-mx\",\n" +
                "            \"Link\": \"http://www.accuweather.com/es/in/gobindapur/2920226/daily-weather-forecast/2920226?day=1&unit=c&lang=es-mx\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        jsonObject = new JSONObject(object);*/

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
        weather.setDate(date[0]);

        cityWithWeather.setWeather(weather);

       return cityWithWeather;
    }

    private void setTweet(){
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
