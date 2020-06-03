package com.weathy.bot.control;


import com.weathy.bot.model.Country;
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

    public Controller( HashMap<String, List<Integer>> data){

        this.data = data;
        setCountry();

    }
    public Controller(String[] props) throws JSONException {
        getConditions(props);
    }

    private void setCountry(){
        Country country = new Country("México",this.data);
        System.out.println("dasdsa");
    }


    public void getConditions(String[] props) throws JSONException {


        JSONObject output= null;
        JSONArray jsonArray = null;


        try {
            URL path = new URL("http://dataservice.accuweather.com/forecasts/v1/hourly/1hour/200023?apikey=i8cLO8jDZn2ZE8PiVfXTb17btelOTOjI");
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
         output= (JSONObject) jsonArray.getJSONObject(0).get("Temperature");
        System.out.println(output.get("Unit"));




    }

}
