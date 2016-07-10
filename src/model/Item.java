package model;

import org.json.simple.JSONArray;

public class Item {

    private JSONArray json;

    public Item(JSONArray json) {
        super();
        this.json = json;
        
        System.out.println(json);
    }

}
