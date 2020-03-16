package sdu.shoppinglistapp;

import java.util.List;

public class User {
    string name;
    string eMail;
    string pasword;
    int userID;
    List<ShopList> subscribedShopLists;


    public User(string name, string eMail, string pasword, int userID, List<ShopList> subscribedShopLists) {
        this.name = name;
        this.eMail = eMail;
        this.pasword = pasword;
        this.userID = userID;
        this.subscribedShopLists = subscribedShopLists;
    }


    public string getName() {
        return name;
    }

    public string geteMail() {
        return eMail;
    }

    public string getPasword() {
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
        ShopList slist;
        //create a new slist
        setSubscribedShopLists(slist);
    }

    public boolean addItem (newItem ShopItem, slist ShopList){
        //add item to shoplist
        boolean answer = false;
        return answer;// change when return is added from shoplist
    }

    public boolean removeItem (newItem ShopItem, slist ShopList){
        //remove item to shoplist
        boolean answer = false;
        return answer;// change when return is added from shoplist
    }

    public void changeCheckmark(chekedItem ShopItem){
        //change checkmark on item
    }
}
