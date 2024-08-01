package MOCO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import graph.Graph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Utils {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void generateDotFile(Set<Edge> dotSet, int numNodes, String filePath) {
        if (dotSet == null) {
            throw new IllegalArgumentException("dotSet cannot be null");
        }

        StringBuilder dotContent = new StringBuilder();
        dotContent.append("digraph g {\n\n");

        for (Edge edge : dotSet) {
            if (edge.getSource() == null || edge.getTarget() == null || edge.getApi() == null) {
                System.err.println("Edge contains null values: " + edge);
                continue;
            }
            dotContent.append("\t")
                    .append(edge.getSource())
                    .append(" -> ")
                    .append(edge.getTarget())
                    .append(" [label=\"")
                    .append(edge.getApi())
                    .append("\"];\n");
        }

        dotContent.append("\n}\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(dotContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveEdgesToFile(Collection<graph.Edge> edges, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            new Gson().toJson(edges, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Graph<Object, Object> loadGraphFromFile(String fileName) {
        Graph<Object, Object> behaviorGraph = new Graph<>(true, true, false);
        try (FileReader reader = new FileReader(fileName)) {
            Type edgeType = new TypeToken<Collection<graph.Edge>>(){}.getType();
            Collection<graph.Edge> edges = new Gson().fromJson(reader, edgeType);
            for (graph.Edge edge : edges) {
                behaviorGraph.setEdge(edge.getSource(), edge.getTarget(), edge.getName(), edge.getName());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return behaviorGraph;
    }

    public static Map<String, Set<String>> loadOutEdgeApis(Graph<Object, Object> behaviorGraph){
        Map<String, Set<String>> outEdgeApis = new HashMap<>();
        Collection<graph.Edge> edges = behaviorGraph.getEdges();
        edges.forEach(edge ->{
            Node source = Node.fromString(edge.getSource());
            String nodeContent = source.getContent();
            if (!outEdgeApis.containsKey(nodeContent)){
                outEdgeApis.put(nodeContent, new HashSet<>());
            }
            outEdgeApis.get(nodeContent).add(edge.getName());
        });
        return outEdgeApis;
    }

    public static List<List<String>> findShortestPaths(Graph<Object, Object> behaviorGraph, String node1, String node2, String node0) {
        List<List<String>> paths = new ArrayList<>();
        if (!bfs(behaviorGraph, node1, node2, paths)) {
            List<List<String>> pathFromNode0 = new ArrayList<>();
            bfs(behaviorGraph, node0, node1, pathFromNode0);
            if (!pathFromNode0.isEmpty()) {
                for (List<String> path : pathFromNode0) {
                    path.add(0, "START_FROM_ORIGIN");
                }
                paths = pathFromNode0.subList(0, Math.min(3, pathFromNode0.size()));
            } else {
                paths.add(new ArrayList<>());
            }
        } else {
            paths = paths.subList(0, Math.min(3, paths.size()));
        }
        return paths;
    }

    private static boolean bfs(Graph<Object, Object> graph, String start, String end, List<List<String>> allPaths) {
        Queue<PathNode> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(new PathNode(start, new ArrayList<>()));
        boolean found = false;

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();
            String lastNode = current.node;

            if (lastNode.equals(end)) {
                allPaths.add(new ArrayList<>(current.path));
                found = true;
                continue;
            }

            if (visited.contains(lastNode)) {
                continue;
            }
            visited.add(lastNode);

            for (Object edgeObj : graph.outEdges(lastNode)) {
                graph.Edge edge = (graph.Edge) edgeObj;
                String neighbor = (String) edge.getTarget();
                if (!visited.contains(neighbor)) {
                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(edge.getName());
                    queue.offer(new PathNode(neighbor, newPath));
                }
            }
        }
        return found;
    }

    private static class PathNode {
        String node;
        List<String> path;

        PathNode(String node, List<String> path) {
            this.node = node;
            this.path = path;
        }
    }

    public static String executePythonScript(ProcessBuilder pb, String scriptPath, String... args) throws IOException, InterruptedException {
        List<String> command = new ArrayList<>();
        command.add("python");
        command.add(scriptPath);
        command.addAll(Arrays.asList(args));

        pb.command(command.toArray(new String[0]));
//        LOGGER.info(pb.environment());

        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }
        reader.close();
        process.waitFor();
        int exitCode = process.exitValue();
        if (exitCode != 0) {
            return "Error";
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String fileName = "src/main/java/MOCO/modelFiles/yeelight.json";
        Utils utils = new Utils();
        Graph<Object, Object> gateway_behaviorGraph = utils.loadGraphFromFile(fileName);
        Collection<graph.Edge> edges = gateway_behaviorGraph.getEdges();

        String S3 = "";
        String S4 = "";
        String S0 = "";

        for (graph.Edge edge : edges) {
            if (edge.getSource().contains("S3")) {
                LOGGER.info(edge.getSource());
                S3 = edge.getSource();
            }
            if (edge.getSource().contains("S4")) {
                S4 = edge.getSource();
            }
            if (edge.getSource().contains("S0")) {
                S0 = edge.getSource();
            }
        }

        Map<String, Set<String>> outEdgeApis = loadOutEdgeApis(gateway_behaviorGraph);


        for (Map.Entry<String, Set<String>> entry : outEdgeApis.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        List<List<String>> paths = findShortestPaths(gateway_behaviorGraph, S3, S4, S0);
        for (List<String> path : paths) {
            System.out.println(path);
        }

    }

}
