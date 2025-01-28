package ubb.scs.map.avioane.Domain;

public class MenuItem {
    private Integer id;
    private String category;
    private String item;
    private Float price;
    private String currency;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public MenuItem(String category, String item, Float price, String currency) {
        this.category = category;
        this.item = item;
        this.price = price;
        this.currency = currency;
    }
}
