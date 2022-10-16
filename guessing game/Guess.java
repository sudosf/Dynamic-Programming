import java.util.*;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class Guess {

        // store memoized values
        public static int [][] memoStore;

        static int shortEditDistance(String word1, String word2, int n, int m) {

            // If any String is empty, return the remaining characters of other String
            if (n == 0) return m;
            if (m == 0) return n;

            // To check for memoized values in global array
            if (memoStore[n][m] != -1) return memoStore[n][m];

            // skip for equal characters
            if (word1.charAt(n - 1) == word2.charAt(m - 1)) 
                return memoStore[n][m] = shortEditDistance(word1, word2, n - 1, m - 1);
        
            // If characters are not equal..
            // find the minimum cost out of all 3 operations.
            else {
                int insert, delete, replace; // temp variables

                insert = shortEditDistance(word1, word2, n, m - 1);
                delete = shortEditDistance(word1, word2, n - 1, m);
                replace = shortEditDistance(word1, word2, n - 1, m - 1);

                memoStore[n][m] = 1 + Math.min(insert, Math.min(delete, replace));
                return memoStore[n][m];
            }
        }


        public static void writeToFile(String filename, String data) {

            try {
                FileWriter writer = new FileWriter(filename);
                
                writer.write("Score: " + data);
                writer.close();
                
                System.out.println("\nSuccessfully wrote score to file.");
            } catch (IOException e) {
                System.out.println("Error: could not write to file.");
                e.printStackTrace();
            }
  
        }

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in); 

            int score = 0;
            // allow 10 rounds
            for (int round = 1; round <= 10; round++) {
                String roundName = "animal";
                String answer = "mouse";

                System.out.println("\nRound " + round + ": " + roundName);

                // allow 3 attempts
                for (int attempt = 1; attempt <= 3; attempt++) {
                    System.out.print("\nAnswer " + attempt + ": ");
                    String wordInput = sc.next();

                    int n = answer.length();
                    int m = wordInput.length();
                    
                    memoStore = new int[n + 1][m + 1];
                    for (int i = 0; i < n + 1; i++) {
                        Arrays.fill(memoStore[i], -1);
                    }
                    
                    int distance = shortEditDistance(answer, wordInput, n, m);
                    if( distance != 0) {
                        System.out.println("incorrect -> " + distance);
                    } else {
                        score += 10;
                        System.out.println("correct");
                        break;
                    }
                }
                // System.out.println("Score: " + score);

            }
            writeToFile("user.txt", String.valueOf(score) );
            sc.close(); // close scanner
        }
}
