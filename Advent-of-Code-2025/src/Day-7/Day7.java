import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day7 {

    static long password = 0;
    static int startIndex = 0;
    static ArrayList<String> puzzle = new ArrayList<String>();
    static boolean part1 = false;
    static long[] previousBeamLocation;

    public static void main(String[] args) throws Exception {
        File fileObject = new File("../../resources/Day-7/input.txt");
        try (Scanner scanner = new Scanner(fileObject)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("S")) {
                    startIndex = line.indexOf("S");
                }
                puzzle.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }

        password = solve();
        System.out.println(password);
    }

    private static long solve() {
        long splitCount = 0;
        long timelinesCount = 0;
        int width = puzzle.get(0).length();
        previousBeamLocation = new long[width];
        previousBeamLocation[startIndex] = 1;
        for (int row = 1; row < puzzle.size() - 1; row++) {
            for (int col = 0; col < width; col++) {
                if (puzzle.get(row).charAt(col) == '^') {
                    if (previousBeamLocation[col] > 0) {
                        splitCount++;
                        previousBeamLocation[col - 1] += previousBeamLocation[col];
                        previousBeamLocation[col + 1] += previousBeamLocation[col];
                        previousBeamLocation[col] = 0;
                    }
                }
            }
        }
        timelinesCount = Arrays.stream(previousBeamLocation).sum();
        if (!part1) {
            return timelinesCount;
        }
        return splitCount;
    }

}
