package MOCO;

import IoTSystem.DelayedMessage;
import IoTSystem.DeviceController.*;
import IoTSystem.DeviceTwin.*;
import IoTSystem.Message;
import VirtualDevice.*;
import graph.Graph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.DelayQueue;

public class ExecutionChecker {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Graph<Object, Object> gateway_behaviorGraph = Utils.loadGraphFromFile("src/main/java/MOCO/modelFiles/gateway.json");
    private static final Graph<Object, Object> light_behaviorGraph = Utils.loadGraphFromFile("src/main/java/MOCO/modelFiles/yeelight.json");
    private static final Graph<Object, Object> cm_behaviorGraph = Utils.loadGraphFromFile("src/main/java/MOCO/modelFiles/coffeeMachine.json");
    private static final Graph<Object, Object> vc_behaviorGraph = Utils.loadGraphFromFile("src/main/java/MOCO/modelFiles/videoCamera.json");
    private static final Graph<Object, Object> wm_behaviorGraph = Utils.loadGraphFromFile("src/main/java/MOCO/modelFiles/washingMachine.json");

    public static boolean preCheck(Message message, String currentState, Map<String, Set<String>> gateway_outEdgeApis,
                                   Map<String, Set<String>> light_outEdgeApis, Map<String, Set<String>> cm_outEdgeApis,
                                   Map<String, Set<String>> vc_outEdgeApis, Map<String, Set<String>> wm_outEdgeApis) {
//        LOGGER.info("[*] PreCheck. Msg:" + message + " CurrentState:" + currentState);
        if (currentState.equals("Invalid")){
            return false;
        }
        String deviceType = message.getDeviceType();
//        LOGGER.info(currentState);
//        LOGGER.info(light_outEdgeApis.keySet());
        switch (deviceType) {
            case "CoffeeMachine":
                return cm_outEdgeApis.get(currentState).toString().contains(message.getDeviceAPI());
            case "Gateway":
                return gateway_outEdgeApis.get(currentState).toString().contains(message.getDeviceAPI());
            case "Yeelight":
                return light_outEdgeApis.get(currentState).toString().contains(message.getDeviceAPI());
            case "VideoCamera":
                return vc_outEdgeApis.get(currentState).toString().contains(message.getDeviceAPI());
            case "WashingMachine":
                return wm_outEdgeApis.get(currentState).toString().contains(message.getDeviceAPI());
            default:
                return false;
        }
    }

    public static void postCheck(String api, String preState, Object controller, DelayQueue<DelayedMessage> delayedQueue, Message message){
        String controllerName = controller.getClass().getSimpleName();
        checkDeviations(controllerName, preState, api, controller, delayedQueue, message);
    }

    public static void checkDeviations(String controllerName, String preState, String api, Object controller,
                                       DelayQueue<DelayedMessage> delayedQueue, Message message){
        switch (controllerName){
            case "CMController":
                String currentState = ((CMController) controller).getCmTwin().toSystemDeviceString();
                String targetState = getTargetStateBasedOnBehaviourModels(preState, api, cm_behaviorGraph);
                if (!currentState.equals(targetState) && !targetState.equals("null")){
                    LOGGER.error("Digital Deviation Detected");
                    ((CMController) controller).getCmTwin().setTargetState(targetState);
                }
                // Check physical deviation
                String currentPhysicalState = ((CMController) controller).getCoffeeMachine().toSystemStateString();
                currentPhysicalState = "CoffeeMachine{'waterReady':'true', 'beanReady':'false', 'milkReady':'false', 'cupReady':'true', 'thisTimeCoffeeReady':'false', 'isWorking':'false', 'isPowerOn':'true'}";
                if (!currentPhysicalState.equals(targetState) && !targetState.equals("null")){
                    LOGGER.error("Physical Deviation Detected");
                    if (currentPhysicalState.equals(preState)){
                        delayedQueue.offer(new DelayedMessage(message, 100));
                    }else {
                        List<String> actionLists = calculateSolutions(cm_behaviorGraph, currentPhysicalState, targetState, message);
                        generateCMActions(actionLists, ((CMController) controller), delayedQueue);
                        LOGGER.info("Action List: " + actionLists);
                    }
                }
                break;
            case "GatewayController":
                String currentState_gateway = ((GatewayController) controller).getGatewayTwin().toSystemDeviceString();
                String targetState_gateway = getTargetStateBasedOnBehaviourModels(preState, api, gateway_behaviorGraph);
                if (!currentState_gateway.equals(targetState_gateway) && !targetState_gateway.equals("null")){
                    LOGGER.error("Digital Deviation Detected");
                    ((GatewayController) controller).getGatewayTwin().setTargetState(targetState_gateway);
                }
                // Check physical deviation
                String currentGateway = ((GatewayController) controller).getGateway().toSystemString();
                if (!currentGateway.equals(targetState_gateway) && !targetState_gateway.equals("null")){
                    LOGGER.error("Physical Deviation Detected");
                    if (currentGateway.equals(preState)){
                        delayedQueue.offer(new DelayedMessage(message, 100));
                    }else {
                        List<String> actionLists = calculateSolutions(cm_behaviorGraph, currentGateway, targetState_gateway, message);
                        generateGWActions(actionLists, ((GatewayController) controller), delayedQueue);
                        LOGGER.info("Action List: " + actionLists);
                    }
                }
                break;
            case "LightController":
                String current_light = ((LightController) controller).getLightTwin().toSystemString();
                String target_light = getTargetStateBasedOnBehaviourModels(preState, api, light_behaviorGraph);
                if (!current_light.equals(target_light) && !target_light.equals("null")){
                    LOGGER.error("Digital Deviation Detected");
                    ((LightController) controller).getLightTwin().setTargetState(target_light);
                }
                // Check physical deviation
                String currentLight = ((LightController) controller).getYeelight().toSystemString();
                if (!currentLight.equals(target_light) && !target_light.equals("null")){
                    LOGGER.error("Physical Deviation Detected");
                    if (currentLight.equals(preState)){
                        delayedQueue.offer(new DelayedMessage(message, 100));
                    }else {
                        List<String> actionLists = calculateSolutions(cm_behaviorGraph, currentLight, target_light, message);
                        generateLightActions(actionLists, ((LightController) controller), delayedQueue);
                        LOGGER.info("Action List: " + actionLists);
                    }
                }
                break;
            case "VCController":
                String current_vc = ((VCController) controller).getVcTwin().toSystemDeviceString();
                String target_vc = getTargetStateBasedOnBehaviourModels(preState, api, vc_behaviorGraph);
                if (!current_vc.equals(target_vc) && !target_vc.equals("null")){
                    LOGGER.error("Digital Deviation Detected");
                    ((VCController) controller).getVcTwin().setTargetState(target_vc);
                }
                // Check physical deviation
                String currentVideoCamera = ((VCController) controller).getVc().toSystemString();
                if (!currentVideoCamera.equals(target_vc) && !target_vc.equals("null")){
                    LOGGER.error("Physical Deviation Detected");
                    if (currentVideoCamera.equals(preState)){
                        delayedQueue.offer(new DelayedMessage(message, 100));
                    }else {
                        List<String> actionLists = calculateSolutions(cm_behaviorGraph, currentVideoCamera, target_vc, message);
                        generateVCActions(actionLists, ((VCController) controller), delayedQueue);
                        LOGGER.info("Action List: " + actionLists);
                    }
                }
                break;
            case "WMController":
                String current_wm = ((WMController) controller).getWmTwin().toDeviceString();
                String target_wm = getTargetStateBasedOnBehaviourModels(preState, api, wm_behaviorGraph);
                if (!current_wm.equals(target_wm) && !target_wm.equals("null")){
                    LOGGER.error("Digital Deviation Detected");
                    ((WMController) controller).getWmTwin().setTargetState(target_wm);
                }
                // Check physical deviation
                String currentWM = ((WMController) controller).getWm().toString();
                if (!currentWM.equals(target_wm) && !target_wm.equals("null")){
                    LOGGER.error("Physical Deviation Detected");
                    if (currentWM.equals(preState)){
                        delayedQueue.offer(new DelayedMessage(message, 100));
                    }else {
                        List<String> actionLists = calculateSolutions(cm_behaviorGraph, currentWM, target_wm, message);
                        generateVMActions(actionLists, ((WMController) controller), delayedQueue);
                        LOGGER.info("Action List: " + actionLists);
                    }
                }
                break;
            default:
                LOGGER.info("Controller not found");
        }
    }

    private static void generateVMActions(List<String> actionLists, WMController controller, DelayQueue<DelayedMessage> delayedQueue) {
        for (String action : actionLists){
            if ("START_FROM_ORIGIN".equals(action)) {
                controller.setWm(new WashingMachine());
                controller.setWmTwin(new WMTwin());
            } else {
                delayedQueue.offer(new DelayedMessage(new Message("WashingMachine", action, new String[]{}), 100));
            }
        }
    }

    private static void generateVCActions(List<String> actionLists, VCController controller, DelayQueue<DelayedMessage> delayedQueue) {
        for (String action : actionLists){
            if ("START_FROM_ORIGIN".equals(action)) {
                controller.setVc(new VideoCamera());
                controller.setVcTwin(new VCTwin());
            } else {
                delayedQueue.offer(new DelayedMessage(new Message("VideoCamera", action, new String[]{}), 100));
            }
        }
    }

    private static void generateLightActions(List<String> actionLists, LightController controller, DelayQueue<DelayedMessage> delayedQueue) {
        for (String action : actionLists){
            switch (action) {
                case "START_FROM_ORIGIN":
                    Yeelight yeelight = new Yeelight(10, false, 255, 255, 255);
                    LightTwin lightTwin = new LightTwin(10, false, 255, 255, 255);
                    controller.setYeelight(yeelight);
                    controller.setLightTwin(lightTwin);
                    break;
                case "setBrightness":
                    delayedQueue.offer(new DelayedMessage(new Message("Yeelight", "setBrightness", new String[]{"50"}), 100));
                    break;
                case "setRGB":
                    delayedQueue.offer(new DelayedMessage(new Message("Yeelight", "setRGB", new String[]{"120", "130", "111"}), 100));
                    break;
                default:
                    delayedQueue.offer(new DelayedMessage(new Message("Yeelight", action, new String[]{}), 100));
                    break;
            }
        }
    }

    private static void generateGWActions(List<String> actionLists, GatewayController controller, DelayQueue<DelayedMessage> delayedQueue) {
        for (String action : actionLists){
            switch (action) {
                case "START_FROM_ORIGIN":
                    controller.setGateway(new Gateway());
                    controller.setGatewayTwin(new GatewayTwin());
                    break;
                case "setLightBrightness":
                    delayedQueue.offer(new DelayedMessage(new Message("Gateway", "setLightBrightness", new String[]{"50"}), 100));
                    break;
                case "addDevice":
                    delayedQueue.offer(new DelayedMessage(new Message("Gateway", "addDevice", new String[]{"device1"}), 100));
                    break;
                case "removeDevice":
                    new Message("Gateway", "removeDevice", new String[]{"device1"});
                    delayedQueue.offer(new DelayedMessage(new Message("Gateway", "removeDevice", new String[]{"device1"}), 100));
                    break;
                default:
                    delayedQueue.offer(new DelayedMessage(new Message("Gateway", action, new String[]{}), 100));
                    break;
            }
        }
    }

    private static void generateCMActions(List<String> actionLists, CMController controller, DelayQueue<DelayedMessage> delayedQueue) {
        for (String action : actionLists){
            switch (action) {
                case "START_FROM_ORIGIN":
                    controller.setCoffeeMachine(new CoffeeMachine());
                    controller.setCmTwin(new CMTwin());
                    break;
                case "brewCoffee":
                    delayedQueue.offer(new DelayedMessage(new Message("CoffeeMachine", "brewCoffee", new String[]{"1"}), 100));
                    break;
                default:
                    delayedQueue.offer(new DelayedMessage(new Message("CoffeeMachine", action, new String[]{}), 100));
                    break;
            }
        }
    }

    public static String getTargetStateBasedOnBehaviourModels (String preState, String cmd, Graph<Object, Object> behaviorGraph) {
        Collection<graph.Edge> edges = behaviorGraph.getEdges();
        for (graph.Edge edge : edges) {
            if (edge.getSource().contains(preState) && edge.getName().equals(cmd)) {
//                LOGGER.info("[****************] Target State: " + Node.fromString(edge.getTarget()).getContent());
                return Node.fromString(edge.getTarget()).getContent();
            }
        }
        return "null";
    }

    public static List<String> calculateSolutions(Graph<Object, Object> behaviorGraph, String source, String target, Message message){
        Collection<graph.Edge> edges = behaviorGraph.getEdges();
        String sourceNode = "";
        String targetNode = "";
        String S0 = "";
        for (graph.Edge edge : edges) {
            if (edge.getSource().contains(source)) {
                LOGGER.info(edge.getSource());
                sourceNode = edge.getSource();
            }
            if (edge.getSource().contains(target)) {
                targetNode = edge.getSource();
            }
            if (edge.getSource().contains("S0")) {
                S0 = edge.getSource();
            }
        }
        LOGGER.info("-----------------");
//        LOGGER.info("SourceNode: " + sourceNode);
//        LOGGER.info("TargetNode: " + targetNode);
//        LOGGER.info("S0: " + S0);
        LOGGER.info("Calculate Solutions");
        LOGGER.info("-----------------");
        List<List<String>> paths = Utils.findShortestPaths(behaviorGraph, sourceNode, targetNode, S0);
        for (List<String> path : paths) {
            if (path.get(path.size()-1).equals(message.getDeviceAPI()) && !path.contains("START_FROM_ORIGIN"))
                return path;
        }
        for (List<String> path : paths) {
            if (path.get(path.size()-1).equals(message.getDeviceAPI()))
                return path;
        }
        for (List<String> path : paths) {
            if (!path.contains("START_FROM_ORIGIN"))
                return path;
        }
        return paths.get(0);
    }

}
