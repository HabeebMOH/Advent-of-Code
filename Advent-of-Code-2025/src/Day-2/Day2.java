import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.regex.*;

public class Day2 {
    static String[] ranges;
    static long password = 0;

    public static void main(String[] args) throws Exception {
        File fileObject = new File("../../resources/Day-2/input.txt");
        try (Scanner scanner = new Scanner(fileObject)) {
            while (scanner.hasNextLine()) {
                String file = scanner.nextLine();
                ranges = file.split(",");
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        for (String range : ranges) {
            password += checkInvalidIds2(range);
        }
        System.out.println(password);
    }

    private static long checkInvalidIds(String range) {
        long toReturn = 0;
        long startRange = Long.parseLong(range.split("-")[0]);
        long endRange = Long.parseLong(range.split("-")[1]);
        for (long i = startRange; i <= endRange; i++) {
            String temp = String.valueOf(i);
            int tempLength = temp.length();
            if (tempLength % 2 == 0) {
                if (temp.substring(0, tempLength / 2).equals(temp.substring(tempLength / 2))) {
                    toReturn += Long.parseLong(temp);
                }
            }
        }
        return toReturn;
    }

    private static long checkInvalidIds2(String range) {
        long toReturn = 0;
        long startRange = Long.parseLong(range.split("-")[0]);
        long endRange = Long.parseLong(range.split("-")[1]);
        String patternString = "^(\\d+)\\1+$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher;
        for (long i = startRange; i <= endRange; i++) {
            String temp = String.valueOf(i);
            matcher = pattern.matcher(temp);
            if (matcher.find()) {
                toReturn += Long.parseLong(temp);
            }
        }
        return toReturn;
    }
}
