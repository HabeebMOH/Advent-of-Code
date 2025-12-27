import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.Set;
import java.util.Comparator;

public class Day5 {
    static class Range {
        long start;
        long end;

        public Range(long s, long e) {
            this.start = s;
            this.end = e;
        }

        public String toString() {
            return "(" + start + ", " + end + ")";
        }
    }

    static class SortRange implements Comparator<Range> {

        @Override
        public int compare(Range o1, Range o2) {
            Range a = o1;
            Range b = o2;

            if (a.start < b.start)
                return -1;
            if (b.start < a.start)
                return 1;
            return 0;
        }

    }

    static long password = 0;

    static ArrayList<Range> ranges = new ArrayList<Range>();
    static ArrayList<Long> ingredients = new ArrayList<Long>();

    public static void main(String[] args) throws Exception {
        boolean foundIngredients = false;
        File fileObject = new File("../../resources/Day-5/input.txt");
        try (Scanner scanner = new Scanner(fileObject)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("")) {
                    foundIngredients = true;
                    continue;
                }

                if (foundIngredients) {
                    ingredients.add(Long.parseLong(line));
                } else {
                    String start = line.split("-")[0];
                    String end = line.split("-")[1];
                    Range range = new Range(Long.parseLong(start), Long.parseLong(end));
                    ranges.add(range);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        // password = numberOfFreshIngredients(ranges, ingredients);
        sortTuples();
        password = numberOfFreshIDs(ranges);
        System.out.println(password);
    }

    private static void sortTuples() {
        Collections.sort(ranges, new SortRange());
    }

    private static long numberOfFreshIDs(ArrayList<Day5.Range> ranges) {
        long totalFreshIDs = 0;
        ArrayList<Range> mergedRanges = new ArrayList<Range>();
        for (Range range : ranges) {
            if (mergedRanges.size() == 0) {
                mergedRanges.add(range);
            }
            if (range.start <= mergedRanges.get(mergedRanges.size() - 1).end) {
                long newStart = Math.min(range.start, mergedRanges.get(mergedRanges.size() - 1).start);
                long newEnd = Math.max(range.end, mergedRanges.get(mergedRanges.size() - 1).end);
                mergedRanges.set(mergedRanges.size() - 1, new Range(newStart, newEnd));
            } else {
                mergedRanges.add(range);
            }

        }
        for (Range mergedRange : mergedRanges) {
            totalFreshIDs += mergedRange.end - mergedRange.start + 1;
        }
        return totalFreshIDs;
    }

    private static int numberOfFreshIngredients(ArrayList<Day5.Range> ranges, ArrayList<Long> ingredients) {
        int totalFreshIngredients = 0;
        for (long ingredient : ingredients) {
            if (valueInARange(ingredient, ranges)) {
                totalFreshIngredients++;
            }
        }

        return totalFreshIngredients;
    }

    private static boolean valueInARange(long ingredient, ArrayList<Day5.Range> ranges) {
        for (Range range : ranges) {
            if (range.start <= ingredient && range.end >= ingredient) {
                return true;
            }
        }
        return false;
    }
}
