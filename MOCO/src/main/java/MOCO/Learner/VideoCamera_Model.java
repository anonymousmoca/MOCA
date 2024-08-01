package MOCO.Learner;

import MOCO.API;
import MOCO.Edge;
import MOCO.Node;
import VirtualDevice.VideoCamera;
import graph.Graph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static MOCO.Utils.generateDotFile;
import static MOCO.Utils.saveEdgesToFile;

public class VideoCamera_Model {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Map<String, Set<String>> stateTransitions;

    public VideoCamera_Model() {
        stateTransitions = new HashMap<>();
    }

    private static List<API> getAllPossibleAPIs() {
        List<API> apis = new ArrayList<>();
        apis.add(new API("turnOn", true, false));
        apis.add(new API("turnOff", true, false));
        apis.add(new API("turnOnMotionRecord", true, false));
        apis.add(new API("turnOffMotionRecord", true, false));
        apis.add(new API("turnOnLight", true, false));
        apis.add(new API("turnOffLight", true, false));
        apis.add(new API("turnOnFullColor", true, false));
        apis.add(new API("turnOffFullColor", true, false));
        apis.add(new API("turnOnFlip", true, false));
        apis.add(new API("turnOffFlip", true, false));
        apis.add(new API("turnOnImproveProgram", true, false));
        apis.add(new API("turnOffImproveProgram", true, false));
        apis.add(new API("turnOnWdr", true, false));
        apis.add(new API("turnOffWdr", true, false));
        apis.add(new API("turnOnTrack", true, false));
        apis.add(new API("turnOffTrack", true, false));
        apis.add(new API("turnOffWatermark", true, false));
        apis.add(new API("setMaxClient", true, false));
        apis.add(new API("setNightMode", true, false));
        apis.add(new API("setMiniLevel", true, false));

        return apis;
    }

    private static String executeApi(String api, VideoCamera videoCamera){
        switch (api) {
            case "turnOn":
                return videoCamera.turnOn().equals("success") ? videoCamera.toString() : "skip";
            case "turnOff":
                return videoCamera.turnOff().equals("success") ? videoCamera.toString() : "skip";
            case "turnOnMotionRecord":
                return videoCamera.turnOnMotionRecord().equals("success") ? videoCamera.toString() : "skip";
            case "turnOffMotionRecord":
                return videoCamera.turnOffMotionRecord().equals("success") ? videoCamera.toString() : "skip";
            case "turnOnLight":
                return videoCamera.turnOnLight().equals("success") ? videoCamera.toString() : "skip";
            case "turnOffLight":
                return videoCamera.turnOffLight().equals("success") ? videoCamera.toString() : "skip";
            case "turnOnFullColor":
                return videoCamera.turnOnFullColor().equals("success") ? videoCamera.toString() : "skip";
            case "turnOffFullColor":
                return videoCamera.turnOffFullColor().equals("success") ? videoCamera.toString() : "skip";
            case "turnOnFlip":
                return videoCamera.turnOnFlip().equals("success") ? videoCamera.toString() : "skip";
            case "turnOffFlip":
                return videoCamera.turnOffFlip().equals("success") ? videoCamera.toString() : "skip";
            case "turnOnImproveProgram":
                return videoCamera.turnOnImproveProgram().equals("success") ? videoCamera.toString() : "skip";
            case "turnOffImproveProgram":
                return videoCamera.turnOffImproveProgram().equals("success") ? videoCamera.toString() : "skip";
            case "turnOnWdr":
                return videoCamera.turnOnWdr().equals("success") ? videoCamera.toString() : "skip";
            case "turnOffWdr":
                return videoCamera.turnOffWdr().equals("success") ? videoCamera.toString() : "skip";
            case "turnOnTrack":
                return videoCamera.turnOnTrack().equals("success") ? videoCamera.toString() : "skip";
            case "turnOffTrack":
                return videoCamera.turnOffTrack().equals("success") ? videoCamera.toString() : "skip";
            case "turnOffWatermark":
                return videoCamera.turnOffWatermark().equals("success") ? videoCamera.toString() : "skip";
            case "setMaxClient":
                return videoCamera.setMaxClient(2).equals("success") ? videoCamera.toString() : "skip";
            case "setNightMode":
                return videoCamera.setNightMode(2).equals("success") ? videoCamera.toString() : "skip";
            case "setMiniLevel":
                return videoCamera.setMiniLevel(2).equals("success") ? videoCamera.toString() : "skip";
            default:
                return videoCamera.toString();
        }
    }

    private static void generateGraph(Set<Edge> edges){
        VideoCamera device = new VideoCamera();
        String initState = device.toString();
        String initSystemState = device.toSystemString();
        stateTransitions.put(initSystemState, new HashSet<>());

        exploreState(initState, initSystemState, edges);
    }

    private static void exploreState(String state, String systemState, Set<Edge> edges){
        List<API> apis = getAllPossibleAPIs();

        for (API api : apis) {
            VideoCamera currentDevice = VideoCamera.fromString(state);
            String nextState = executeApi(api.getName(), currentDevice);
            if (!nextState.equals("skip")){
                VideoCamera nextWM = VideoCamera.fromString(nextState);
                String nextSystemState = nextWM.toSystemString();
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
            VideoCamera_Model test = new VideoCamera_Model();
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


        String dotFile = "src/main/java/MOCO/modelFiles/videoCamera_output.dot";
        generateDotFile(dotSet, nodeMap.size(), dotFile);

        // save userGraph to file
        Collection<graph.Edge> edge = userGraph.getEdges();
        String fileName = "src/main/java/MOCO/modelFiles/videoCamera.json";
        saveEdgesToFile(edge, fileName);

    }


}
