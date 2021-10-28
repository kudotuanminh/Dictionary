package Algorithm;

public class Levenshtein {
    public static int distance(String source, String target) {
        if (source.length() == 0) {
            return target.length();
        }
        if (target.length() == 0) {
            return source.length();
        }
        int dp[][] = new int[source.length() + 1][target.length() + 1];
        for (int i = 0; i <= source.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= target.length(); j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= source.length(); i++) {
            char sch = source.charAt(i - 1);
            for (int j = 1; j <= target.length(); j++) {
                char tch = target.charAt(j - 1);
                int cost = sch == tch ? 0 : 1;
                dp[i][j] = minimum(dp[i - 1][j] + 1, // deletion
                        dp[i][j - 1] + 1, // insertion
                        dp[i - 1][j - 1] + cost); // substitution
            }
        }
        return dp[source.length()][target.length()];
    }

    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }
}
