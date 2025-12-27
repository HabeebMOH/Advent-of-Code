import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files

public class Day6 {

    static long password = 0;
    static boolean part2 = true;
    static ArrayList<ArrayList<String>> equationNumbers = new ArrayList<ArrayList<String>>();
    static ArrayList<String> operations = new ArrayList<String>();
    static ArrayList<char[]> equationNumbers2 = new ArrayList<char[]>();
    static ArrayList<String> operations2 = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        File fileObject = new File("../../resources/Day-6/input.txt");
        try (Scanner scanner = new Scanner(fileObject)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!part2) {
                    parseLine1(line);

                } else {
                    parseLine2(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        password = solveWeirdMath();
        System.out.println(password);
    }

    private static void parseLine1(String line) {
        ArrayList<String> lineList = new ArrayList<>(Arrays.asList(line.trim().split("\\s+")));
        if (lineList.get(0).equals("+") || lineList.get(0).equals("*")) {
            operations.addAll(lineList);
        } else {
            equationNumbers.add(new ArrayList<>(Arrays.asList(line.split("(?<=\\d)\\s"))));
        }
    }

    private static void parseLine2(String line) {
        if (line.charAt(0) == '+' || line.charAt(0) == '*') {
            operations2.addAll(new ArrayList<>(Arrays.asList(line.trim().split("\\s+"))));
        } else {
            equationNumbers2.add(line.toCharArray());
        }
    }

    private static long solveWeirdMath() {
        long finalValue = 0;

        if (!part2) {
            finalValue = solvePart1();
        } else {
            finalValue = solvePart2();
        }
        return finalValue;
    }

    private static long solvePart2() {
        long solution = 0;
        int operationCounter = 0;
        long interSolution = 0;
        for (int col = 0; col < equationNumbers2.get(0).length; col++) {
            boolean isColumnEmpty = true;
            String digit = "";
            for (int row = 0; row < equationNumbers2.size(); row++) {
                if (equationNumbers2.get(row)[col] != ' ') {
                    isColumnEmpty = false;
                    digit += equationNumbers2.get(row)[col];
                }
            }
            if (isColumnEmpty) {
                operationCounter++;
                solution += interSolution;
                interSolution = 0;
            } else {
                if (operations2.get(operationCounter).equals("*")) {
                    if (interSolution == 0) {
                        interSolution = 1;
                    }
                    interSolution *= Long.parseLong(digit);
                } else {
                    interSolution += Long.parseLong(digit);
                }
            }
        }
        solution += interSolution;
        return solution;
    }

    private static long solvePart1() {
        long solution = 0;
        for (int col = 0; col < operations2.size(); col++) {
            if (operations2.get(col).equals("*")) {
                long columnMult = 1;
                for (int row = 0; row < equationNumbers2.size(); row++) {
                    columnMult *= Long.parseLong(equationNumbers.get(row).get(col));
                }
                solution += columnMult;
            } else {
                long columnAddition = 0;
                for (int row = 0; row < equationNumbers2.size(); row++) {
                    columnAddition += Long.parseLong(equationNumbers.get(row).get(col));
                }
                solution += columnAddition;
            }
        }
        return solution;
    }

}
