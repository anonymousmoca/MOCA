package MOCO.Learner;

import MOCO.API;
import MOCO.Edge;
import MOCO.Node;
import VirtualDevice.CoffeeMachine;
import graph.Graph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static MOCO.Utils.generateDotFile;
import static MOCO.Utils.saveEdgesToFile;

public class CoffeeMachine_Model {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Map<String, Set<String>> stateTransitions;
    private static final int MAX_MODE = 20;

    public CoffeeMachine_Model() {
        stateTransitions = new HashMap<>();
    }

    private static List<API> getAllPossibleAPIs() {
        List<API> apis = new ArrayList<>();
        apis.add(new API("turnOn", true, false));
        apis.add(new API("turnOff", true, false));
        apis.add(new API("addWater", false, false));
        apis.add(new API("addCoffeeBean", false, false));
        apis.add(new API("addMilk", false, false));
        apis.add(new API("placeCup", true, true));
        apis.add(new API("fetchCoffee", true, true));
        for (int i = 1; i <= 3; i++) {
            String apiName = "brewCoffee_" + i;
            apis.add(new API(apiName, false, false));
        }

        return apis;
    }

    private static String executeApi(String api, CoffeeMachine device){
        switch (api) {
            case "turnOn":
                return device.turnOn().equals("success") ? device.toString() : "skip";
            case "turnOff":
                return device.turnOff().equals("success") ? device.toString() : "skip";
            case "addWater":
                return device.addWater().equals("success") ? device.toString() : "skip";
            case "addCoffeeBean":
                return device.addCoffeeBean().equals("success") ? device.toString() : "skip";
            case "addMilk":
                return device.addMilk().equals("success") ? device.toString() : "skip";
            case "placeCup":
                return device.placeCup().equals("success") ? device.toString() : "skip";
            case "fetchCoffee":
                return device.fetchCoffee().equals("success") ? device.toString() : "skip";
            case "brewCoffee_1":
                return device.brewCoffee(1).equals("success") ? device.toString() : "skip";
            case "brewCoffee_2":
                return device.brewCoffee(2).equals("success") ? device.toString() : "skip";
            case "brewCoffee_3":
                return device.brewCoffee(3).equals("success") ? device.toString() : "skip";
            default:
                return device.toString();
        }
    }

    private static void generateGraph(Set<Edge> edges) {
        CoffeeMachine device = new CoffeeMachine();
        String initState = device.toString();
        String initSystemState = device.toSystemStateString();
        stateTransitions.put(initSystemState, new HashSet<>());

        exploreState(initState, initSystemState, edges);
    }

    private static void exploreState(String state, String systemState, Set<Edge> edges) {
        List<API> apis = getAllPossibleAPIs();
        for (API api : apis) {
            CoffeeMachine currentCM = CoffeeMachine.fromString(state);
            String nextState = executeApi(api.getName(), currentCM);
            if (!nextState.equals("skip")){
                CoffeeMachine nextCM = CoffeeMachine.fromString(nextState);
                String nextSystemState = nextCM.toSystemStateString();
                Edge edge = new Edge(systemState, nextSystemState, api.toString());
                edges.add(edge);

                int temp = 0;
                while (systemState.equals(nextSystemState)) {
                    if (state.equals(nextState)) {
//                    LOGGER.info("Api: " + api);
//                    LOGGER.info("Current: " + state);
//                    LOGGER.info("Next: " + nextState);
//                    LOGGER.info("State is the same, breaking.");
                        break;
                    }else {
                        String source = nextSystemState;
                        String _nextState = executeApi(api.getName(), nextCM);
                        nextCM = CoffeeMachine.fromString(_nextState);
                        String target = nextCM.toSystemStateString();
                        Edge _edge = new Edge(source, target, api.toString());
                        edges.add(_edge);
                        temp++;
                        if (temp>MAX_MODE) {
//                        LOGGER.info("[MAX] State is the same, breaking.");
                            break;
                        }
                    }
                }

                if (!stateTransitions.containsKey(nextSystemState)) {
                    stateTransitions.put(nextSystemState, new HashSet<>());
                    exploreState(nextCM.toString(), nextSystemState, edges);
                }

                stateTransitions.get(systemState).add(api.getName());
            }
        }
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime(); // start time
        Graph<Object, Object> userGraph = new Graph<>(true,true,false);

        Set<Edge> edges = new HashSet<>();
        int[] sizeHistory = new int[20];
        int historyIndex = 0;
        Set<String> nodesSet = new TreeSet<>();
        Set<String> nodeOrderSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        Map<String, String> nodeNumbers = new HashMap<>();
        int sumEdgeLast;

        Set<Edge> final_Edges = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            Set<Edge> _temp = new HashSet<>();
            CoffeeMachine_Model test = new CoffeeMachine_Model();
            test.generateGraph(_temp);
            edges.addAll(_temp);
        }

        int iteration = 0;
        for (int i = 101; i < 9999; i++) {
            LOGGER.info("Iteration: " + i);
            Set<Edge> _temp = new HashSet<>();
            CoffeeMachine_Model test = new CoffeeMachine_Model();
            test.generateGraph(_temp);
            edges.addAll(_temp);

            for (Edge edge: edges){
                String source = edge.getSource();
                String target = edge.getTarget();
                nodesSet.add(source);
                nodesSet.add(target);
            }

            nodeOrderSet.addAll(nodesSet);

            int nodeCount = 0;
            for (String node : nodeOrderSet) {
                String nodeNumber = "S" + nodeCount;
                nodeNumbers.put(node, nodeNumber);
                nodeCount++;
            }

            int sumEdge = 0;
            for (String node : nodeNumbers.keySet()) {
                String nodeNumber = nodeNumbers.get(node);
//                LOGGER.info("Node: " + nodeNumber);
//                LOGGER.info("State: " + node);
//
//                LOGGER.info("OutEdges:");
                Set<String> visitedApis = new HashSet<>();
                int temp = 0;
                for (Edge edge : edges) {
                    if (edge.getSource().equals(node)) {
                        String target = edge.getTarget();
                        String api = edge.getApi();
                        String targetAndApi = target + " " + api;
                        if (!visitedApis.contains(targetAndApi)) {
//                            LOGGER.info("- " + nodeNumbers.get(target) + ", API = \"" + api + "\" + " + " " + target);
                            visitedApis.add(targetAndApi);
                            temp++;
                        }
                    }
                }
//                System.out.println();
                sumEdge += temp;
            }

            // 输出节点总数
            LOGGER.info("Node Count: " + nodeCount);
            LOGGER.info("Sum Edge: " + sumEdge);

            sizeHistory[historyIndex] = sumEdge;
            historyIndex = (historyIndex + 1) % 20;
            LOGGER.info("[History]: " + Arrays.toString(sizeHistory));
            LOGGER.info("------------------------");
//            LOGGER.info("[Current]: " + edges.size());

            if (checkSizeConsistency(sizeHistory)) {
                break;
            }
            iteration = i+1;
        }

        LOGGER.info("\n\n\n\n------------------------");
        LOGGER.info("[Finished generating graph]");
        LOGGER.info("[Total]: " + edges.size());

        // Construct Dot Graph
        Map<String, String> nodeMap = new HashMap<>();
        Set<String> _nodesSet = new TreeSet<>();
        Set<String> _nodeOrderSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        Set<Edge> dotSet = new HashSet<>();
        for (Edge edge : edges){
            String source = edge.getSource();
            String target = edge.getTarget();
            _nodesSet.add(source);
            _nodesSet.add(target);
        }

        _nodeOrderSet.addAll(nodesSet);

        // 统计节点数量
        int _nodeCount = 0;
        for (String node : _nodeOrderSet) {
            String nodeNumber = "S" + _nodeCount;
            nodeMap.put(node, nodeNumber);
            _nodeCount++;
        }

        for (String node : nodeMap.keySet()) {
            String nodeNumber = nodeMap.get(node);
            LOGGER.info("Node: " + nodeNumber);
            LOGGER.info("State: " + node);

            LOGGER.info("OutEdges:");
            Set<String> visitedApis = new HashSet<>();
            for (Edge edge : edges) {
                if (edge.getSource().equals(node)) {
                    String target = edge.getTarget();
                    String api = edge.getApi();
                    String targetAndApi = target + " " + api;
                    if (!visitedApis.contains(targetAndApi)) {
                        LOGGER.info("- " + nodeMap.get(target) + ", API = \"" + api + "\" + " + " " + target);
                        userGraph.setEdge(new Node(nodeNumber, node).toString(), new Node(nodeMap.get(target), target).toString(), api, api);
                        dotSet.add(new Edge(nodeNumber, nodeMap.get(target), api));
                        visitedApis.add(targetAndApi);
                    }
                }
            }
            System.out.println();
        }

        long endTime = System.nanoTime(); // end time
        long elapsedTime = endTime - startTime; // calculate elapsed time

        double seconds = (double) elapsedTime / 1000000000.0; // convert to seconds

        LOGGER.info("Program ran for " + seconds + " seconds.");

        LOGGER.info("Node number: " + nodeMap.size()+", Edge number: "+dotSet.size()+", Iteration: "+iteration);

        String dotFile = "src/main/java/MOCO/modelFiles/coffeeMachine_output.dot";
        generateDotFile(dotSet, nodeMap.size(), dotFile);

        LOGGER.info("=========== Graph Show ==============");
        Collection<graph.Edge> edge = userGraph.getEdges();
        String fileName = "src/main/java/MOCO/modelFiles/coffeeMachine.json";
        saveEdgesToFile(edge, fileName);

    }

    private static boolean checkSizeConsistency(int[] sizeHistory) {
        if (sizeHistory[0] == 0) {
            return false;
        }

        int size = sizeHistory[0];
        for (int i = 1; i < sizeHistory.length; i++) {
            if (sizeHistory[i] != size) {
                return false;
            }
        }

        return true;
    }

}
