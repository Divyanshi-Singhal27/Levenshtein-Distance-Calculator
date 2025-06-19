import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class levenshtein1App {

    // Task 1: Basic Edit Distance (Cost = 1 for insert, delete, substitute)
    public static int editDistance(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++)
            for (int j = 0; j <= n; j++) {
                if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;
                else if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                                    Math.min(dp[i - 1][j], dp[i][j - 1]));
            }

        return dp[m][n];
    }
    
    // Task 2: Weighted Edit Distance
    public static int weightedEditDistance(String s1, String s2, int Ci, int Cd, int Cs) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++)
            for (int j = 0; j <= n; j++) {
                if (i == 0)
                    dp[i][j] = j * Ci;
                else if (j == 0)
                    dp[i][j] = i * Cd;
                else if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = Math.min(Ci + dp[i][j - 1],
                                Math.min(Cd + dp[i - 1][j],
                                         Cs + dp[i - 1][j - 1]));
            }

        return dp[m][n];
    }

    // Task 3: Spell Checker
    public static List<String> spellChecker(String input, List<String> dictionary, int Ci, int Cd, int Cs) {
        List<String> result = new ArrayList<>();
        int minDist = Integer.MAX_VALUE;

        for (String word : dictionary) {
            int dist = weightedEditDistance(input, word, Ci, Cd, Cs);
            if (dist < minDist) {
                result.clear();
                result.add(word);
                minDist = dist;
            } else if (dist == minDist) {
                result.add(word);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Task 1
        System.out.println("========= Task 1: Basic Levenshtein Distance =========");
        System.out.print("Enter first string: ");
        String s1 = sc.nextLine();
        System.out.print("Enter second string: ");
        String s2 = sc.nextLine();
        int basicDistance = editDistance(s1, s2);
        System.out.println("Levenshtein Distance: " + basicDistance);

        // Task 2
        System.out.println("\n========= Task 2: Weighted Edit Distance =========");
        System.out.print("Enter first string: ");
        String ws1 = sc.nextLine();
        System.out.print("Enter second string: ");
        String ws2 = sc.nextLine();
        System.out.print("Enter Insertion Cost (Ci): ");
        int Ci = sc.nextInt();
        System.out.print("Enter Deletion Cost (Cd): ");
        int Cd = sc.nextInt();
        System.out.print("Enter Substitution Cost (Cs): ");
        int Cs = sc.nextInt();
        int weightedDistance = weightedEditDistance(ws1, ws2, Ci, Cd, Cs);
        System.out.println("Weighted Edit Distance: " + weightedDistance);
        sc.nextLine(); // Consume newline

        // Task 3
        System.out.println("\n========= Task 3: Spell Checker =========");
        List<String> dictionary = new ArrayList<>();
        System.out.print("Enter number of words in dictionary: ");
        int n = Integer.parseInt(sc.nextLine());
        System.out.println("Enter dictionary words:");
        for (int i = 0; i < n; i++) {
            dictionary.add(sc.nextLine());
        }

        System.out.print("Enter word to check: ");
        String inputWord = sc.nextLine();
        System.out.print("Enter Insertion Cost (Ci): ");
        Ci = sc.nextInt();
        System.out.print("Enter Deletion Cost (Cd): ");
        Cd = sc.nextInt();
        System.out.print("Enter Substitution Cost (Cs): ");
        Cs = sc.nextInt();

        List<String> suggestions = spellChecker(inputWord, dictionary, Ci, Cd, Cs);
        System.out.println("Suggestions: " + suggestions);

        sc.close();
    }
}
