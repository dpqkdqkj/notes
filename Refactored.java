import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;


public class DaySixteenRefactored {

    public static void main(String[] args) throws IOException {

        String inputFileName = "test";
//        String inputFileName = "input16";
        Solver solver = new Solver(inputFileName);
        System.out.println(solver.solvePart2());

    }
}


class Solver {

    private final int countValves;
    private final int [] flowRates;
    private final int [][] distances;
    private Map<String, Integer> cache = new HashMap<>();

    public Solver(String inputFileName) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(inputFileName));
        List<String> lines = bf.lines().toList();
        // add all nodes
        List<String> valves = new ArrayList<>();
        Map<String, Integer> flowRates = new HashMap<>();
        for (String line : lines) {
            String[] split = line.split("; ");
            String valveName = split[0].substring(6, 8);
            String flowRateString = split[0].substring(23);
            int flowRate = Integer.parseInt(flowRateString);
            flowRates.put(valveName, flowRate);
            valves.add(valveName);
        }
        // add neighbours
        Map<String, List<String>> valveNeighbours = new HashMap<>();
        for (String line : lines) {
            String[] split = line.split("; ");
            String valveName = split[0].substring(6, 8);

            String[] neighboursArrayString;
            if (split[1].contains(",")) {
                String neighboursString = split[1].substring(23);
                neighboursArrayString = neighboursString.split(", ");
            } else {
                String neighboursString = split[1].substring(22);
                neighboursArrayString = new String[]{neighboursString};
            }

            List<String> neigbours = Arrays.stream(neighboursArrayString).toList();
            valveNeighbours.put(valveName, neigbours);
        }
        Map<Map.Entry<String, String>, Integer> distances = new HashMap<>();
        for (var valve : valves) {
            for (var anotherValve : valves) {
                distances.put(Map.entry(valve, anotherValve), 30);
            }
        }
        for (var valve : valves) {
            distances.put(Map.entry(valve, valve), 0);
        }
        for (var valve : valves) {
            List<String> neighbours = valveNeighbours.get(valve);
            for (var neighbour : neighbours) {
                distances.put(Map.entry(valve, neighbour), 1);
            }
        }
        for (String valveMiddle : valves) {
            for (String valveFirst : valves) {
                for (String valveSecond : valves) {
                    int newDistance = distances.get(Map.entry(valveFirst, valveMiddle))
                            + distances.get(Map.entry(valveMiddle, valveSecond));
                    if (distances.get(Map.entry(valveFirst, valveSecond)) > newDistance) {
                        distances.put(Map.entry(valveFirst, valveSecond), newDistance);
                    }
                }
            }
        }
        // Remove valves with zero flow rate but save start position 'AA'
        valves.removeIf(valve -> flowRates.get(valve) == 0 && !valve.equals("AA"));
        Collections.sort(valves);  // sort for convenience
        countValves = valves.size();  // save count of important (non-zero + start) valves
        // Save flow rates into array
        this.flowRates = new int[countValves];
        for (int i = 0; i < countValves; ++i) {
            this.flowRates[i] = flowRates.get(valves.get(i));
        }
        // Save distances into array
        this.distances = new int[countValves][countValves];
        for (int i = 0; i < countValves; ++i) {
            for (int j = 0; j < countValves; ++j) {
                this.distances[i][j] = distances.get(Map.entry(valves.get(i), valves.get(j)));
            }
        }
    }

    int solvePart2() {
        return solvePart22(
                0,
                0, 0, 0,
                IntStream.range(1, countValves).toArray(), new int[0]);
    }

    List<Integer> getCacheArray(int firstNode, int secondNode,
                        int firstMinutes, int secondMinutes, int[] openedNodes) {
        int[] cacheArray = new int[4 + openedNodes.length];
        cacheArray[0] = firstNode;
        cacheArray[1] = secondNode;
        cacheArray[2] = firstMinutes;
        cacheArray[3] = secondMinutes;
        System.arraycopy(openedNodes, 0, cacheArray, 4, openedNodes.length);

        List<Integer> ints = Arrays.stream(cacheArray).boxed().toList();
        return ints;
    }
    String getCacheString(int firstNode, int secondNode,
                          int firstMinutes, int secondMinutes, int[] openedNodes) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstNode);
        stringBuilder.append('/');
        stringBuilder.append(secondNode);
        stringBuilder.append('/');
        stringBuilder.append(firstMinutes);
        stringBuilder.append('/');
        stringBuilder.append(secondMinutes);
        stringBuilder.append('/');
        for (var node : openedNodes) {
            stringBuilder.append(node);
            stringBuilder.append('/');
        }
        return stringBuilder.toString();
    }

    int solvePart22(int firstNode, int secondNode,
                    int firstMinutes, int secondMinutes,
                    int[] closedNodes, int[] openedNodes) {

        if (closedNodes.length == 0) {
            return 0;
        }

        if (firstMinutes >= 26 && secondMinutes >= 26) {
            return 0;
        }

        String cacheString = getCacheString(firstNode, secondNode, firstMinutes, secondMinutes, openedNodes);

        if (cache.get(cacheString) != null) {
            return cache.get(cacheString);
        }

        int maxPressure = 0;

        // first person
        for (int closedNode : closedNodes) {
            int minuteAfterOpenNode = firstMinutes + distances[firstNode][closedNode] + 1;
            int[] newClosed = Arrays.stream(closedNodes).filter(node -> node != closedNode).toArray();
            int[] newOpened = Arrays.copyOf(openedNodes, openedNodes.length + 1);
            newOpened[openedNodes.length] = closedNode;

            int pressure = (26 - Math.min(26, minuteAfterOpenNode)) * flowRates[closedNode]
                    + solvePart22(closedNode, secondNode, minuteAfterOpenNode, secondMinutes, newClosed, newOpened);
            maxPressure = Math.max(pressure, maxPressure);
        }
        // second person
        for (int closedNode : closedNodes) {
            int minuteAfterOpenNode = secondMinutes + distances[secondNode][closedNode] + 1;
            int[] newClosed = Arrays.stream(closedNodes).filter(node -> node != closedNode).toArray();
            int[] newOpened = Arrays.copyOf(openedNodes, openedNodes.length + 1);
            newOpened[openedNodes.length] = closedNode;
            int pressure = (26 - Math.min(26, minuteAfterOpenNode)) * flowRates[closedNode]
                    + solvePart22(firstNode, closedNode, firstMinutes, minuteAfterOpenNode, newClosed, newOpened);
            maxPressure = Math.max(pressure, maxPressure);
        }

        cache.put(cacheString, maxPressure);

        return maxPressure;
    }

}
