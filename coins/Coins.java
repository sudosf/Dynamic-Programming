import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class Coins {

    /* Use dynamic programming to count the number of ways of making change.
     * 
     * DP Approach: Bottom Up Memoization.
     * Time Complexity: O(n^2)
    */
    public static int getCombinations(int amount, int[] coins) {
        // store all possible combinations
        int[] combinations = new int[amount + 1];
        combinations[0] = 1;

        for (int c : coins) {
            for (int i = 1; i < combinations.length; i++) {
                if (i >= c) {
                    combinations[i] += combinations[i - c];
                }
            }
        }

        return combinations[amount];
    }

    public static ArrayList<String> ReadFile(String filename) {
        ArrayList<String> list = new ArrayList<>();

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                list.add(data);
                // System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: could not open/read file.");
            e.printStackTrace();
        }

        return list;
    }
    
    public static void main(String[] args) {
        int[] coins = {1, 2, 5, 10, 20, 50, 100, 200};

        ArrayList<String> coinData = ReadFile("juice.csv"); // get file data

        // traverse file data, ignore first line
        for (int i = 1; i < coinData.size(); i++) {
            String temp = coinData.get(i);
            String[] receipt = temp.split(";", 3);

            int bill = Integer.parseInt(receipt[0]);
            int tip = Integer.parseInt(receipt[1]);
            int amt_received = Integer.parseInt(receipt[2]);

            System.out.println("\nInput:\t R" + bill + " (bill)");
            System.out.println("\t R" + tip + " (tip amount)");
            System.out.println("\t R" + amt_received + " (amount received)");

            int change = amt_received - (bill + tip);
            int totalCombinations = getCombinations(change, coins);

            System.out.println("Output:\t The final amount to pay is R" + (bill + tip) );
            System.out.println("\t The customer requires R" + change + " change");
            System.out.println("\t The number of ways to make change for R" + change + " is " + totalCombinations);

        }

        // System.out.println( getCombinations(6, coins) );
        // System.out.println(coinData.get(1));
    }
}