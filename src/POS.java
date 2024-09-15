import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.*;

public class POS implements Serializable {

    // hardcoded
    public static Map<Integer, GloceryItem> database = Map.of(
            0, new GloceryItem(0, "Apple", 450, 1, new Date(), new Date(), "yumeth"),
            1, new GloceryItem(1, "Leeks", 90, 1, new Date(), new Date(), "Rashmika")
    );

    public static Map<Integer, String> customers = Map.of(
            0, "Kasun",
            1, "Sithum"
    );

    private final String cashier;
    private final String branch;

    public POS(String cashier, String branch) {
        this.cashier = cashier;
        this.branch = branch;
    }

    public GloceryItem getItemDetails() {
        GloceryItem item = null;
        try {
            // read itemcode from sysin
            InputStreamReader r = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(r);
            System.out.print("Enter item code : ");
            String item_code = br.readLine();
            int key = Integer.parseInt(item_code);

            if (database.containsKey(key)) {
                item = database.get(key);
            } else {
                throw new ItemCodeNotFound();
            }
        // if item not found, request user for another input, handles with recursion
        } catch (ItemCodeNotFound e) {
            System.out.println("Item does not exist");
            item = getItemDetails();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return item;
    }

    public int getItemCount() {
        int count;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter item count : ");
            count = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Enter an integer as the count");
            count = getItemCount();
        }
        return count;
    }

    public int getItemDiscount() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter item discount : ");
            int discount = scanner.nextInt();
            if (discount < 0 || discount >75) {
                System.out.println("Discount must be 0-75");
            } else {
                return discount;
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter an integer as the discount");
        }
        return getItemDiscount();
    }

    public String getCustomer() {
        int customerId;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter customer id : ");
            customerId = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Enter an integer as the id");
            customerId = getItemDiscount();
        }
        String customer = customers.getOrDefault(customerId, "Unregistered customer");
        System.out.println("Welcome " + customer + "!");
        return customer;
    }

    public static void start() {
        // cli, first prompts user for main funtions , then sub functions are handled in classes
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Cashier name : ");
        String name = scanner.nextLine();
        System.out.print("Enter Branch name : ");
        String branch = scanner.nextLine();
        POS pos = new POS(name, branch);

        while (true) {
            System.out.println("0. Create bill");
            System.out.println("1. Continue existing bill");
            System.out.println("2. Exit");
            System.out.print("Enter choice : ");
            try {
                Scanner scanner1 = new Scanner(System.in);
                int choice = scanner1.nextInt();
                Bill bill;
                switch (choice) {
                    case 0:
                        bill = new Bill(pos);
                        break;
                    case 1:
                        bill = Bill.retrieve();
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid choice");
                        continue;
                }
                if (bill.make()) {
                    bill.print();
                } else {
                    bill.store();
                }
            } catch (BillException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("You should enter an integer");
            }
        }
    }

    public String getCashier() {
        return cashier;
    }

    public String getBranch() {
        return branch;
    }
}
