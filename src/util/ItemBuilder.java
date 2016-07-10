package util;

import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.Item;

public class ItemBuilder {

    public HashMap<String, Item> getItemMap(JSONObject json, String item_name) {

        final HashMap<String, Item> items = new HashMap<>();

        final JSONObject item_set = (JSONObject) (json.get(item_name));
        final Object[] keys_array = item_set.keySet().toArray();

        for (Object key : keys_array) {
            String key_str = (String) key;
            JSONArray list = (JSONArray) item_set.get(key_str);
            items.put(key_str, new Item(list));
        }

        return items;
    }
}
