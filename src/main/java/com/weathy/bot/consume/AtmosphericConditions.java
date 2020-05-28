package com.weathy.bot.consume;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AtmosphericConditions {

    public AtmosphericConditions(){

    }

    public String getConditions(){
        String output = "empty";

        try {
            URL path = new URL("https://api.datos.gob.mx/v1/condiciones-atmosfericas");
            HttpURLConnection conn = (HttpURLConnection) path.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);

            /*while ((output = br.readLine()) != null) {
                System.out.println(output);
            }*/
            output = br.readLine();
            conn.disconnect();
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

}
