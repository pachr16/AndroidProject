package sdu.shoppinglistapp;

import java.util.List;

public class User {
    String name;
    String eMail;
    String pasword;
    int userID;
    List<ShopList> subscribedShopLists;


    public User(String name, String eMail, String pasword, int userID, List<ShopList> subscribedShopLists) {
        this.name = name;
        this.eMail = eMail;
        this.pasword = pasword;
        this.userID = userID;
        this.subscribedShopLists = subscribedShopLists;
    }


    public String getName() {
        return name;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPasword() {
        return pasword;
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
