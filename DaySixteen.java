import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DaySixteen {
    public static void main(String[] args) throws IOException {

//        "Valve HH has flow rate=22; tunnel leads to valve GG"
        SolutionSixteen solution = new SolutionSixteen();
//        solution.initVertexes();
        solution.parseInput();
        solution.findDistances();
//        solution.printDistances();
//
//        ValveNode currentNode = solution.start;
        int remainingTime = 30;
        Set<ValveNode> opened = new HashSet<>(solution.vertexes);
        opened.removeIf(e -> e.getFlowRate() == 0);
        int sumPressure = 0;
        int currentFlowRate = 0;
//        while (remainingTime > 0) {
//            // find next node
//            ValveNode next = solution.calcucate(
//                    currentNode, solution.findMaxFlowRateNode(opened), opened, remainingTime);
//            System.out.println(next);
//            // if it exists
//            if (next != null) {
//                int spentTime = currentNode.getDistances().get(next) + 1;
//                if (spentTime > remainingTime) {
//                    sumPressure += remainingTime * currentFlowRate;
//                    break;
//                }
//                sumPressure += spentTime * currentFlowRate;
//                remainingTime -= spentTime;
//                currentFlowRate += next.getValue();
//                currentNode = next;
//                opened.remove(next);
//            } else {
////                System.out.println(sumPressure);
//                sumPressure += remainingTime * currentFlowRate;
//                break;
//            }
//            System.out.println(remainingTime);
//        }
//        System.out.println(sumPressure + " " + currentFlowRate);


//        ValveNode currentNode = solution.start;
//        while (remainingTime > 0) {
//            ValveNode next = solution.magicDady(currentNode, opened, remainingTime);
//            if (next != null) {
//                int spentTime = currentNode.getDistances().get(next) + 1;
//                sumPressure += spentTime * currentFlowRate;
//                remainingTime -= spentTime;
//                currentFlowRate += next.getValue();
//                currentNode = next;
//                opened.remove(next);
//            } else {
//                sumPressure += remainingTime * currentFlowRate;
//                break;
//            }
//        }
//        System.out.println(sumPressure + " " + currentFlowRate);
        List<ValveNode> nonZero =  new ArrayList<>();
        for (ValveNode vertex : solution.vertexes) {
            if (vertex.getFlowRate() != 0) {
                nonZero.add(vertex);
            }
        }

//        System.out.println(solution.getMaxPressurePart1(solution.start, 0, nonZero));
        List<ValveNode> closed = new LinkedList<>(nonZero);
//        System.out.println(solution.findMaximumPressure(solution.start, 0, closed));

        System.out.println(solution.findMaximumPressure2(
                new Path(0, 0, solution.start, opened),
                new HashMap<>()));
        System.out.println(solution.findMaximumPressureWithElephant(
                new Path(0, 0, solution.start, opened),
                new Path(0, 0, solution.start, opened),
                new HashMap<>()));

        // a b c d e f g
        // bc bd be bf bg
        // cd ce cf cg
        // de df dg
        // ef eg
        // fg
        // ---- ok let's try bc ----
        // de df dg
        // ef eg
        // fg
        // ----- go de ----
        // fg
        //

        // b c
        // one to b one to c
        //

        // a (b c) (d e) (f g)
        // a (b c) (d e) (g f)
        // a (b c) (e d) (g f)
        // a (b c) (e d) (f g)
//    }
////
//    public static void main(String[] args) {
//
//
//        Map<String, ValveNode> map = new HashMap<>();
//        String currentLine = "Valve EG has flow rate=21; tunnels lead to valves WZ, OF, ZP, QD";
//        String[] split = currentLine.split("; ");
//        String valveName = split[0].substring(6, 8);
//        String flowRateString = split[0].substring(23);
//        int flowRate = Integer.parseInt(flowRateString);
//        String neighboursString = split[1].substring(23);
//        String[] neighboursArrayString = neighboursString.split(", ");
//
//
//
    }

}

class SolutionSixteen {

    Set<ValveNode> vertexes = new HashSet<>();
    ValveNode start;
    String inputFileName = "test";
//    String inputFileName = "input16";  // 1382 too low // 2087 right
    Map<String, ValveNode> nodes = new HashMap<>();


    int getMaxPressurePart1(ValveNode nodeAt, int minute, List<ValveNode> nonZeroValves) {
        if (minute >= 30) {
            return 0;
        }
        int maxFlow = 0;
        Map<ValveNode, Integer> distances = nodeAt.getDistances();
        for (int i = 0; i < nonZeroValves.size(); i++) {
            ValveNode nonZeroValve = nonZeroValves.get(i);
            if (nodeAt.equals(nonZeroValve)) {
                continue;
            }
            int dist = distances.get(nonZeroValve);
            int minAdj = minute + dist + 1;
            nonZeroValves.remove(i);
            int nodeFlow = ((30 - Math.min(minAdj, 30)) * nonZeroValve.getFlowRate());
            int flow = nodeFlow + getMaxPressurePart1(nonZeroValve, minAdj, nonZeroValves);
            nonZeroValves.add(i, nonZeroValve);
            if(flow > maxFlow) {
                maxFlow = flow;
            }
        }
        return maxFlow;
    }

    ValveNode magicDady(final ValveNode node, Set<ValveNode> openedNodes, int remainingTime) {

        Map<ValveNode, Integer> distances = node.getDistances();
        ValveNode maxNode = null;
        int maxPressure = 0;
        for (ValveNode openedNode : openedNodes) {

            if (remainingTime < distances.get(openedNode) + 1) {
                continue;
            }

            int pressureOverEnd = (remainingTime - distances.get(openedNode) - 1) * openedNode.getFlowRate();
            if (maxPressure < pressureOverEnd) {
                maxPressure = pressureOverEnd;
                maxNode = openedNode;
            }

        }
        System.out.println(maxNode);
        return maxNode;
    }

    void initVertexes(){
        ValveNode AA = new ValveNode(0, "AA");  // start
        start = AA;
        ValveNode BB = new ValveNode(13, "BB");
        ValveNode CC = new ValveNode(2, "CC");
        ValveNode DD = new ValveNode(20, "DD");
        ValveNode EE = new ValveNode(3, "EE");
        ValveNode FF = new ValveNode(0, "FF");
        ValveNode GG = new ValveNode(0, "GG");
        ValveNode HH = new ValveNode(22, "HH");
        ValveNode II = new ValveNode(0, "II");
        ValveNode JJ = new ValveNode(21, "JJ");
        vertexes.add(AA);
        vertexes.add(BB);
        vertexes.add(CC);
        vertexes.add(DD);
        vertexes.add(EE);
        vertexes.add(FF);
        vertexes.add(GG);
        vertexes.add(HH);
        vertexes.add(II);
        vertexes.add(JJ);

        AA.getNeighbours().add(BB);
        AA.getNeighbours().add(DD);
        AA.getNeighbours().add(II);
        BB.getNeighbours().add(AA);
        BB.getNeighbours().add(CC);
        CC.getNeighbours().add(BB);
        CC.getNeighbours().add(DD);
        DD.getNeighbours().add(AA);
        DD.getNeighbours().add(CC);
        DD.getNeighbours().add(EE);
        EE.getNeighbours().add(DD);
        EE.getNeighbours().add(FF);
        FF.getNeighbours().add(EE);
        FF.getNeighbours().add(GG);
        GG.getNeighbours().add(FF);
        GG.getNeighbours().add(HH);
        HH.getNeighbours().add(GG);
        II.getNeighbours().add(AA);
        II.getNeighbours().add(JJ);
        JJ.getNeighbours().add(II);

    }

    /*
       fill nodes
     */
    void parseInput() throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(inputFileName));
        List<String> lines = bf.lines().toList();
//        Map<String, ValveNode> nodes = new HashMap<>();
        // add all nodes
        for (String line : lines) {
            String[] split = line.split("; ");
            String valveName = split[0].substring(6, 8);
            String flowRateString = split[0].substring(23);
            int flowRate = Integer.parseInt(flowRateString);
            nodes.put(valveName, new ValveNode(flowRate, valveName));
        }
        // add neighbours
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
            ValveNode node = nodes.get(valveName);
            for (String neighbour : neighboursArrayString) {
                node.getNeighbours().add(nodes.get(neighbour));
            }
        }
        vertexes.addAll(nodes.values());
        start = nodes.get("AA");
    }

    void printGraph(ValveNode node, Set<ValveNode> visited) {
        System.out.println(node);
        visited.add(node);
        for (ValveNode neighbour : node.getNeighbours()) {
            if (visited.contains(neighbour)) {
                continue;
            }
            printGraph(neighbour, visited);
        }
    }

    ValveNode findMaxFlowRateNode(Set<ValveNode> vertexes) {
        ValveNode maxFlowRateNode = new ValveNode(-1, "tmp");
        for (ValveNode vertex : vertexes) {
            if (vertex.getFlowRate() > maxFlowRateNode.getFlowRate()) {
                maxFlowRateNode = vertex;
            }
        }
//        System.out.println(maxFlowRateNode);
        return maxFlowRateNode;
    }

    ValveNode calcucate(final ValveNode node, ValveNode maxFlowRateNode, Set<ValveNode> openedValveNodes,
                        int remainingTime) {
        // (d_2 - d_1 + 1) * fR_1 < fR_2
        // JJ 21 3
        // BB 13 2
        //
//        boolean removed = openedValveNodes.removeIf(e -> node.getDistances().get(e) >= remainingTime);
//        if (removed) {
//            System.out.println("removed");
//        }
        if (openedValveNodes.isEmpty()) {
            return null;
        }
        int distanceMaxFR = node.getDistances().get(maxFlowRateNode);
        int maxFR = maxFlowRateNode.getFlowRate();
        ValveNode maxFRNode = maxFlowRateNode;
        ValveNode lastMaxFRNode = null;

        Set<ValveNode> candidates = new HashSet<>(openedValveNodes);
        candidates.remove(node);
        candidates.remove(maxFlowRateNode);
        candidates.removeIf(candidate -> candidate.getFlowRate() == 0);
//        candidates.removeIf(candidate -> node.getDistances().get(candidate) >= remainingTime);
//        if (candidates.isEmpty()) {
//            return null;
//            System.out.println("em");
//        }

//        while (lastMaxFRNode != maxFRNode) {
        while (true) {
            System.out.println(candidates);
            for (ValveNode vertex : candidates) {
//                if (node.getDistances().get(vertex) + 1 >= remainingTime) {
//                    System.out.println(node.getDistances().get(vertex) );
//                    System.out.println(vertex);
//                    System.out.println(remainingTime);
//                    System.out.println("vot tak vot");
//                    continue;
//                }
                int difference = (distanceMaxFR - node.getDistances().get(vertex) + 1) * vertex.getFlowRate();
//                int difference = (remainingTime - node.getDistances().get(vertex) + 1) * vertex.getValue();
                if (difference > maxFR) {
                    maxFRNode = vertex;
                    maxFR = difference;
                } else if (difference == maxFR && !vertex.equals(lastMaxFRNode)) {
                    System.out.println("aaaaaaaaaaaaAAAAAAAAAA");
                }
            }
            if (lastMaxFRNode != maxFRNode) {
                lastMaxFRNode = maxFRNode;
                distanceMaxFR = node.getDistances().get(maxFRNode);
                maxFR = maxFRNode.getFlowRate();
            } else {
                break;
            }
        }
//        System.out.println(maxFRNode);
        return maxFRNode;
    }


//    void distanceDaddy(ValveNode node, Set<ValveNode> visited, Map<ValveNode, Integer> distances, Integer distance) {
//        visited.add(node);
//        System.out.println(node + " " + distance);
//        for (ValveNode neighbour : node.getNeighbours()) {
//            if (!distances.containsKey(node)) {
//                distances.put(node, distance);
//            } else {
//                if (distance < distances.get(node)) {
//                    distances.put(node, distance);
//                }
//            }
//            if (visited.contains(neighbour)) {
//                continue;
//            }
//            distanceDaddy(neighbour, visited, distances, distance + 1);
//        }
//        System.out.println(distances);
//    }

    Map<ValveNode, Integer> dijkstra(ValveNode node) {
        Map<ValveNode, Integer> distances = new HashMap<>();
        for (ValveNode vertex : vertexes) {
            if (vertex.equals(node)) {
                distances.put(vertex, 0);
            } else {
                distances.put(vertex, Integer.MAX_VALUE - 2);
            }
        }
        PriorityQueue<Map.Entry<ValveNode, Integer>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());
        queue.addAll(distances.entrySet());
        while (!queue.isEmpty()) {
            var vertex = queue.remove();
            for (ValveNode neighbour : vertex.getKey().getNeighbours()) {
                Integer distance = distances.get(vertex.getKey()) + 1;
                if (distance < distances.get(neighbour)) {
                    queue.remove(Map.entry(neighbour, distances.get(neighbour)));
                    queue.add(Map.entry(neighbour, distance));
                    distances.put(neighbour, distance);
                }
            }
        }
        return distances;
    }

    void findDistances() {
        for (ValveNode vertex : vertexes) {
            vertex.setDistances(dijkstra(vertex));
        }
    }

    void printDistances() {
        for (ValveNode vertex : vertexes) {
            System.out.println(vertex.getName() + " " + vertex.getDistances());
        }
    }

//    void answer1(ValveNode node) {
//        for (ValveNode neighbour : node.getNeighbours()) {
//
//            Path path = new Path(1, 0, neighbour);
//            if (neighbour.getFlowRate() == 0) {
//
//            } else {
//                path.getEnabled().add(neighbour);
//                path.setMinutes(path.getMinutes() + 1);
//                path.setPressure(path.getPressure() + neighbour.getFlowRate());
//            }
//        }
//
//        int minutes = 0;
//        int pressure = 0;
//        Set<ValveNode> enabled = new HashSet<>();
//        Iterator<ValveNode> iterator = node.getNeighbours().iterator();
//        PriorityQueue<Path> paths = new PriorityQueue<>();
//
//        ValveNode next = iterator.next();
//        minutes += 1;
//        if (next.getFlowRate() > 0) {
//            // add two path
//            Path enabledPath = new Path(minutes + 1, next.getFlowRate(), next);
//            enabledPath.getEnabled().add(next);
//
//            Path skippedPath = new Path(minutes, 0, next);
//
//        }
//
//
//    }

    void brilliant() {
        // current node
        // pick the closest node with maximum value
        // calculate minutes and pressure
        // update current node
        // save this path into priority queue
        //

        // fundamentals:
        // ------------
        // in begin this story we have only one path (AA)
        // but later maybe more than one path (AA, AA-DD, AA-JJ, ...)
        // each path have set of visited nodes and reference to current node
        // and also have such parameters as current time and current pressure or flow rate
        // we can store these paths into priority queue (maximize flow rate and minimize lost time)

        // pick path from queue
        // for current node find closest

        // all what we need for solution:
        //    1. map with minimal distances between all non-zero nodes
        //    2. map with values (pressure) of all nodes
        //    3. ???
        //    4. PROFIT!!!

        // p(t) = fR * t - N * (H + 1)
        // fR = 33
        // t = 6
        // N = 2
        // H = 3
        // 33 * 6 - 2 * (3 + 1)

        // p(t) =
        // AA --1--> DD --2--> BB -> ...
        // 0 * (1 + 1) = 0 ; fR = 20
        // 20 * (2 + 1) = 60; fR = 20 + 13 = 33
        // p = fR * (distance + 1)
        // DD --3---> JJ
        // 20 * (3 + 1) = 80; fR = 20 + 21 = 41
        // BB --3--> JJ; fR = 20 + 13 + 21 = 54
        // 33 * (3 + 1) = 132

        // p(t) = sum(fR(t) * (distance + 1))

        // t(0): fR = a;
        // t(2): fR = b; (1+1) 20
        // t(6): fR = c; (5+1) 22

        // t(N): fR = X;
        // t(distance + 1): fR > b

        // !!!
        // fR_X > fR_b * (distance_X - distance_b)

        // from DD to JJ = 3
        // fR_JJ = 21
        // fR_BB = 13
        // distance_JJ = 3
        // distance_BB = 2

        // t(0) -> t(7) sum = 22;
        // t(3) -> t(7) sum = 20 * 4 = 80;

        //


        //


        //





        // currentFlowRate = 0
        // candidateFlowRate = 22
        // candidateDistance = 5 min
        // anotherFlowRate = 20
        // anotherDistance = 2 min
        // 5 - 2 = 3
        // 3 * 20 = 60 > 22
        // 61 - 5 min
        // 20 - 2 min
        // 0  1  2  3  4  5  6
        // 0  0  X  20 20 20 20
        // 0  0  0  0  0  X  81

        // 0  0  X  20 20 20 20 20! 101 101
        // 0  0  0  0  0  X  81 81! 101 101

        // fR_1 = 20; d_1 = 2
        // fR_2 = 81; d_2 = 5
        // (d_2 - d_1 + 1) * fR_1 < fR_2
        // (5 - 2 + 1) * 20 < 81 ?
        // 4 * 20 < 81
        // 80 < 81 ; True!

        // 5 - 2 + 1 = 4
        // 4 * 20 = 80
        // 1 * 81 = 81

        // distances = [ 1,  2,  4,  6]
        // fRates    = [10, 13, 29, 14]

        // find max fR: 29
        // distance: 4

        // for all other
        // find
        // (4 - distance + 1) * fR
        // [40, 39, 29, -..]

        // [1, 1, 2, 2, 3, 4, 5, 19]
        // [3, 4, 12, 11, 4, ,...]
        // if (distance > distance_R && fR < fR_R) skip;
        // else if (==) ??;

















    }

    int findMaximumPressure(ValveNode node, int minute, List<ValveNode> closedNodes) {

        if (minute >= 30) {
            return 0;
        }
        int maxPressure = 0;
        for (int i = 0; i < closedNodes.size(); i++) {
            // open node
            ValveNode closedNode = closedNodes.remove(i);

            int minuteAfterOpenNode = minute + node.getDistances().get(closedNode) + 1;
            int pressure = (30 - Math.min(30, minuteAfterOpenNode)) * closedNode.getFlowRate()
                    + findMaximumPressure(closedNode, minuteAfterOpenNode, closedNodes);

            // close node
            closedNodes.add(i, closedNode);

            if (pressure > maxPressure) {
                maxPressure = pressure;
            }
        }
        return maxPressure;
    }

    int findMaximumPressure2(Path path, Map<Path, Integer> memo) {

        if (path.getMinutes() >= 30) {
            return 0;
        }
        if (memo.containsKey(path)) {
//            if (memo.get(path) > 656) {
//                System.out.println(path.getMinutes() + " " +
//                        path.getEnabled() + " " + memo.get(path));
//            }
            return memo.get(path);
        }

        int maxPressure = 0;
        for (var closedNode : path.getEnabled()) {
            int minuteAfterOpenNode = path.getMinutes() + path.getCurrentNode().getDistances().get(closedNode) + 1;
            Set<ValveNode> closedNodes = new HashSet<>(path.getEnabled());
            closedNodes.remove(closedNode);
            Path newPath = new Path(minuteAfterOpenNode, maxPressure, closedNode, closedNodes);
            int pressure = (30 - Math.min(30, minuteAfterOpenNode)) * closedNode.getFlowRate()
                    + findMaximumPressure2(newPath, memo);
            maxPressure = Math.max(pressure, maxPressure);
            memo.put(path, maxPressure);
        }
        return maxPressure;
    }

    int findMaximumPressureWithElephant(Path myPath, Path elephantPath, Map<Path, Integer> memo) {
        if (myPath.getMinutes() >= 26) {
            return 0;
        }
        if (elephantPath.getMinutes() >= 26) {
            return 0;
        }
//        if (memo.containsKey(myPath)) {
//            return memo.get(myPath);
//        } else if (memo.containsKey(elephantPath)) {
//            return memo.get(elephantPath);
//        }
        int maxPressure = 0;
        int maxPressure2 = 0;

        for (var myNode : myPath.getEnabled()) {
            if (!elephantPath.getEnabled().contains(myNode)) {
                continue;
            }
            int minuteAfterOpenNode = myPath.getMinutes() + myPath.getCurrentNode().getDistances().get(myNode) + 1;
            Set<ValveNode> closedNodes = new HashSet<>(myPath.getEnabled());
            closedNodes.remove(myNode);
            Path myNewPath = new Path(minuteAfterOpenNode, maxPressure, myNode, closedNodes);
            int pressure = (26 - Math.min(26, minuteAfterOpenNode)) * myNode.getFlowRate()
                    + findMaximumPressureWithElephant(myNewPath, elephantPath, memo);
            maxPressure = Math.max(pressure, maxPressure);
            memo.put(myPath, maxPressure);
        }

        for (var elephantNode : elephantPath.getEnabled()) {
            if (!myPath.getEnabled().contains(elephantNode)) {
                continue;
            }
            int minuteAfterOpenNode = elephantPath.getMinutes() + elephantPath.getCurrentNode().getDistances().get(elephantNode) + 1;
            Set<ValveNode> closedNodes = new HashSet<>(elephantPath.getEnabled());
            closedNodes.remove(elephantNode);
            Path elephantNewPath = new Path(minuteAfterOpenNode, maxPressure, elephantNode, closedNodes);
            int pressure = (26 - Math.min(26, minuteAfterOpenNode)) * elephantNode.getFlowRate()
                    + findMaximumPressureWithElephant(myPath, elephantNewPath, memo);
            maxPressure = Math.max(pressure, maxPressure);
            memo.put(elephantPath, maxPressure);
        }

        return maxPressure;
    }


    // void f(closedNodes, openedNodes, minuteMy, minuteEl, )

    // currentNode
    // for available nodes :
    //   available.remove(node);
    //   opened.add(node);
    //   pressure = node * minute + dist + 1;
    //   find max
    //

}

class ValveNode {

    private Set<ValveNode> neighbours = new HashSet<>();
    private int flowRate;
    private String name;
    private Map<ValveNode, Integer> distances;


    public ValveNode(int value, String name) {
        this.flowRate = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDistances(Map<ValveNode, Integer> distances) {
        this.distances = distances;
    }

    public Map<ValveNode, Integer> getDistances() {
        return distances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValveNode valveNode = (ValveNode) o;

        if (flowRate != valveNode.flowRate) return false;
        return Objects.equals(name, valveNode.name);
    }

    @Override
    public int hashCode() {
        int result = flowRate;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public int getFlowRate() {
        return flowRate;
    }

    Set<ValveNode> getNeighbours() {
        return neighbours;
    }

    @Override
    public String toString() {
        return "("+name + "," + flowRate +")" ;
    }
}

class Path {
    private int minutes;
    private int pressure;
    private Set<ValveNode> enabled;
    private ValveNode currentNode;
    private int flowRate;

    public Path(int minutes, int pressure, ValveNode currentNode, Set<ValveNode> closedNodes) {
        this.minutes = minutes;
        this.pressure = pressure;
        this.currentNode = currentNode;
        this.enabled = closedNodes;
    }

    void update() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path path = (Path) o;

        if (minutes != path.minutes) return false;
        if (!enabled.equals(path.enabled)) return false;
        return currentNode.equals(path.currentNode);
    }

    @Override
    public int hashCode() {
        int result = minutes;
        result = 31 * result + enabled.hashCode();
        result = 31 * result + currentNode.hashCode();
        return result;
    }

    public int getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(int flowRate) {
        this.flowRate = flowRate;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public Set<ValveNode> getEnabled() {
        return enabled;
    }

    public void setEnabled(Set<ValveNode> enabled) {
        this.enabled = enabled;
    }

    public ValveNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(ValveNode currentNode) {
        this.currentNode = currentNode;
    }



}
