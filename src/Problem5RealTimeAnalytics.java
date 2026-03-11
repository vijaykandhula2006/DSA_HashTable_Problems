import java.util.*;

class Event {
    String url;
    String userId;
    String source;

    Event(String url, String userId, String source) {
        this.url = url;
        this.userId = userId;
        this.source = source;
    }
}

public class Problem5RealTimeAnalytics {

    static HashMap<String, Integer> pageViews = new HashMap<>();
    static HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    static HashMap<String, Integer> trafficSources = new HashMap<>();

    public static void processEvent(Event event) {

        // Count page views
        pageViews.put(event.url, pageViews.getOrDefault(event.url, 0) + 1);

        // Track unique visitors
        uniqueVisitors.putIfAbsent(event.url, new HashSet<>());
        uniqueVisitors.get(event.url).add(event.userId);

        // Count traffic sources
        trafficSources.put(event.source,
                trafficSources.getOrDefault(event.source, 0) + 1);
    }

    public static void getDashboard() {

        System.out.println("\nTop Pages:");

        pageViews.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(10)
                .forEach(entry -> {

                    String url = entry.getKey();
                    int views = entry.getValue();
                    int unique = uniqueVisitors.get(url).size();

                    System.out.println(url + " → " + views + " views (" + unique + " unique)");
                });

        System.out.println("\nTraffic Sources:");

        int total = trafficSources.values().stream().mapToInt(i -> i).sum();

        for (Map.Entry<String, Integer> entry : trafficSources.entrySet()) {

            double percent = (entry.getValue() * 100.0) / total;

            System.out.println(entry.getKey() + ": " + percent + "%");
        }
    }

    public static void main(String[] args) {

        processEvent(new Event("/article/breaking-news", "user1", "google"));
        processEvent(new Event("/article/breaking-news", "user2", "facebook"));
        processEvent(new Event("/sports/championship", "user3", "google"));
        processEvent(new Event("/sports/championship", "user1", "direct"));
        processEvent(new Event("/article/breaking-news", "user3", "google"));
        processEvent(new Event("/sports/championship", "user4", "google"));

        getDashboard();
    }
}