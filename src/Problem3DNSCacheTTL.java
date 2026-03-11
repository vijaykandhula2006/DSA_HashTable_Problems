import java.util.*;

class DNSEntry {
    String ipAddress;
    long expiryTime;

    DNSEntry(String ipAddress, long ttlSeconds) {
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class Problem3DNSCacheTTL {

    static HashMap<String, DNSEntry> cache = new HashMap<>();

    static int cacheHits = 0;
    static int cacheMisses = 0;

    public static String resolve(String domain) {

        if (cache.containsKey(domain)) {

            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                cacheHits++;
                System.out.println("Cache HIT for " + domain);
                return entry.ipAddress;
            } else {
                System.out.println("Cache EXPIRED for " + domain);
                cache.remove(domain);
            }
        }

        cacheMisses++;

        // Simulate upstream DNS lookup
        String newIP = "172.217." + new Random().nextInt(255) + "." + new Random().nextInt(255);

        System.out.println("Cache MISS for " + domain + " → Query upstream");

        cache.put(domain, new DNSEntry(newIP, 10)); // TTL 10 seconds

        return newIP;
    }

    public static void getCacheStats() {

        int total = cacheHits + cacheMisses;

        double hitRate = total == 0 ? 0 : ((double) cacheHits / total) * 100;

        System.out.println("\nCache Statistics:");
        System.out.println("Hits: " + cacheHits);
        System.out.println("Misses: " + cacheMisses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Resolving google.com → " + resolve("google.com"));

        Thread.sleep(2000);

        System.out.println("Resolving google.com → " + resolve("google.com"));

        Thread.sleep(11000); // wait for TTL expiration

        System.out.println("Resolving google.com → " + resolve("google.com"));

        getCacheStats();
    }
}