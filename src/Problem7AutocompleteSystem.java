import java.util.*;

public class Problem7AutocompleteSystem {

    // query -> frequency
    static HashMap<String, Integer> queryFrequency = new HashMap<>();

    public static void addQuery(String query) {

        queryFrequency.put(query,
                queryFrequency.getOrDefault(query, 0) + 1);
    }

    public static List<String> search(String prefix) {

        List<Map.Entry<String, Integer>> matches = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : queryFrequency.entrySet()) {

            if (entry.getKey().startsWith(prefix)) {
                matches.add(entry);
            }
        }

        matches.sort((a, b) -> b.getValue() - a.getValue());

        List<String> results = new ArrayList<>();

        for (int i = 0; i < Math.min(10, matches.size()); i++) {

            Map.Entry<String, Integer> entry = matches.get(i);

            results.add(entry.getKey() + " (" + entry.getValue() + ")");
        }

        return results;
    }

    public static void main(String[] args) {

        addQuery("java tutorial");
        addQuery("javascript tutorial");
        addQuery("java download");
        addQuery("java tutorial");
        addQuery("java interview questions");
        addQuery("java tutorial");

        System.out.println("Suggestions for 'jav':");

        List<String> suggestions = search("jav");

        for (String s : suggestions) {
            System.out.println(s);
        }
    }
}
