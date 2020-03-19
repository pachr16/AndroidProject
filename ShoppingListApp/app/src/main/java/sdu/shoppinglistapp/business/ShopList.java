package sdu.shoppinglistapp.business;

import java.util.ArrayList;

import sdu.shoppinglistapp.persistence.DbHandler;

public class ShopList {
    private int id = -1;
    private String listName;
    private long time = 0;
    private ArrayList<Integer> users = new ArrayList<>();
    private ArrayList<ShopItem> items = new ArrayList<>();
    private DbHandler dbh = DbHandler.getInstance();  // instance of singleton DbHandler class

    /**
     * for creating empty shoplist that has not yet been given id
     * @param userid
     */
    public ShopList(int userid, String listName) {
        this.listName = listName;
        this.time = System.currentTimeMillis();
        this.users.add(userid);     // id of creating user, add further users with dedicated method
    }

    /**
     * for creating a new shoplist that already contains items
     * @param userid
     * @param items
     */
    public ShopList(int userid, String listName, ArrayList<ShopItem> items) {
        this.time = System.currentTimeMillis();
        this.listName = listName;
        this.users.add(userid);     // id of creating user, add further users with dedicated method
        this.items = items;
    }

    /**
     * for creating an existing shoplist from the database
     * @param id
     * @param time
     * @param userid
     * @param items
     */
    public ShopList(int id, String listName, long time, int userid, ArrayList<ShopItem> items) {
        this.id = id;
        this.listName = listName;
        this.time = time;
        this.users.add(userid);     // id of creating user, add further users with dedicated method
        this.items = items;
    }

    // call whenever a list is mutated to update timestamp for last change in list
    private void updateTimeStamp() {
        this.time = System.currentTimeMillis();
    }

    // *** METHODS TO MUTATE LISTS / ITEMS ON LISTS BELOW HERE ***
    // add / remove user from list
    public void addUserToList(String email) {
        int userid = dbh.getUserid(email);
        this.users.add(userid);
        this.updateTimeStamp();
        dbh.addUserToList(this, userid);
    }
    public void removeUser(int userid) {
        this.updateTimeStamp();
        dbh.removeUserFromList(this, userid);
        this.users.remove(userid);
    }

    // add / remove items from list
    public ShopItem addItem(ShopItem item) {

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

    // *** GETTERS BELOW HERE ***
    public boolean hasChanged() {
        return dbh.hasShopListChanged(this);
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

    // *** SETTERS BELOW HERE ***
    public void setItems(ArrayList<ShopItem> items) {
        this.items = items;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setUsers(ArrayList<Integer> users) {
        this.users = users;
    }
}
