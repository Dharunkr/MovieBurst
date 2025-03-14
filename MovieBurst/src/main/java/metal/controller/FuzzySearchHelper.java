package metal.controller;
import java.util.*;
public class FuzzySearchHelper {
	// Predefined TMDB genres
    private static final Map<String, String> genreMap = new HashMap<>();

    static {
        genreMap.put("action", "28");
        genreMap.put("comedy", "35");
        genreMap.put("drama", "18");
        genreMap.put("thriller", "53");
        genreMap.put("romance", "10749");
        genreMap.put("sci-fi", "878");
        genreMap.put("horror", "27");
        genreMap.put("fantasy", "14");
        // Add more genres if needed
    }

    // Function to calculate Levenshtein Distance
    private static int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), 
                        dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1)
                    );
                }
            }
        }
        return dp[a.length()][b.length()];
    }

    // Find the closest matching genre
    public static String getClosestGenre(String input) {
        int minDistance = Integer.MAX_VALUE;
        String closestGenre = input;

        for (String genre : genreMap.keySet()) {
            int distance = levenshteinDistance(input.toLowerCase(), genre.toLowerCase());
            if (distance < minDistance) {
                minDistance = distance;
                closestGenre = genre;
            }
        }
        return closestGenre;
    }

    public static String getGenreId(String genre) {
        return genreMap.getOrDefault(genre.toLowerCase(), "");
    }
}

