package IoTSystem;

import IoTSystem.DeviceController.*;
import IoTSystem.DeviceTwin.*;
import MOCO.MessageProxy;
import MOCO.Utils;
import VirtualDevice.*;
import graph.Graph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.HWDiskStore;

import java.util.List;


import static IoTSystem.TaskScheduler.sleep;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    // Device, Twin, Controller
    private static CoffeeMachine coffeeMachine;
    private static CMTwin cmTwin;
    private static CMController cmController;

    private static Gateway gateway;
    private static GatewayTwin gatewayTwin;
    private static GatewayController transGatewayController;

    private static Yeelight yeelight;
    private static LightTwin lightTwin;
    private static LightController lightController;

    private static VideoCamera vc;
    private static VCTwin vcTwin;
    private static VCController vcController;

    private static WashingMachine wm;
    private static WMTwin wmTwin;
    private static WMController wmController;

    // TaskScheduler
    private static TaskScheduler taskScheduler;

    // device behavior models
    private static String gatewayFile = "src/main/java/MOCO/modelFiles/gateway.json";
    private static Graph<Object, Object> gateway_behaviorGraph;
    private static String lightFile = "src/main/java/MOCO/modelFiles/yeelight.json";
    private static Graph<Object, Object> light_behaviorGraph;
    private static String cmFile = "src/main/java/MOCO/modelFiles/coffeeMachine.json";
    private static Graph<Object, Object> cm_behaviorGraph;
    private static String vcFile = "src/main/java/MOCO/modelFiles/videoCamera.json";
    private static Graph<Object, Object> vc_behaviorGraph;
    private static String wmFile = "src/main/java/MOCO/modelFiles/washingMachine.json";
    private static Graph<Object, Object> wm_behaviorGraph;

    // outAPIs
    private static Map<String, Set<String>> gateway_outEdgeApis;
    private static Map<String, Set<String>> light_outEdgeApis;
    private static Map<String, Set<String>> cm_outEdgeApis;
    private static Map<String, Set<String>> vc_outEdgeApis;
    private static Map<String, Set<String>> wm_outEdgeApis;

    public static void initialize() {
        coffeeMachine = new CoffeeMachine();
        cmTwin = new CMTwin();
        cmController = new CMController("CM001", coffeeMachine, cmTwin);

        gateway = new Gateway();
        gatewayTwin = new GatewayTwin();
        transGatewayController = new GatewayController("gateway001", gateway, gatewayTwin);

        yeelight = new Yeelight(10, false, 255, 255, 255);
        lightTwin = new LightTwin(10, false, 255, 255, 255);
        lightController = new LightController("light001", yeelight, lightTwin);

        vc = new VideoCamera();
        vcTwin = new VCTwin();
        vcController = new VCController("vc001", vc, vcTwin);

        wm = new WashingMachine();
        wmTwin = new WMTwin();
        wmController = new WMController("wm001", wm, wmTwin);

        taskScheduler = new TaskScheduler(5, cmController, transGatewayController, lightController, vcController, wmController);
    }

    public static void loadBehaviorModelsAndOutApis() {
        gateway_behaviorGraph = Utils.loadGraphFromFile(gatewayFile);
        light_behaviorGraph = Utils.loadGraphFromFile(lightFile);
        cm_behaviorGraph = Utils.loadGraphFromFile(cmFile);
        vc_behaviorGraph = Utils.loadGraphFromFile(vcFile);
        wm_behaviorGraph = Utils.loadGraphFromFile(wmFile);
        gateway_outEdgeApis = Utils.loadOutEdgeApis(gateway_behaviorGraph);
        light_outEdgeApis = Utils.loadOutEdgeApis(light_behaviorGraph);
        cm_outEdgeApis = Utils.loadOutEdgeApis(cm_behaviorGraph);
        vc_outEdgeApis = Utils.loadOutEdgeApis(vc_behaviorGraph);
        wm_outEdgeApis = Utils.loadOutEdgeApis(wm_behaviorGraph);
    }

    public static String getCurrentStateBasedOnMsg (Message message){
        String deviceType =  message.getDeviceType();
        switch (deviceType) {
            case "CoffeeMachine":
                return cmTwin.toSystemDeviceString();
            case "Gateway":
                return gatewayTwin.toSystemDeviceString();
            case "Yeelight":
                return lightTwin.toSystemDeviceString();
            case "VideoCamera":
                return vcTwin.toSystemDeviceString();
            case "WashingMachine":
                return wmTwin.toDeviceString();
            default:
                return "Invalid";
        }
    }


    public static void main(String[] args) {
        LOGGER.info("Starting loading files ...");
        initialize();
        loadBehaviorModelsAndOutApis();
        LOGGER.info("Files loaded successfully!");

        MessageProxy messageProxy = new MessageProxy(taskScheduler, gateway_outEdgeApis, light_outEdgeApis, cm_outEdgeApis, vc_outEdgeApis, wm_outEdgeApis, true);
        messageProxy.setProxyOn(true);

        // Monitor system performance before the test
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        CentralProcessor processor = hal.getProcessor();
        List<HWDiskStore> diskStores = hal.getDiskStores();
        double[] loadBefore = processor.getSystemLoadAverage(3);
        long[] readBytesBefore = new long[diskStores.size()];
        long[] writeBytesBefore = new long[diskStores.size()];
        for (int i = 0; i < diskStores.size(); i++) {
            readBytesBefore[i] = diskStores.get(i).getReadBytes();
            writeBytesBefore[i] = diskStores.get(i).getWriteBytes();
        }

        long startTime = System.nanoTime(); // start time of the program
        taskScheduler.start();

        for (int i = 0; i < 100; i++) {
            // MessageSender.sendMessageYeelight(messageProxy);
            // MessageSender.sendMessageVideoCamera(messageProxy);
//            MessageSender.sendMessageGateway(messageProxy);
            MessageSender.sendMessageWashingMachine(messageProxy);
//            MessageSender.sendMessageCoffeeMachine(messageProxy);
        }

        taskScheduler.shutdown();
        long endTime = System.nanoTime(); // end time of the program
        double duration = (endTime - startTime) / 1000000000.0 - 5;
        LOGGER.info("Program ran for " + duration + " seconds.");

        // Monitor system performance after the test
        double[] loadAfter = processor.getSystemLoadAverage(3);
        long[] readBytesAfter = new long[diskStores.size()];
        long[] writeBytesAfter = new long[diskStores.size()];
        for (int i = 0; i < diskStores.size(); i++) {
            readBytesAfter[i] = diskStores.get(i).getReadBytes();
            writeBytesAfter[i] = diskStores.get(i).getWriteBytes();
        }

        // Print CPU load difference
        LOGGER.info("CPU Load Average Difference:");
        for (int i = 0; i < loadBefore.length; i++) {
            double diff = loadAfter[i] - loadBefore[i];
            LOGGER.info(String.format(" %d min: %.2f%%", i + 1, diff * 100));
        }

        // Print disk read/write bytes difference
        LOGGER.info("Disk Usage Difference:");
        for (int i = 0; i < diskStores.size(); i++) {
            long readDiff = readBytesAfter[i] - readBytesBefore[i];
            long writeDiff = writeBytesAfter[i] - writeBytesBefore[i];
            LOGGER.info(String.format(" Disk %s: Read Difference: %d bytes, Write Difference: %d bytes", diskStores.get(i).getName(), readDiff, writeDiff));
        }
    }


}
