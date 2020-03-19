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
        //SELECT users.user_id
        //FROM users
        //WHERE users.email = email
    }

    public ShopItem addItem(ShopList slist, ShopItem item) {
        ShopItem item;
        // TODO query database to add items to list
        /*
        INSERT INTO items(name, added_by, checkmarked, list_id)
        VALUES(<name>,<added_by>,<checkmarked>,<list_id>)
         */
        return item;
    }

    public void removeItem(ShopList slist, ShopItem item) {
        // TODO query database to remove items from list
        /*
        DELETE FROM items
        WHERE items.item_id = <value>
         */
    }

    public void checkmark(ShopList slist, ShopItem item) {
        // TODO query database to set checkmark-boolean to TRUE if currently FALSE, and the other way around
        /*
        UPDATE items
        SET checkmarked = <value>
        WHERE items.item_id = <value>
         */
    }

    public void addUserToList(ShopList slist, int userid) {
        // TODO query database to connect user with this userid to the given slist
        /*
        INSERT INTO lists(list_id, user_id)
        VALUES(<list_id>, <user_id>)
         */
    }

    public void removeUserFromList(ShopList slist, int userid) {
        // TODO query database to remove the given user from given slist
        /*
        DELETE FROM lists
        WHERE lists.list_id = <value> AND lists.user_id = <value>
         */
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
        /*
        SELECT lists.last_updated
        FROM lists
        WHERE lists.list_id = <value>
         */
    }

    public ShopList createShopList(ShopList slist) {
        // TODO query database to create a new ShopList in the database
        /*
        INSERT INTO lists(user_id, list_name, last_updated)
        VALUES(<user_id>, <list_name>, <last_updated>)
        RETURNING lists.list_id
         */
    }

    public User registerUser(User user) {
        // TODO query database to create a new User in the database
        // TODO return the User object with the database-given id!
        /*
        INSERT INTO users(screen_name, email, password)
        VALUES(<screen_name>, <email>, <password>)
        RETURNING users.user_id

         */
    }

    public boolean doesEmailExist(String email) {
        // TODO query database to check if the given email already exists in the database - return TRUE if it DOES
        /*
        SELECT users.email
        FROM users
        WHERE users.email = <value>
         */
    }

    public List<ShopList> getShoplistOverview(User user) {
        // TODO query database to get all shoplists that the User is a part of
        /*
        SELECT lists.list_id, lists.list_name
        FROM lists
        WHERE lists.user_id = <value>
         */
    }

    public User checkCredentials(String email, String pw) {
        // TODO query database to check that given email matches given password. Return the User object if it matches

        // TODO in case of mismatch, return a null object
    }
}
