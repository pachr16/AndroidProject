package sdu.shoppinglistapp;

import java.util.ArrayList;

public class ShopList {
    private int id = -1;
    private long time = 0;
    private ArrayList<Integer> users = new ArrayList<>();
    private ArrayList<ShopItem> items = new ArrayList<>();


    public ShopList(int userid, ArrayList<ShopItem> items) {
        this.time = System.currentTimeMillis();
        this.users.add(userid);     // id of creating user, add further users with dedicated method
        this.items = items;
    }

    public ShopList(int id, long time, int userid, ArrayList<ShopItem> items) {
        this.id = id;
        this.time = time;
        this.users.add(userid);     // id of creating user, add further users with dedicated method
        this.items = items;
    }

    public void addUser(int userid) {
        this.users.add(userid);
    }

    public void removeUser(int userid) {
        this.users.remove(userid);
    }

    public void addItem(ShopItem item) {
        this.items.add(item);
    }

    public void removeItem(ShopItem item) {
        this.items.remove(item);
    }

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public ArrayList<Integer> getUsers() {
        return users;
    }

    public ArrayList<ShopItem> getItems() {
        return items;
    }

}
