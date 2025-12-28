import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day11 {
    static long password = 0;
    static Map<String, List<String>> puzzle = new HashMap<>();
    static List<List<String>> pathsToOut = new ArrayList<>();
    static String start;
    static String end = "out";
    static boolean part1 = true;

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
            dfs2(start, "out", false, false);
            System.out.println(password);
        } else {
            // part 2
            dfs2("svr", "fft", false, false);
            long svrToFft = password;
            password = 0;
            dfs2("fft", "dac", false, false);
            long fftToDac = password;
            password = 0;
            dfs2("dac", "out", false, false);
            long dacToEnd = password;
            password = dacToEnd * fftToDac * svrToFft;
        }
    }

    private static void dfs2(String current, String end, boolean seenFft, boolean seenDac) {
        if (current.equals(end)) {
            if (part1) {
                password++;
            } else {
                if (seenFft && seenDac)
                    password++;
                return;
            }
        }
        for (String next : puzzle.getOrDefault(current, List.of())) {
            dfs2(next, end,
                    seenFft || next.equals("fft"),
                    seenDac || next.equals("dac"));

        }
    }
}
