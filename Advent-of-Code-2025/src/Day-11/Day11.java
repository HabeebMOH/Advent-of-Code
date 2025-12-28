import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Credits to jsharp9009/AdventOfCode2025 on gitHub for part 2 inspiration using memoization

public class Day11 {
    static Map<String, List<String>> puzzle = new HashMap<>();
    static List<List<String>> pathsToOut = new ArrayList<>();
    static String start;
    static String end = "out";
    static boolean part1 = false;

    public static void main(String[] args) throws Exception {
        File fileObject = new File("../../resources/Day-11/input.txt");
        try (Scanner scanner = new Scanner(fileObject)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(": ");
                String node = parts[0];
                List<String> outputs = Arrays.asList(parts[1].split(" "));
                puzzle.put(node, outputs);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        if (part1) {
            start = "you";

        } else {
            start = "svr";
        }

        if (part1) {
            System.out.println(dfs2(start, "out", false, false));
        } else {
            System.out.println(dfs2("svr", "out", false, false));
        }
    }

    static Map<String, Long> memo = new HashMap<>();

    private static long dfs2(String current, String end, boolean seenFft, boolean seenDac) {
        // If reached the end
        if (current.equals(end)) {
            if (part1)
                return 1;
            return (seenFft && seenDac) ? 1 : 0;
        }

        // Build memo key for this state
        String key = current + "|" + seenFft + "|" + seenDac;
        if (memo.containsKey(key))
            return memo.get(key);

        long count = 0;
        for (String next : puzzle.getOrDefault(current, List.of())) {
            count += dfs2(
                    next,
                    end,
                    seenFft || next.equals("fft"),
                    seenDac || next.equals("dac"));
        }

        memo.put(key, count);
        return count;
    }
}
