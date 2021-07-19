package ahmed.yaqoubi.controlPanel.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;

    private String desc;
    private int amount;
    private double price;
    private String category;
    private String image;
    private boolean isFavorite;
    private float rateAverage;
    private int numOfRate;
    private String adminToken;
    public Product() {

    }

    public Product(String id, String name,   String desc, int amount, double price, String category, String image, boolean isFavorite, float rateAverage, int numOfRate) {
        this.id = id;
        this.name = name;
         this.desc = desc;
        this.amount = amount;
        this.price = price;
        this.category = category;
        this.image = image;
        this.isFavorite = isFavorite;
        this.rateAverage = rateAverage;
        this.numOfRate = numOfRate;
    }

    public float getRateAverage() {
        return rateAverage;
    }

    public void setRateAverage(float rateAverage) {
        this.rateAverage = rateAverage;
    }

    public int getNumOfRate() {
        return numOfRate;
    }

    public void setNumOfRate(int numOfRate) {
        this.numOfRate = numOfRate;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getImage() {
        return image;
    }

    public String getAdminToken() {
        return adminToken;
    }

    public void setAdminToken(String adminToken) {
        this.adminToken = adminToken;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
