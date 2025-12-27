import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// ONLY PART ONE DONE :((
public class Day9 {

    static long password = 0;
    static ArrayList<String> puzzle = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        File fileObject = new File("../../resources/Day-9/input.txt");
        try (Scanner scanner = new Scanner(fileObject)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
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
        long largestArea = 0;
        for (int i = 0; i < puzzle.size() - 1; i++) {
            for (int j = i + 1; j < puzzle.size(); j++) {
                int[] tile1 = { Integer.parseInt(puzzle.get(i).split(",")[0]),
                        Integer.parseInt(puzzle.get(i).split(",")[1]) };
                int[] tile2 = { Integer.parseInt(puzzle.get(j).split(",")[0]),
                        Integer.parseInt(puzzle.get(j).split(",")[1]) };
                long currentArea = calculateArea(tile1, tile2);
                // System.out.println(tile1[0] + " " + tile1[1] + ", " + tile2[0] + " " +
                // tile2[1]);
                // System.out.println(currentArea);
                // System.out.println();
                if (currentArea > largestArea) {
                    largestArea = currentArea;
                }
            }
        }
        return largestArea;
    }

    private static long calculateArea(int[] tile1, int[] tile2) {
        long width = Math.abs(tile1[0] - tile2[0]) + 1;
        long height = Math.abs(tile1[1] - tile2[1]) + 1;
        return width * height;
    }
}
