import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Day1 {
    static int startingValue = 50;
    static int numOfWheelValues = 100;
    static int currentValue = startingValue;
    static int password = 0;

    public static void main(String[] args) throws Exception {
        File fileObject = new File("../../resources/Day-1/input.txt");
        try (Scanner scanner = new Scanner(fileObject)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // if (method1(line))
                // password++;
                password += method2(line);
            }
            System.out.println(password);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    private static int method2(String line) {
        int cipherValue = Integer.parseInt(line.substring(1));
        int numZeroPasses = 0;
        if (line.startsWith("R")) {
            numZeroPasses = (currentValue + cipherValue) / numOfWheelValues;
            currentValue = Math.floorMod(currentValue + cipherValue, numOfWheelValues);
        } else {
            if (currentValue - cipherValue <= 0 && currentValue != 0) {
                numZeroPasses += 1;
            }
            numZeroPasses += Math.abs(currentValue - cipherValue) / numOfWheelValues;
            currentValue = Math.floorMod(currentValue - cipherValue, numOfWheelValues);
        }

        return numZeroPasses;
    }

    private static boolean method1(String line) {
        int cipherValue = Integer.parseInt(line.substring(1));
        if (line.startsWith("R")) {
            currentValue = Math.floorMod(currentValue + cipherValue, numOfWheelValues);
        } else {
            currentValue = Math.floorMod(currentValue - cipherValue, numOfWheelValues);
        }
        if (currentValue == 0) {
            return true;
        }
        return false;
    }
}
