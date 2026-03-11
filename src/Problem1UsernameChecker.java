import java.util.*;

public class Problem1UsernameChecker {

    // username -> userId
    static HashMap<String, Integer> userDatabase = new HashMap<>();

    // username -> attempt frequency
    static HashMap<String, Integer> attemptFrequency = new HashMap<>();

    // Check availability
    public static boolean checkAvailability(String username) {

        attemptFrequency.put(username,
                attemptFrequency.getOrDefault(username, 0) + 1);

        return !userDatabase.containsKey(username);
    }

    // Suggest alternatives
    public static List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        suggestions.add(username + "1");
        suggestions.add(username + "2");
        suggestions.add(username.replace("_", "."));

        return suggestions;
    }

    // Find most attempted username
    public static String getMostAttempted() {

        String mostAttempted = null;
        int max = 0;

        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {

            if (entry.getValue() > max) {
                max = entry.getValue();
                mostAttempted = entry.getKey();
            }
        }

        return mostAttempted + " (" + max + " attempts)";
    }

    public static void main(String[] args) {

        // Existing users
        userDatabase.put("john_doe", 101);
        userDatabase.put("admin", 102);

        System.out.println("Check username availability:");

        System.out.println("john_doe → " + checkAvailability("john_doe"));
        System.out.println("jane_smith → " + checkAvailability("jane_smith"));

        System.out.println();

        System.out.println("Suggestions for john_doe:");
        System.out.println(suggestAlternatives("john_doe"));

        // Simulate many attempts
        checkAvailability("admin");
        checkAvailability("admin");
        checkAvailability("admin");

        System.out.println();

        System.out.println("Most attempted username:");
        System.out.println(getMostAttempted());
    }
}
