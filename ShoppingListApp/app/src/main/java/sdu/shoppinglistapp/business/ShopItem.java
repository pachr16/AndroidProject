package sdu.shoppinglistapp.business;

public class ShopItem {

    private String amount;
    private String product;

    public ShopItem(String amount, String product) {
        this.amount = amount;
        this.product = product;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
