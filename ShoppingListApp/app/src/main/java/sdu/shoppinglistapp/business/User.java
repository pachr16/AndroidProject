package sdu.shoppinglistapp.business;

import java.util.ArrayList;
import java.util.List;

public class User {
    String name;
    String eMail;
    String password;
    int userID;
    List<ShopList> subscribedShopLists;

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

    public String getPasword() {
        return password;
    }

    public int getUserID() {
        return userID;
    }

    public List<ShopList> getSubscribedShopLists() {
        return subscribedShopLists;
    }


    public void setSubscribedShopLists(ShopList slist) {
        this.subscribedShopLists.add(slist);
        // update database
    }

    public void newSList (){
        ShopList slist = new ShopList(userID);
        setSubscribedShopLists(slist);
    }

    public boolean addItem (ShopItem newItem ,ShopList slist){
        //add item to shoplist
        boolean answer = false;
        return answer;// change when return is added from shoplist
    }

    public boolean removeItem ( ShopItem newItem, ShopList slist){
        //remove item to shoplist
        boolean answer = false;
        return answer;// change when return is added from shoplist
    }

    public void changeCheckmark(ShopItem chekedItem){
        //change checkmark on item
    }

}
