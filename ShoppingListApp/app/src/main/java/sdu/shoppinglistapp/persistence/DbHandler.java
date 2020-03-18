package sdu.shoppinglistapp.persistence;

import java.util.HashMap;
import java.util.List;

import sdu.shoppinglistapp.business.ShopItem;
import sdu.shoppinglistapp.business.ShopList;
import sdu.shoppinglistapp.business.User;

public class DbHandler {
    private static DbHandler instance = null; // instance of singleton class

    // getinstance for singleton instance
    public static DbHandler getInstance() {
        if (instance == null) {
            instance = new DbHandler();
        }
        return instance;
    }

    // private constructor for singleton purposes
    private DbHandler() {
    }

    public int getUserid(String email) {
        // TODO query database to find userid for matching email
    }

    public void addItem(ShopList slist, ShopItem item) {
        // TODO query database to add items to list
    }

    public void removeItem(ShopList slist, ShopItem item) {
        // TODO query database to remove items from list
    }

    public void checkmark(ShopList slist, ShopItem item) {
        // TODO query database to set checkmark-boolean to TRUE if currently FALSE, and the other way around
    }

    public void addUserToList(ShopList slist, int userid) {
        // TODO query database to connect user with this userid to the given slist
    }

    public void removeUserFromList(ShopList slist, int userid) {
        // TODO query database to remove the given user from given slist
    }

    public HashMap<Integer, String> getUsername(List<Integer> userid) {
        // TODO query database to get screenname from userids
        // evt lav stringbuilder og kør foreach over listen??
    }

    public ShopList getShopList(int slistid) {
        // TODO query database to get shoplist from id
    }

    public boolean hasShopListChanged(ShopList slist) {
        // TODO query database to see if timestamp has changed, return true if it has changed
    }

    public ShopList createShopList(ShopList slist) {
        // TODO query database to create a new ShopList in the database
        // TODO return the shoplist with the database-given id!
    }

    public User registerUser(User user) {
        // TODO query database to create a new User in the database
        // TODO return the User object with the database-given id!
    }

    public boolean doesEmailExist(String email) {
        // TODO query database to check if the given email already exists in the database - return TRUE if it DOES
    }

    public User checkCredentials(String email, String pw) {
        // TODO query database to check that given email matches given password. Return the User object if it matches
        // TODO in case of mismatch, return a null object
    }
}
