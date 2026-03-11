import java.util.*;

public class Problem4PlagiarismDetector {

    // n-gram index: ngram -> set of documents
    static HashMap<String, Set<String>> ngramIndex = new HashMap<>();

    static int N = 3; // 3-grams (groups of 3 words)

    public static List<String> generateNGrams(String text) {

        String[] words = text.toLowerCase().split("\\s+");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }

            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }

    public static void indexDocument(String docId, String text) {

        List<String> grams = generateNGrams(text);

        for (String gram : grams) {

            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docId);
        }
    }

    public static void analyzeDocument(String docId, String text) {

        List<String> grams = generateNGrams(text);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : grams) {

            if (ngramIndex.containsKey(gram)) {

                for (String existingDoc : ngramIndex.get(gram)) {

                    matchCount.put(existingDoc,
                            matchCount.getOrDefault(existingDoc, 0) + 1);
                }
            }
        }

        System.out.println("Analyzing document: " + docId);

        for (Map.Entry<String, Integer> entry : matchCount.entrySet()) {

            int matches = entry.getValue();
            double similarity = (matches * 100.0) / grams.size();

            System.out.println("Match with " + entry.getKey()
                    + " → " + similarity + "% similarity");
        }
    }

    public static void main(String[] args) {

        String doc1 = "machine learning improves search engine results";
        String doc2 = "deep learning improves search engine performance";
        String doc3 = "machine learning improves recommendation systems";

        indexDocument("doc1", doc1);
        indexDocument("doc2", doc2);

        analyzeDocument("doc3", doc3);
    }
}