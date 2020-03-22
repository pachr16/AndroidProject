package sdu.shoppinglistapp.business;

import java.io.Serializable;

public class ShopItem implements Serializable {
    private String itemString = "";
    private boolean checkmarked = false;
    private String addedBy;
    private int id = -1;


    /**
     * for creating a new item that has not yet been given an id
     * @param itemString
     * @param username
     */
    public ShopItem(String itemString, String username) {
        this.itemString = itemString;
        this.addedBy = username;
    }

    /**
     * for creating existing items from the database
     * @param itemString
     * @param checkmarked
     * @param username
     * @param id
     */
    public ShopItem(String itemString, boolean checkmarked, String username, int id) {
        this.itemString = itemString;
        this.checkmarked = checkmarked;
        this.addedBy = username;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getItemString() {
        return itemString;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public boolean isCheckmarked() {
        return checkmarked;
    }

    public void flipCheckmarked() {
        if (this.checkmarked) {
            checkmarked = false;
        } else {
            checkmarked = true;
        }
    }

    public void setId(int id) {
        this.id = id;
    }
}
