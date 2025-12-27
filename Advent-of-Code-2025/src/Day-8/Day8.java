import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Day8 {

    static class Junction {
        int id;
        long x;
        long y;
        long z;
        int circuit = -1;

        public Junction(int id, long x, long y, long z) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public String toString() {
            return "Junction (" + id + "): (" + x + ", " + y + ", " + z + ")";
        }

        public long distanceTo(Junction other) {
            return (long) Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
        }
    }

    static class DistanceTriple {
        long distance;
        Junction j1;
        Junction j2;

        DistanceTriple(long dist, Junction j1, Junction j2) {
            distance = dist;
            this.j1 = j1;
            this.j2 = j2;
        }

        public String toString() {
            return "(" + distance + ", " + j1.id + ", " + j2.id + ")";
        }
    }

    static class SortTriple implements Comparator<DistanceTriple> {

        @Override
        public int compare(DistanceTriple o1, DistanceTriple o2) {
            DistanceTriple a = o1;
            DistanceTriple b = o2;

            if (a.distance < b.distance)
                return -1;
            if (b.distance < a.distance)
                return 1;
            return 0;
        }

    }

    static long password = 0;
    static int idCounter = 1;
    static ArrayList<Junction> junctions = new ArrayList<Junction>();
    static ArrayList<DistanceTriple> distances = new ArrayList<DistanceTriple>();
    static HashMap<Integer, HashSet<Integer>> circuits = new HashMap<>();
    static int closestToConnect = 1000;
    static int circuitIdCounter = 1;
    static Junction lastAddedJ1;
    static Junction lastAddedJ2;
    static boolean part1 = false;

    public static void main(String[] args) throws Exception {
        File fileObject = new File("../../resources/Day-8/input.txt");
        try (Scanner scanner = new Scanner(fileObject)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] coords = line.split(",");
                junctions.add(new Junction(idCounter, Long.parseLong(coords[0]), Long.parseLong(coords[1]),
                        Long.parseLong(coords[2])));
                idCounter++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        calculateDistances();
        if (part1) {
            buildCircuits();
            password = solve();
        } else {
            buildCircuits2();
            password = lastAddedJ1.x * lastAddedJ2.x;
        }

        System.out.println(password);
    }

    private static long solve() {
        List<Integer> topThreeSizes = circuits.values().stream()
                .map(Set::size)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .collect(Collectors.toList());
        long product = 1;
        for (Integer integer : topThreeSizes) {
            product *= integer;
        }
        return product;
    }

    private static boolean connectJunctions(int index) {
        boolean allConnected = false;
        Junction j1 = distances.get(index).j1;
        Junction j2 = distances.get(index).j2;
        if (j1.circuit == -1 && j2.circuit == -1) {
            j1.circuit = circuitIdCounter;
            j2.circuit = circuitIdCounter;
            circuits.putIfAbsent(circuitIdCounter, new HashSet<>());
            circuits.get(circuitIdCounter).add(j1.id);
            circuits.get(circuitIdCounter).add(j2.id);
            if (circuits.get(circuitIdCounter).size() == junctions.size()) {
                allConnected = true;
                lastAddedJ1 = j1;
                lastAddedJ2 = j2;
            }
            circuitIdCounter++;
        } else if (j1.circuit != -1 && j2.circuit != -1) {
            if (j1.circuit != j2.circuit) {
                int minID = Math.min(j1.circuit, j2.circuit);
                int maxID = Math.max(j1.circuit, j2.circuit);
                circuits.get(minID).addAll(circuits.get(j1.circuit));
                circuits.get(minID).addAll(circuits.get(j2.circuit));
                j1.circuit = minID;
                j2.circuit = minID;
                for (Integer id : circuits.get(maxID)) {
                    junctions.get(id - 1).circuit = minID;
                }
                if (circuits.get(minID).size() == junctions.size()) {
                    allConnected = true;
                    lastAddedJ1 = j1;
                    lastAddedJ2 = j2;
                }
                circuits.remove(maxID);
            }
        } else {
            int maxID = Math.max(j1.circuit, j2.circuit);
            j1.circuit = maxID;
            j2.circuit = maxID;
            circuits.get(maxID).add(j1.id);
            circuits.get(maxID).add(j2.id);
            if (circuits.get(maxID).size() == junctions.size()) {
                allConnected = true;
                lastAddedJ1 = j1;
                lastAddedJ2 = j2;
            }
        }
        return allConnected;
    }

    private static void buildCircuits2() {
        int counter = 0;
        boolean done = false;
        while (!done) {
            done = connectJunctions(counter);
            counter++;
        }
    }

    private static void buildCircuits() {
        for (int i = 0; i < closestToConnect; i++) {
            connectJunctions(i);
        }
    }

    private static void calculateDistances() {
        for (int i = 0; i < junctions.size() - 1; i++) {
            for (int j = i + 1; j < junctions.size(); j++) {
                Junction j1 = junctions.get(i);
                Junction j2 = junctions.get(j);
                long dist = j1.distanceTo(j2);
                distances.add(new DistanceTriple(dist, j1, j2));
            }
        }
        Collections.sort(distances, new SortTriple());
    }
}
