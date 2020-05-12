package sdu.shoppinglistapp.business;

public class ShopItem {

    private String amount;
    private String product;
    private String picked;

    public ShopItem(String amount, String product, String picked) {
        this.amount = amount;
        this.product = product;
        this.picked = picked;
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

    public String getPicked() {
        return picked;
    }

    public void setPicked(String picked) {
        this.picked = picked;
    }
}
