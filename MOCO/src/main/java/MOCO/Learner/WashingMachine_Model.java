package MOCO.Learner;

import MOCO.API;
import MOCO.Edge;
import MOCO.Node;
import VirtualDevice.WashingMachine;
import graph.Graph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static MOCO.Utils.generateDotFile;
import static MOCO.Utils.saveEdgesToFile;

public class WashingMachine_Model {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Map<String, Set<String>> stateTransitions;

    public WashingMachine_Model() {
        stateTransitions = new HashMap<>();
    }

    private static List<API> getAllPossibleAPIs() {
        List<API> apis = new ArrayList<>();
        apis.add(new API("turnOn", true, false));
        apis.add(new API("turnOff", true, false));
        apis.add(new API("openDoor", true, false));
        apis.add(new API("closeDoor", true, false));
        apis.add(new API("fillWater", false, false));
        apis.add(new API("startWashing", false, true));
        apis.add(new API("startRinsing", false, true));
        apis.add(new API("startSpinning", false, true));
        apis.add(new API("stop", true, true));
        return apis;
    }

    private static String executeApi(String api, WashingMachine wm){
        switch (api) {
            case "turnOn":
                return wm.turnOn().equals("success") ? wm.toString() : "skip";
            case "turnOff":
                return wm.turnOff().equals("success") ? wm.toString() : "skip";
            case "openDoor":
                return wm.openDoor().equals("success") ? wm.toString() : "skip";
            case "closeDoor":
                return wm.closeDoor().equals("success") ? wm.toString() : "skip";
            case "fillWater":
                return wm.fillWater().equals("success") ? wm.toString() : "skip";
            case "startWashing":
                return wm.startWashing().equals("success") ? wm.toString() : "skip";
            case "startRinsing":
                return wm.startRinsing().equals("success") ? wm.toString() : "skip";
            case "startSpinning":
                return wm.startSpinning().equals("success") ? wm.toString() : "skip";
            case "stop":
                return wm.stop().equals("success") ? wm.toString() : "skip";
            default:
                return wm.toString();
        }
    }

    private static void generateGraph(Set<Edge> edges){
        WashingMachine device = new WashingMachine();
        String initState = device.toString();
        String initSystemState = device.toString();
        stateTransitions.put(initSystemState, new HashSet<>());

        exploreState(initState, initSystemState, edges);
    }

    private static void exploreState(String state, String systemState, Set<Edge> edges){
        List<API> apis = getAllPossibleAPIs();

        for (API api : apis) {
            WashingMachine currentDevice = WashingMachine.fromString(state);
            String nextState = executeApi(api.getName(), currentDevice);
            if (!nextState.equals("skip")){
                WashingMachine nextWM = WashingMachine.fromString(nextState);
                String nextSystemState = nextWM.toString();
                Edge edge = new Edge(systemState, nextSystemState, api.toString());
                edges.add(edge);

                if (!stateTransitions.containsKey(nextSystemState)) {
                    stateTransitions.put(nextSystemState, new HashSet<>());
                    exploreState(nextWM.toString(), nextSystemState, edges);
                }

                stateTransitions.get(systemState).add(api.getName());

            }
        }
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Graph<Object, Object> userGraph = new Graph<>(true,true,false);
        Set<Edge> edges = new HashSet<>();
        int[] sizeHistory = new int[20];
        int historyIndex = 0;
        Set<String> nodesSet = new TreeSet<>();
        Set<String> nodeOrderSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        Map<String, String> nodeNumbers = new HashMap<>();
        int sumEdgeLast;

        Set<Edge> final_Edges = new HashSet<>();

        int iteration = 0;
        for (int i = 0; i < 2; i++) {
            LOGGER.info("Iteration: " + iteration++);
            Set<Edge> _temp = new HashSet<>();
            WashingMachine_Model test = new WashingMachine_Model();
            test.generateGraph(_temp);
            edges.addAll(_temp);
        }

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

        LOGGER.info("Node Count: " + nodeCount);
        LOGGER.info("Sum Edge: " + sumEdge);

        sizeHistory[historyIndex] = sumEdge;
        historyIndex = (historyIndex + 1) % 20;
        LOGGER.info("[History]: " + Arrays.toString(sizeHistory));
        LOGGER.info("------------------------");
//            LOGGER.info("[Current]: " + edges.size());

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

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        double seconds = (double) elapsedTime / 1000000000.0;

        LOGGER.info("Program ran for " + seconds + " seconds.");

        LOGGER.info("Node Count: " + nodeCount + " Edge Count: " + dotSet.size());

        String dotFile = "src/main/java/MOCO/modelFiles/washingMachine_output.dot";
        generateDotFile(dotSet, nodeMap.size(), dotFile);

        // save userGraph to file
        Collection<graph.Edge> edge = userGraph.getEdges();
        String fileName = "src/main/java/MOCO/modelFiles/washingMachine.json";
        saveEdgesToFile(edge, fileName);


    }




}
