import java.io.Serializable;
import java.util.Date;

public class GloceryItem implements Serializable {
    private final int itemCode;
    private final String name;
    private final double price;
    private final double weight;
    private final Date manufac;
    private final Date exp;
    private final String manufacturer;

    public GloceryItem(int item_code, String name, double price, double weight, Date manufac, Date exp, String manufacturer){
        itemCode = item_code;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.manufac = manufac;
        this.exp = exp;
        this.manufacturer = manufacturer;
    }

    public int getItemCode() {
        return itemCode;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public Date getManufac() {
        return manufac;
    }

    public Date getExp() {
        return exp;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
