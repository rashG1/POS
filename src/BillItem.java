import java.io.Serializable;

public class BillItem implements Serializable {

    private final GloceryItem item;

    private int count;

    private int discount;

    public BillItem(GloceryItem item, int count, int discount) {

        this.item = item;
        this.count = count;
        this.discount = discount;
    }

    public int getCount() {
        return count;
    }

    public int getDiscount() {
        return discount;
    }

    public GloceryItem getItem() {
        return item;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getNetPrice() {
        return (count * (item.getPrice() / item.getWeight())) - getNetDiscount();
    }

    public double getNetDiscount() {
        return count * (item.getPrice() / item.getWeight()) * ((double) discount / 100);
    }

    @Override
    public String toString() {
        return String.format(
                "%s - %.2f/%.2f - %d - %d%% - %.2f/=",
                item.getName(),
                item.getPrice(),
                item.getWeight(),
                count,
                discount,
                getNetPrice()
        );
    }
}
