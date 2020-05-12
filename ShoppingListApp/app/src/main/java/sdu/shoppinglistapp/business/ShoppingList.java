package sdu.shoppinglistapp.business;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShoppingList {

    String id;
    String name;
    ArrayList<String> subscribers;
    ArrayList<String> content;

    //Constructor for generating a shoppingList.object when querying Firebase
    public ShoppingList(String id, String name, ArrayList subscribers, ArrayList content) {
        this.id = id;
        this.name = name;
        this.subscribers = subscribers;
        this.content = content;
    }

    //Constructor for creating a new shoppingList and put it into Firebase
    public ShoppingList(String id, String name, ArrayList subscribers) {
        this.id = id;
        this.name = name;
        this.subscribers = subscribers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(ArrayList<String> subscribers) {
        this.subscribers = subscribers;
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }
}
