package MOCO.Learner;

import MOCO.API;
import MOCO.Edge;
import MOCO.Node;
import VirtualDevice.SmartVacuum;
import graph.Graph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static MOCO.Utils.generateDotFile;
import static MOCO.Utils.saveEdgesToFile;

public class SmartVacuum_Model {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Map<String, Set<String>> stateTransitions;

    public SmartVacuum_Model() {
        stateTransitions = new HashMap<>();
    }

    private static List<API> getAllPossibleAPIs() {
        List<API> apis = new ArrayList<>();
        apis.add(new API("turnOn", true, false));
        apis.add(new API("turnOff", true, false));
        apis.add(new API("startCleaning", true, false));
        apis.add(new API("stopCleaning", true, false));
        apis.add(new API("setModeAuto", true, false));
        apis.add(new API("setModeSpot", true, false));
        apis.add(new API("setModeEdge", true, false));
        return apis;
    }

    private static String executeApi(String api, SmartVacuum smartVacuum) {
        switch (api) {
            case "turnOn":
                return smartVacuum.turnOn().equals("success") ? smartVacuum.toString() : "skip";
            case "turnOff":
                return smartVacuum.turnOff().equals("success") ? smartVacuum.toString() : "skip";
            case "startCleaning":
                return smartVacuum.startCleaning().equals("success") ? smartVacuum.toString() : "skip";
            case "stopCleaning":
                return smartVacuum.stopCleaning().equals("success") ? smartVacuum.toString() : "skip";
            case "setModeAuto":
                return smartVacuum.setMode("auto").equals("success") ? smartVacuum.toString() : "skip";
            case "setModeSpot":
                return smartVacuum.setMode("spot").equals("success") ? smartVacuum.toString() : "skip";
            case "setModeEdge":
                return smartVacuum.setMode("edge").equals("success") ? smartVacuum.toString() : "skip";
            default:
                return smartVacuum.toString();
        }
    }

    private static void generateGraph(Set<Edge> edges) {
        SmartVacuum device = new SmartVacuum();
        String initState = device.toString();
        String initSystemState = device.toSystemString();
        stateTransitions.put(initSystemState, new HashSet<>());

        exploreState(initState, initSystemState, edges);
    }

    private static void exploreState(String state, String systemState, Set<Edge> edges) {
        List<API> apis = getAllPossibleAPIs();

        for (API api : apis) {
            SmartVacuum currentDevice = SmartVacuum.fromString(state);
            String nextState = executeApi(api.getName(), currentDevice);
            if (!nextState.equals("skip")) {
                SmartVacuum nextDevice = SmartVacuum.fromString(nextState);
                String nextSystemState = nextDevice.toSystemString();
                Edge edge = new Edge(systemState, nextSystemState, api.toString());
                edges.add(edge);

                if (!stateTransitions.containsKey(nextSystemState)) {
                    stateTransitions.put(nextSystemState, new HashSet<>());
                    exploreState(nextDevice.toString(), nextSystemState, edges);
                }

                stateTransitions.get(systemState).add(api.getName());
            }
        }
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime(); // 获取开始时间
        Graph<Object, Object> userGraph = new Graph<>(true, true, false);
        Set<Edge> edges = new HashSet<>();
        int[] sizeHistory = new int[20];
        int historyIndex = 0;
        Set<String> nodesSet = new TreeSet<>();
        Set<String> nodeOrderSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        // Create node numbers map
        Map<String, String> nodeNumbers = new HashMap<>();

        int iteration = 0;
        for (int i = 0; i < 2; i++) {
            LOGGER.info("Iteration: " + iteration++);
            Set<Edge> _temp = new HashSet<>();
            SmartVacuum_Model test = new SmartVacuum_Model();
            test.generateGraph(_temp);
            edges.addAll(_temp);
        }

        for (Edge edge : edges) {
            String source = edge.getSource();
            String target = edge.getTarget();
            nodesSet.add(source);
            nodesSet.add(target);
        }

        nodeOrderSet.addAll(nodesSet);

        // Count node numbers
        int nodeCount = 0;
        for (String node : nodeOrderSet) {
            String nodeNumber = "S" + nodeCount;
            nodeNumbers.put(node, nodeNumber);
            nodeCount++;
        }

        // print out edges and apis
        int sumEdge = 0;
        for (String node : nodeNumbers.keySet()) {
            Set<String> visitedApis = new HashSet<>();
            int temp = 0;
            for (Edge edge : edges) {
                if (edge.getSource().equals(node)) {
                    String target = edge.getTarget();
                    String api = edge.getApi();
                    String targetAndApi = target + " " + api;
                    if (!visitedApis.contains(targetAndApi)) {
                        visitedApis.add(targetAndApi);
                        temp++;
                    }
                }
            }
            sumEdge += temp;
        }

        LOGGER.info("Node Count: " + nodeCount);
        LOGGER.info("Sum Edge: " + sumEdge);

        sizeHistory[historyIndex] = sumEdge;
        historyIndex = (historyIndex + 1) % 20;
        LOGGER.info("[History]: " + Arrays.toString(sizeHistory));
        LOGGER.info("------------------------");

        LOGGER.info("\n\n\n\n------------------------");
        LOGGER.info("[Finished generating graph]");
        LOGGER.info("[Total]: " + edges.size());

        // Construct Dot Graph
        Map<String, String> nodeMap = new HashMap<>();
        Set<String> _nodesSet = new TreeSet<>();
        Set<String> _nodeOrderSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        Set<Edge> dotSet = new HashSet<>();
        for (Edge edge : edges) {
            String source = edge.getSource();
            String target = edge.getTarget();
            _nodesSet.add(source);
            _nodesSet.add(target);
        }

        _nodeOrderSet.addAll(nodesSet);

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
        long elapsedTime = endTime - startTime; // running time

        double seconds = (double) elapsedTime / 1000000000.0; // transfer to seconds

        LOGGER.info("Program ran for " + seconds + " seconds.");

        LOGGER.info("Node Count: " + nodeCount + " Edge Count: " + dotSet.size());

        String dotFile = "src/main/java/MOCO/modelFiles/smartvacuum_output.dot";
        generateDotFile(dotSet, nodeMap.size(), dotFile);

        Collection<graph.Edge> edge = userGraph.getEdges();
        String fileName = "src/main/java/MOCO/modelFiles/smartvacuum.json";
        saveEdgesToFile(edge, fileName);
    }
}
