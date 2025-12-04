import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.regex.*;

public class Day3 {
    static long password = 0;
    static int passwordLength = 12;

    public static void main(String[] args) throws Exception {
        File fileObject = new File("../../resources/Day-3/input.txt");
        try (Scanner scanner = new Scanner(fileObject)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                password += getHighestVoltage2(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        System.out.println(password);
    }

    private static int getHighestVoltage(String bank) {
        int firstNumberIndex = 0;
        int firstNumber = 0;
        int secondNumber = 0;

        for (int i = bank.length() - 2; i >= 0; i--) {
            if (Integer.parseInt("" + bank.charAt(i)) >= firstNumber) {
                firstNumber = Integer.parseInt("" + bank.charAt(i));
                firstNumberIndex = i;
            }
        }

        for (int j = firstNumberIndex + 1; j < bank.length(); j++) {
            if (Integer.parseInt("" + bank.charAt(j)) >= secondNumber) {
                secondNumber = Integer.parseInt("" + bank.charAt(j));
            }
        }

        int highestVolage = Integer.parseInt("" + firstNumber + secondNumber);
        return highestVolage;
    }

    private static long getHighestVoltage2(String bank) {
        int startingIndex = 0;
        int bankLength = bank.length();
        int batteriesToFill = passwordLength;
        int[] chosenBatteryVoltage = new int[passwordLength];
        int currentLargestVolt = 0;
        int currentLargestIndex = 0;

        for (int i = 0; i < passwordLength; i++) {
            for (int j = startingIndex; j <= ((bankLength - currentLargestIndex) - batteriesToFill)
                    + currentLargestIndex; j++) {
                if (Integer.parseInt("" + bank.charAt(j)) > currentLargestVolt) {
                    currentLargestVolt = Integer.parseInt("" + bank.charAt(j));
                    currentLargestIndex = j;
                }
            }
            startingIndex = currentLargestIndex + 1;
            batteriesToFill--;
            chosenBatteryVoltage[i] = currentLargestVolt;
            currentLargestVolt = 0;
        }
        String finalSelection = "";
        for (int volt : chosenBatteryVoltage) {
            finalSelection += "" + volt;
        }
        long highestVolage = Long.parseLong(finalSelection);
        return highestVolage;
    }

}