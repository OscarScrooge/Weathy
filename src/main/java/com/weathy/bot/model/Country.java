package com.weathy.bot.model;

import java.util.*;

public class Country {

    private String name;
    private List<State> states = new ArrayList<>();

    public Country(String name, HashMap<String, List<Integer>> data){
         this.name = name;
         setCountry(data);
    }



    private void setCountry(HashMap<String, List<Integer>> data){

        for (Map.Entry<String,List<Integer>> entry : data.entrySet()) {

            String stateName = entry.getKey();
            List<Integer> idCities= entry.getValue();

            State state = new State(stateName,idCities);
            states.add(state);

        }
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states){
        this.states = states;
    }
}
