package sdu.shoppinglistapp;

public class ShopItem {
    private String itemString = "";
    private boolean checkmarked = false;
    private User addedBy;
    private int id = -1;


    public ShopItem(String itemString, boolean checkmarked, User addedBy) {
        this.itemString = itemString;
        this.checkmarked = checkmarked;
        this.addedBy = addedBy;
    }

    public ShopItem(String itemString, boolean checkmarked, User addedBy, int id) {
        this.itemString = itemString;
        this.checkmarked = checkmarked;
        this.addedBy = addedBy;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getItemString() {
        return itemString;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public boolean isCheckmarked() {
        return checkmarked;
    }

    public void setCheckmarked(boolean checkmarked) {
        this.checkmarked = checkmarked;
    }
}
