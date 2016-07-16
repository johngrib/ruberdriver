package model;

import java.util.LinkedList;

import org.json.simple.JSONArray;

import config.Const;
import lombok.Getter;
import lombok.Setter;

public class Item {

    @Getter
    private LinkedList<String> list;

    @Getter
    @Setter
    private String name;

    public Item(String name, JSONArray json) {
        super();

        LinkedList<String> sub_items = new LinkedList<>();

        for (int i = 0; i < json.size(); i++) {
            String sub = (String) json.get(i);

            if (sub.startsWith(Const.COMMENT)) {

            } else if (sub.startsWith(Const.COMMENT_QUIT)) {
                break;
            } else {
                sub_items.add(sub);
            }
        }

        this.list = sub_items;
    }

}
