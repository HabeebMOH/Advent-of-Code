import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class Day4 {
    static int password = 0;
    static ArrayList<String> rollRows = new ArrayList<>();
    static char[][] input;

    public static void main(String[] args) throws Exception {
        File fileObject = new File("../../resources/Day-4/input.txt");
        try (Scanner scanner = new Scanner(fileObject)) {
            while (scanner.hasNextLine()) {
                rollRows.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        input = stringListToChar2d(rollRows);
        password += countAndRemoveAccessibleRolls(input);
        System.out.println(password);
    }

    private static char[][] stringListToChar2d(ArrayList<String> rollRows2) {
        char[][] toReturn = new char[rollRows2.size()][rollRows2.get(0).length()];
        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = rollRows2.get(i).toCharArray();
        }
        return toReturn;
    }

    private static int countAccessibleRolls(char[][] input2) {
        int output = 0;
        for (int i = 0; i < input2.length; i++) {
            for (int j = 0; j < input2[i].length; j++) {
                int rollsSurrounding = totalSurroundingRolls(input2, i, j);
                if (rollsSurrounding < 4) {
                    output++;
                }
            }
        }
        return output;
    }

    private static int countAndRemoveAccessibleRolls(char[][] input2) {
        int output = 0;
        boolean rollRemoved = true;
        while (rollRemoved) {
            rollRemoved = false;
            for (int i = 0; i < input2.length; i++) {
                for (int j = 0; j < input2[i].length; j++) {
                    int rollsSurrounding = totalSurroundingRolls(input2, i, j);
                    if (rollsSurrounding < 4) {
                        output++;
                        rollRemoved = true;
                        input2[i][j] = 'x';
                    }
                }
            }
        }
        return output;
    }

    private static int totalSurroundingRolls(char[][] input2, int i, int j) {
        int total = 0;
        if (input2[i][j] == 'x') {
            input2[i][j] = '.';
        }

        if (input2[i][j] == '.') {
            return 4;
        }

        total = rollsAbove(input2, i, j) + rollsBelow(input2, i, j) + rollsSame(input2, i, j);

        return total;
    }

    private static int rollsSame(char[][] input2, int i, int j) {
        int rollsSame = 0;
        if (j - 1 >= 0 && (input2[i][j - 1] == '@')) {
            rollsSame++;
        }
        if (j + 1 < input2[i].length && (input2[i][j + 1] == '@'))
            rollsSame++;
        return rollsSame;
    }

    private static int rollsBelow(char[][] input2, int i, int j) {
        int rollsBelow = 0;
        if (i + 1 >= input2.length) {
            return 0;
        }
        if (j - 1 >= 0 && (input2[i + 1][j - 1] == '@')) {
            rollsBelow++;
        }

        if (input2[i + 1][j] == '@')
            rollsBelow++;

        if (j + 1 < input2[i + 1].length && (input2[i + 1][j + 1] == '@')) {
            rollsBelow++;
        }
        return rollsBelow;
    }

    private static int rollsAbove(char[][] input2, int i, int j) {
        int rollsAbove = 0;
        if (i - 1 < 0) {
            return 0;
        }
        if (j - 1 >= 0 && (input2[i - 1][j - 1] == '@')) {
            rollsAbove++;
        }

        if (input2[i - 1][j] == '@')
            rollsAbove++;

        if (j + 1 < input2[i - 1].length && (input2[i - 1][j + 1] == '@')) {
            rollsAbove++;
        }
        return rollsAbove;
    }

}