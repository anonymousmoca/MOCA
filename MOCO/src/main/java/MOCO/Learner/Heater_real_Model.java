package MOCO.Learner;

import MOCO.Edge;
import MOCO.Utils;
import MOCO.Node;
import RealDevice.deviceTwins4Real.HeaterStatus;
import graph.Graph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

import static MOCO.Utils.generateDotFile;
import static MOCO.Utils.saveEdgesToFile;

public class Heater_real_Model {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Map<String, Set<String>> stateTransitions;
    private static int MAX_RETRIES = 5;

    public Heater_real_Model() { stateTransitions = new HashMap<>(); }

    private static List<String> getAllPossibleAPIs() {
        List<String> apis = new ArrayList<>();
        apis.add("turnOn");
        apis.add("turnOff");
//        apis.add("setBuzzerOn");
//        apis.add("setBuzzerOff");
        apis.add("setTargetTemp");
        return apis;
    }

    private static String executeApi(String api, ProcessBuilder pb) throws IOException, InterruptedException {
        switch (api) {
            case "turnOn":
                return Utils.executePythonScript(pb, "src/main/java/RealDevice/heater/on.py");
            case "turnOff":
                return Utils.executePythonScript(pb, "src/main/java/RealDevice/heater/off.py");
            case "setBuzzerOn":
                return Utils.executePythonScript(pb, "src/main/java/RealDevice/heater/setBuzzer.py", "True");
            case "setBuzzerOff":
                return Utils.executePythonScript(pb, "src/main/java/RealDevice/heater/setBuzzer.py", "False");
            case "setTargetTemp":
                return Utils.executePythonScript(pb, "src/main/java/RealDevice/heater/setTargetTemp.py", "25");
            case "reset":
                return Utils.executePythonScript(pb, "src/main/java/RealDevice/heater/reset.py");
            default:
                return "Error";
        }
    }

    private static String resetToCurrentState(HeaterStatus currentStatus, String api, ProcessBuilder pb) throws IOException, InterruptedException {
        executeApi("reset", pb);
        LOGGER.info("Reset to -> " + currentStatus);
//        LOGGER.info("Current buzzer: " + currentStatus.isBuzzer());
        executeApi("turnOn", pb);
        int targetTemp = currentStatus.getTargetTemp();
        if (targetTemp != 18) {
            executeApi("setTargetTemp", pb);
        }
//        if (currentStatus.isBuzzer()) {
//            executeApi("setBuzzerOn", pb);
//        }else{
//            executeApi("setBuzzerOff", pb);
//        }
        if (!currentStatus.isOn()) {
            executeApi("turnOff", pb);
        }
//        LOGGER.info(executeApi(api, pb));
        return executeApi(api, pb);
    }

    private static void generateGraph(Set<Edge> edges, ProcessBuilder pb) throws IOException, InterruptedException {
        HeaterStatus device = HeaterStatus.parse(executeApi("reset", pb));
        String initState = device.toString();
        String initSystemState = device.toSystemString();
        if (stateTransitions == null) {
            LOGGER.error("Error: stateTransitions is null.");
            return;
        }
        stateTransitions.put(initSystemState, new HashSet<>());
        exploreState(initState, initSystemState, edges, pb);
    }

    private static void exploreState(String state, String systemState, Set<Edge> edges, ProcessBuilder pb) throws IOException, InterruptedException {
        List<String> apis = getAllPossibleAPIs();
        for (String api : apis) {
            LOGGER.info("Current[-] - " + state);
            LOGGER.info("API[-] - " + api);
            HeaterStatus currentState = HeaterStatus.fromString(state);
            HeaterStatus firstNextState = null;

            for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
                HeaterStatus nextState = HeaterStatus.parse(resetToCurrentState(currentState, api, pb));

                if (nextState != null) {
                    if (firstNextState == null) {
                        firstNextState = nextState; // Save the first non-null nextState
                    }
                    LOGGER.info("Current state: " + currentState.toString() + ", API: " + api + ", Next state: " + nextState.toString());
                    String nextSystemState = nextState.toSystemString();
                    Edge edge = new Edge(systemState, nextSystemState, api);
                    edges.add(edge);

                    if (!stateTransitions.containsKey(nextSystemState)) {
                        stateTransitions.put(nextSystemState, new HashSet<>());
                        exploreState(nextState.toString(), nextSystemState, edges, pb);
                    }

                    stateTransitions.get(systemState).add(api);
                    break; // Exit the retry loop if successful
                } else {
                    LOGGER.info("Attempt " + (attempt + 1) + " failed for API: " + api);
                }
            }

            if (firstNextState == null) {
                LOGGER.info("Failed to get a valid nextState for API: " + api + " after " + MAX_RETRIES + " attempts.");
                // Handle the failure case (e.g., log an error, throw an exception, etc.)
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        long startTime = System.nanoTime(); // Get start time
        Graph<Object, Object> userGraph = new Graph<>(true,true,false);
        Set<Edge> edges = new HashSet<>();
        int[] sizeHistory = new int[20];
        int historyIndex = 0;
        Set<String> nodesSet = new TreeSet<>();
        Set<String> nodeOrderSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        // Create node number map
        Map<String, String> nodeNumbers = new HashMap<>();
        int sumEdgeLast;

        // Final edges set
        Set<Edge> final_Edges = new HashSet<>();

        ProcessBuilder pb = new ProcessBuilder();
        pb.redirectErrorStream(true);

        int iteration = 0;
        for (int i = 0; i < 1; i++) {
            LOGGER.info("Iteration: " + iteration++);
            Set<Edge> _temp = new HashSet<>();
            Heater_real_Model test = new Heater_real_Model();
            test.generateGraph(_temp, pb);
            edges.addAll(_temp);
        }

        for (Edge edge: edges){
            String source = edge.getSource();
            String target = edge.getTarget();
            nodesSet.add(source);
            nodesSet.add(target);
        }

        nodeOrderSet.addAll(nodesSet);

        // Count nodes
        int nodeCount = 0;
        for (String node : nodeOrderSet) {
            String nodeNumber = "S" + nodeCount;
            nodeNumbers.put(node, nodeNumber);
            nodeCount++;
        }

        // Output all nodes' outgoing edges and APIs
        int sumEdge = 0;
        for (String node : nodeNumbers.keySet()) {
            String nodeNumber = nodeNumbers.get(node);
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

        // Output node count
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
        for (Edge edge : edges){
            String source = edge.getSource();
            String target = edge.getTarget();
            _nodesSet.add(source);
            _nodesSet.add(target);
        }

        _nodeOrderSet.addAll(nodesSet);

        // Count nodes
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

        long endTime = System.nanoTime(); // Get end time
        long elapsedTime = endTime - startTime; // Calculate run time (nanoseconds)

        double seconds = (double) elapsedTime / 1000000000.0; // Convert to seconds

        LOGGER.info("Program ran for " + seconds + " seconds.");

        LOGGER.info("Node Count: " + nodeCount + " Edge Count: " + dotSet.size());

        String dotFile = "src/main/java/MOCO/modelFiles/heater_real_output.dot";
        generateDotFile(dotSet, nodeMap.size(), dotFile);

        LOGGER.info("=========== Graph Show ==============");
        Collection<graph.Edge> edge = userGraph.getEdges();
        String fileName = "src/main/java/MOCO/modelFiles/heater_Real.json";
        saveEdgesToFile(edge, fileName);

    }
}