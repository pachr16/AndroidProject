package sdu.shoppinglistapp.business;

import java.util.ArrayList;
import java.util.List;

import sdu.shoppinglistapp.persistence.DbHandler;

public class User {
    private String name;
    private String eMail;
    private String password;
    private int userID;
    private List<ShopList> subscribedShopLists;
    private DbHandler dbh = DbHandler.getInstance();


    /**
     * for creating users that have been given an id from the database
     * @param name
     * @param eMail
     * @param password
     * @param userID
     * @param subscribedShopLists
     */
    public User(String name, String eMail, String password, int userID, List<ShopList> subscribedShopLists) {
        this.name = name;
        this.eMail = eMail;
        this.password = password;
        this.userID = userID;
        this.subscribedShopLists = subscribedShopLists;
    }

    /**
     * for registering new users in the system
     * @param name
     * @param email
     * @param password
     */
    public User(String name, String email, String password) {
        this.name = name;
        this.eMail = email;
        this.password = password;
        this.userID = -1;
        this.subscribedShopLists = new ArrayList<ShopList>();
    }

    public String getName() {
        return name;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPassword() {
        return password;
    }

    public int getUserID() {
        return userID;
    }

    public List<ShopList> getSubscribedShopLists() {
        return subscribedShopLists;
    }


    public void addSubscribedShopList(ShopList slist) {
        this.subscribedShopLists.add(slist);
        dbh.addUserToList(slist, this.userID);
    }

    public void newSList () {
        ShopList slist = new ShopList(userID);
        addSubscribedShopList(slist);
    }

    public void addItem (ShopItem newItem ,ShopList slist) {
        //add item to shoplist
        slist.addItem(newItem);
    }

    public void removeItem (ShopItem newItem, ShopList slist) {
        //remove item to shoplist
        slist.removeItem(newItem);
    }

    public void changeCheckmark(ShopItem checkedItem) {
        checkedItem.flipCheckmarked();
    }

}
