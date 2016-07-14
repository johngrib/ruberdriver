package util;

import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import config.Const;
import model.Item;

public class ItemBuilder {

    public HashMap<String, Item> getItemMap(JSONObject json, String item_name) {

        final HashMap<String, Item> items = new HashMap<>();

        final JSONObject item_set = (JSONObject) (json.get(item_name));
        final Object[] keys_array = item_set.keySet().toArray();

        for (Object key : keys_array) {
            String key_str = (String) key;
            JSONObject obj = (JSONObject) item_set.get(key_str);
            String it_name = (String) obj.get(Const.NAME);
            JSONArray list = (JSONArray) obj.get(Const.ACTION);
            items.put(key_str, new Item(it_name, list));
        }

        return items;
    }
}
