import java.util.*;

class TokenBucket {

    int tokens;
    int maxTokens;
    long lastRefillTime;
    int refillRate; // tokens per hour

    TokenBucket(int maxTokens, int refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.tokens = maxTokens;
        this.lastRefillTime = System.currentTimeMillis();
    }

    void refill() {

        long now = System.currentTimeMillis();
        long elapsed = (now - lastRefillTime) / 1000; // seconds

        int tokensToAdd = (int) (elapsed * refillRate / 3600);

        if (tokensToAdd > 0) {
            tokens = Math.min(maxTokens, tokens + tokensToAdd);
            lastRefillTime = now;
        }
    }

    boolean allowRequest() {

        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }

        return false;
    }
}

public class Problem6RateLimiter {

    static HashMap<String, TokenBucket> clients = new HashMap<>();

    static int LIMIT = 5; // max requests
    static int REFILL_RATE = 5; // per hour

    public static String checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId,
                new TokenBucket(LIMIT, REFILL_RATE));

        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {

            return "Allowed (" + bucket.tokens + " requests remaining)";
        } else {

            return "Denied (rate limit exceeded)";
        }
    }

    public static void main(String[] args) {

        String client = "abc123";

        for (int i = 0; i < 7; i++) {

            System.out.println(checkRateLimit(client));
        }
    }
}