import java.util.*;

public class Problem2FlashSaleInventory {

    // productId -> stock
    static HashMap<String, Integer> inventory = new HashMap<>();

    // waiting list
    static LinkedHashMap<String, List<Integer>> waitingList = new LinkedHashMap<>();

    public static int checkStock(String productId) {
        return inventory.getOrDefault(productId, 0);
    }

    public static synchronized String purchaseItem(String productId, int userId) {

        int stock = inventory.getOrDefault(productId, 0);

        if (stock > 0) {
            inventory.put(productId, stock - 1);
            return "Success! Remaining stock: " + (stock - 1);
        } else {

            waitingList.putIfAbsent(productId, new ArrayList<>());
            waitingList.get(productId).add(userId);

            int position = waitingList.get(productId).size();

            return "Out of stock. Added to waiting list. Position #" + position;
        }
    }

    public static void main(String[] args) {

        inventory.put("IPHONE15_256GB", 3);

        System.out.println("Stock: " + checkStock("IPHONE15_256GB"));

        System.out.println(purchaseItem("IPHONE15_256GB", 101));
        System.out.println(purchaseItem("IPHONE15_256GB", 102));
        System.out.println(purchaseItem("IPHONE15_256GB", 103));
        System.out.println(purchaseItem("IPHONE15_256GB", 104));
        System.out.println(purchaseItem("IPHONE15_256GB", 105));

    }
}