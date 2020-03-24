package sdu.shoppinglistapp.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sdu.shoppinglistapp.persistence.DbHandler;

public class ShopList implements Serializable {
    private String id = "";
    private String listName;
    private long time = 0;
    private HashMap<Integer, String> users = new HashMap<>();
    private ArrayList<ShopItem> items = new ArrayList<>();
    private DbHandler dbh = DbHandler.getInstance();  // instance of singleton DbHandler class

    /**
     * for creating a new empty shoplist that has not yet been given id, nor a name
     * @param userid
     */
    public ShopList(int userid, String screenname) {
        this.listName = "New Shopping List";
        this.time = System.currentTimeMillis();
        this.users.put(userid, screenname);
    }

    /**
     * for creating empty shoplist that has not yet been given id, but has been given a name
     * @param userid
     */
    public ShopList(int userid, String screenname, String listName) {
        this.listName = listName;
        this.time = System.currentTimeMillis();
        this.users.put(userid, screenname);     // id of creating user, add further users with dedicated method
    }

    /**
     * for creating an existing shoplist from the database with multiple users
     * @param id
     * @param listName
     * @param time
     * @param items
     * @param users
     */
    public ShopList(String id, String listName, long time, ArrayList<ShopItem> items, HashMap<Integer, String> users) {
        this.id = id;
        this.listName = listName;
        this.time = time;
        for (Map.Entry<Integer, String> entry: users.entrySet()) {
            int u = entry.getKey();
            String sn = entry.getValue();
            this.users.put(u, sn);
        }
        this.items = items;
    }

    /*

    // *** METHODS TO MUTATE LISTS / ITEMS ON LISTS BELOW HERE ***
    // add / remove user from list
    public void addUserToList(String email) {
        int userid = dbh.getUserid(email);
        this.users.put(userid, dbh.getUserScreenname(email));
        this.updateTimeStamp();
        dbh.addUserToList(this, userid);
    }
    public void removeUser(int userid) {
        this.updateTimeStamp();
        dbh.removeUserFromList(this, userid);
        this.users.remove(userid);
    }

    // add / remove items from list
    public void addItem(ShopItem item) {
        this.updateTimeStamp();
        this.items.add(dbh.addItem(this, item));  // MAYBE THIS NEEDS TO BE CHANGED TO FIND THE MUTATED ITEM AT this.items.get(index);
    }
    public void removeItem(ShopItem item) {
        this.updateTimeStamp();
        dbh.removeItem(this, item);  // MAYBE THIS NEEDS TO BE CHANGED TO FIND THE MUTATED ITEM AT this.items.get(index);
        this.items.remove(item);
    }

    public void checkmark(ShopItem item) {
        int index = this.items.indexOf(item);
        this.items.get(index).flipCheckmarked();
        this.updateTimeStamp();
        dbh.checkmark(this, item);  // MAYBE THIS NEEDS TO BE CHANGED TO FIND THE MUTATED ITEM AT this.items.get(index);
    }

    /**
     * checks for updates for this id in the database and updates the current object to match
     */
    /*
    public void update() {
        if (dbh.hasShopListChanged(this)) {
            ShopList newList = dbh.getShopList(this.id);
            this.items = newList.getItems();
            this.users = newList.getUsers();
            this.listName = newList.getName();
            this.time = newList.getTime();
        }
    }

    // *** GETTERS BELOW HERE ***
    public boolean hasChanged() {
        return dbh.hasShopListChanged(this);
    }

     */

    public String getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public HashMap<Integer, String> getUsers() {
        return users;
    }

    public ArrayList<ShopItem> getItems() {
        return items;
    }

    public String getName() {
        return this.listName;
    }

    // *** SETTERS BELOW HERE ***
    public void setId(String id) {
        this.id = id;
    }

    // call whenever a list is mutated to update timestamp for last change in list
    private void updateTimeStamp() {
        this.time = System.currentTimeMillis();
    }
}
