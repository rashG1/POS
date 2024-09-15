import java.io.*;
import java.util.Date;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bill implements Serializable {
    private static int nextId = 0;
    private final int id;
    private final String customer;

    private final POS pos;
    private final Hashtable<Integer, BillItem> items = new Hashtable<>();

    private int getNextId() {
        return nextId++;
    }

    public Bill(POS pos) {
        this.pos = pos;
        this.id = getNextId();
        this.customer = pos.getCustomer();
        System.out.println("Created new bill #" + id);
    }

    public void addItem(GloceryItem item, int count, int discount) {
        // add item to bill
        int key = item.getItemCode();
        if (items.containsKey(key)) {
            BillItem billItem = items.get(key);
            billItem.setCount(billItem.getCount() + count);
            billItem.setDiscount(discount);
        } else {
            items.put(key, new BillItem(item, count, discount));
        }
    }

    public void deleteItem(GloceryItem item) throws ItemNotFound {
        int key = item.getItemCode();
        if (items.containsKey(key)) {
            items.remove(key);
        } else {
            throw new ItemNotFound();
        }
    }

    public void print() {
        System.out.println("----------------------------------");
        System.out.println("Cashier: " + pos.getCashier());
        System.out.println("Branch: " + pos.getBranch());
        System.out.println("Customer: " + customer);
        double totalPrice = 0;
        double totalDiscount = 0;
        for (BillItem billItem : items.values()) {
            totalPrice += billItem.getNetPrice();
            totalDiscount += billItem.getNetDiscount();
            System.out.println(billItem);
        }
        System.out.printf("Total discount: %.2f\n", totalDiscount);
        System.out.printf("Total price: %.2f\n", totalPrice);
        System.out.println("datetime: " + new Date().toString());
        System.out.println("----------------------------------");
    }

    public void store() {
        // serialize , if error occrus let use know
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bill_" + id + ".ser"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println("Error occurred when storing bill data.");
            System.out.println(e.getMessage());
        }
    }

    public static Bill retrieve() throws BillException, InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter bill id : ");
        int id = scanner.nextInt();
        String path = "bill_" + id + ".ser";
        Bill bill;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object obj = ois.readObject();
            if (obj instanceof Bill) {
                bill = (Bill) obj;
            } else {
                throw new BillException("Invalid bill data found.");
            }
            ois.close();
            // File already exists. No errors should occur.
            new File(path).delete();
        } catch (IOException | ClassNotFoundException e) {
            throw new BillException("No bill data found.");
        }
        return bill;
    }

    public int getId() {
        return id;
    }

    public boolean make() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("0. Add item");
            System.out.println("1. Remove item");
            System.out.println("2. Finalize");
            System.out.println("3. Save and exit");
            System.out.print("Enter choice : ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    addItem(pos.getItemDetails(), pos.getItemCount(), pos.getItemDiscount());
                    System.out.println("Item added to the bill");
                    break;
                case 1:
                    try {
                        deleteItem(pos.getItemDetails());
                        System.out.println("Item removed from the bill");
                    } catch (ItemNotFound e) {
                        System.out.println("Item not in the bill");
                    }
                    break;
                case 2:
                    return true;
                case 3:
                    return false;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
