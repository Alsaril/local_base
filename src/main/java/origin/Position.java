package origin;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Position {

    @SerializedName("id")
    @Expose
    @DatabaseField(id = true)
    private int id;
    @SerializedName("count")
    @Expose
    @DatabaseField
    private String count;
    @SerializedName("price")
    @Expose
    @DatabaseField
    private String price;
    @SerializedName("discount")
    @Expose
    @DatabaseField
    private String discount;
    @SerializedName("product")
    @Expose
    private Product product;

    @DatabaseField(columnName = "product")
    private int product_id;

    @DatabaseField
    private int operation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        product_id = product.getId();
    }

    public void setOperation(Operation operation) {
        this.operation = operation.getId();
    }

}
