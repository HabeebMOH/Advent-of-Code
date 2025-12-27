import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day10 {

    static class State {
        String mask; // the current bitmask of lights
        int presses; // how many presses so far

        State(String mask, int presses) {
            this.mask = mask;
            this.presses = presses;
        }
    }

    static long password = 0;
    static ArrayList<String> puzzle = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        File fileObject = new File("../../resources/Day-10/input.txt");
        try (Scanner scanner = new Scanner(fileObject)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                puzzle.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        for (String string : puzzle) {
            password += solve(string);
        }
        System.out.println(password);
    }

    private static long solve(String line) {
        String[] lineParts = line.split(" ");
        String[] splitButtons = Arrays.copyOfRange(lineParts, 1, lineParts.length - 1);
        String joltage = lineParts[lineParts.length - 1];
        String targetBinary = lineParts[0].substring(1, lineParts[0].length() - 1).replace('.', '0').replace('#', '1');
        List<List<Integer>> buttons = new ArrayList<>();
        for (final String splitButton : splitButtons) {
            final String[] splitWiring = splitButton.substring(1, splitButton.length() - 1).split(",");
            buttons.add(Arrays.stream(splitWiring)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList()));
        }
        String start = "0".repeat(targetBinary.length());
        Queue<State> queue = new ArrayDeque<>();
        HashSet<String> visited = new HashSet<>();

        queue.add(new State(start, 0));
        visited.add(start);

        // --- BFS Loop ---
        while (!queue.isEmpty()) {
            State current = queue.remove();

            if (current.mask.equals(targetBinary)) {
                return current.presses;
            }

            for (List<Integer> button : buttons) {
                String nextMask = applyButton(current.mask, button);

                if (!visited.contains(nextMask)) {
                    visited.add(nextMask);
                    queue.add(new State(nextMask, current.presses + 1));
                }
            }
        }
        return -1;

    }

    public static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }

    private static String applyButton(String mask, List<Integer> button) {
        String buttonBinary = buttonsToBinaryString(button, mask);
        int buttonDecimal = Integer.parseInt(buttonBinary, 2);
        int maskDecimal = Integer.parseInt(mask, 2);
        int result = buttonDecimal ^ maskDecimal;
        String toReturn = Integer.toBinaryString(result);
        return padLeftZeros(toReturn, mask.length());
    }

    private static String buttonsToBinaryString(List<Integer> button, String target) {
        StringBuilder string = new StringBuilder("0".repeat(target.length()));
        for (Integer integer : button) {
            string.setCharAt(integer, '1');
        }
        return string.toString();
    }
}
