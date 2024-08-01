package IoTSystem;

import IoTSystem.DeviceController.*;
import IoTSystem.DeviceTwin.*;
import MOCO.ExecutionChecker;
import VirtualDevice.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.*;

public class TaskScheduler {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ExecutorService executor;
    private static DelayQueue<DelayedMessage> delayedQueue = new DelayQueue<>();;
    private MessageQueue messageQueue;
    private CMController cmController;
    private GatewayController gatewayController;
    private LightController lightController;
    private VCController vcController;
    private WMController wmController;

    public TaskScheduler(int threadPoolSize, CMController cmController) {
        if (threadPoolSize == 0) {
            this.executor = Executors.newSingleThreadExecutor();
        }else{
            this.executor = Executors.newFixedThreadPool(threadPoolSize);
        }
        this.messageQueue = new MessageQueue();
        this.cmController = cmController;
        this.delayedQueue = new DelayQueue<>();
    }

    public TaskScheduler(int threadPoolSize, CMController cmController, GatewayController gatewayController, LightController lightController, VCController vcController, WMController wmController) {
        if (threadPoolSize == 0) {
            this.executor = Executors.newSingleThreadExecutor();
        }else{
            this.executor = Executors.newFixedThreadPool(threadPoolSize);
        }
        this.messageQueue = new MessageQueue();
        this.cmController = cmController;
        this.delayedQueue = new DelayQueue<>();
        this.gatewayController = gatewayController;
        this.lightController = lightController;
        this.vcController = vcController;
        this.wmController = wmController;
    }

    public void addMessage(Message message) {
        messageQueue.addMessage(message);
    }

    // Start the task scheduler
    public void start() {
//        executor.submit(() -> {
//            while (!Thread.currentThread().isInterrupted()) {
//                // Take the next message from the queue
//                Message message = messageQueue.getMessage();
//                // Dispatch the message to the appropriate controller
//                dispatchMessage(message);
//            }
//        });
        executor.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Message message = messageQueue.getMessage();
                dispatchMessage(message);
            }
        });
        executor.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    DelayedMessage delayedMessage = delayedQueue.take();
                    messageQueue.addMessage(delayedMessage.getMessage());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    // Method to dispatch message to the appropriate controller
    private void dispatchMessage(Message message) {
        if (message != null) {
            try {
                LOGGER.info("[Handling...]" + message);
                String deviceType = message.getDeviceType();
                String api = message.getDeviceAPI();
                String[] args = message.getDeviceAPIArgs();
                // Dispatch the message to the appropriate controller
                if (args == null) {
                    args = new String[0];
                }
                Class<?>[] argTypes = new Class[args.length];
                Object[] parsedArgs = new Object[args.length];
                for (int i = 0; i < args.length; i++) {
                    parsedArgs[i] = parseArg(args[i]);
                    argTypes[i] = getPrimitiveType(parsedArgs[i].getClass());
                }
                switch (deviceType) {
                    case "CoffeeMachine":
                        String state = cmController.getCmTwin().toSystemStateString().replace("CMTwin","CoffeeMachine");
                        invokeMethod(cmController, api, argTypes, parsedArgs, state, message);
                        cmController.printInternalState();
                        break;
                    case "Gateway":
                        state = gatewayController.getGatewayTwin().toSystemString().replace("GatewayTwin","Gateway");
                        invokeMethod(gatewayController, api, argTypes, parsedArgs, state, message);
                        gatewayController.printInternalState();
                        break;
                    case "Yeelight":
                        state = lightController.getLightTwin().toSystemString().replace("LightTwin","Yeelight");
                        invokeMethod(lightController, api, argTypes, parsedArgs, state, message);
                        lightController.printInternalState();
                        break;
                    case "VideoCamera":
                        state = vcController.getVcTwin().toSystemString().replace("VCTwin","VideoCamera");
                        invokeMethod(vcController, api, argTypes, parsedArgs, state, message);
                        vcController.printInternalState();
                        break;
                    case "WashingMachine":
                        state = wmController.getWmTwin().toString().replace("WMTwin","WashingMachine");
                        invokeMethod(wmController, api, argTypes, parsedArgs, state, message);
                        wmController.printInternalState();
                        break;
                    default:
                        LOGGER.error("Device type not found: " + deviceType);
                }

            } catch (NoSuchMethodException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            LOGGER.info("Thread interrupted");
            Thread.currentThread().interrupt();
        }
    }

    public static void invokeMethod(Object controller, String methodName, Class<?>[] argTypes, Object[] args, String currentState, Message message) throws NoSuchMethodException, InterruptedException {
        Method method = controller.getClass().getMethod(methodName, argTypes);
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            try {
                method.invoke(controller, args);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                LOGGER.info("[Done] " + methodName);
                ExecutionChecker.postCheck(methodName, currentState, controller, delayedQueue, message);
                latch.countDown();
            }
        }).start();

//        latch.await();
    }

    private Object parseArg(String arg) {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return arg;
        }
    }

    // Shutdown the task scheduler gracefully
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.err.println("Executor did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private Class<?> getPrimitiveType(Class<?> wrapper) {
        if (wrapper == Integer.class) {
            return int.class;
        }
        return wrapper;
    }

    public static void main(String[] args) {

        CoffeeMachine coffeeMachine = new CoffeeMachine();
        CMTwin cmTwin = new CMTwin();
        CMController cmController = new CMController("CM001", coffeeMachine, cmTwin);

        Gateway gateway = new Gateway();
        GatewayTwin gatewayTwin = new GatewayTwin();
        GatewayController transGatewayController = new GatewayController("gateway1", gateway, gatewayTwin);

        Yeelight yeelight = new Yeelight(10, true, 255, 255, 255);
        LightTwin lightTwin = new LightTwin(10, true, 255, 255, 255);
        LightController lightController = new LightController("light1", yeelight, lightTwin);

        VCController vcController = new VCController("vc001", new VideoCamera(), new VCTwin());

        WashingMachine wm = new WashingMachine();
        WMTwin wmTwin = new WMTwin();
        WMController wmController = new WMController("wm1", wm, wmTwin);

        TaskScheduler taskScheduler = new TaskScheduler(0, cmController, transGatewayController, lightController, vcController, wmController);

        // Test adding and getting messages
        Message message1 = new Message("CoffeeMachine", "turnOn", new String[]{});
        Message message2 = new Message("CoffeeMachine", "addWater", new String[]{});
        Message message3 = new Message("CoffeeMachine", "addMilk", new String[]{});
        Message message4 = new Message("CoffeeMachine", "addCoffeeBean", new String[]{});
        Message message5 = new Message("CoffeeMachine", "placeCup", new String[]{});
        Message message6 = new Message("CoffeeMachine", "brewCoffee", new String[]{"1"});

        taskScheduler.addMessage(message1);
        taskScheduler.addMessage(message2);
        taskScheduler.addMessage(message3);
        taskScheduler.addMessage(message4);
        taskScheduler.addMessage(message5);
        taskScheduler.addMessage(message6);

        Message wmMessage1 = new Message("WashingMachine", "turnOn", new String[]{});
        Message wmMessage2 = new Message("WashingMachine", "openDoor", new String[]{});
        Message wmMessage3 = new Message("WashingMachine", "closeDoor", new String[]{});
        Message wmMessage4 = new Message("WashingMachine", "fillWater", new String[]{});
        taskScheduler.addMessage(wmMessage1);
        taskScheduler.addMessage(wmMessage2);
        taskScheduler.addMessage(wmMessage3);
        taskScheduler.addMessage(wmMessage4);

        Message gwMessage1 = new Message("Gateway", "turnLightOn", new String[]{});
        Message gwMessage2 = new Message("Gateway", "turnLightOff", new String[]{});
        Message gwMessage3 = new Message("Gateway", "setLightBrightness", new String[]{"50"});
        Message gwMessage4 = new Message("Gateway", "turnAlarmOn", new String[]{});
        Message gwMessage5 = new Message("Gateway", "turnAlarmOff", new String[]{});
        Message gwMessage6 = new Message("Gateway", "addDevice", new String[]{"device1"});
        Message gwMessage7 = new Message("Gateway", "removeDevice", new String[]{"device1"});
        taskScheduler.addMessage(gwMessage1);
        taskScheduler.addMessage(gwMessage2);
        taskScheduler.addMessage(gwMessage3);
        taskScheduler.addMessage(gwMessage4);
        taskScheduler.addMessage(gwMessage5);
        taskScheduler.addMessage(gwMessage6);
        taskScheduler.addMessage(gwMessage7);

        Message vcMessage1 = new Message("VideoCamera", "turnOnMotionRecord", new String[]{});
        Message vcMessage2 = new Message("VideoCamera", "turnOnLight", new String[]{});
        Message vcMessage3 = new Message("VideoCamera", "turnOnFullColor", new String[]{});
        Message vcMessage4 = new Message("VideoCamera", "turnOnFlip", new String[]{});
        Message vcMessage5 = new Message("VideoCamera", "turnOnImproveProgram", new String[]{});
        Message vcMessage6 = new Message("VideoCamera", "turnOnWdr", new String[]{});
        Message vcMessage7 = new Message("VideoCamera", "turnOnTrack", new String[]{});
        taskScheduler.addMessage(vcMessage1);
        taskScheduler.addMessage(vcMessage2);
        taskScheduler.addMessage(vcMessage3);
        taskScheduler.addMessage(vcMessage4);
        taskScheduler.addMessage(vcMessage5);
        taskScheduler.addMessage(vcMessage6);
        taskScheduler.addMessage(vcMessage7);

        Message lcMessage1 = new Message("Yeelight", "turnOn", new String[]{});
        Message lcMessage2 = new Message("Yeelight", "turnOff", new String[]{});
        Message lcMessage3 = new Message("Yeelight", "setBrightness", new String[]{"50"});
        Message lcMessage4 = new Message("Yeelight", "setRGB", new String[]{"120", "130", "111"});
        taskScheduler.addMessage(lcMessage1);
        taskScheduler.addMessage(lcMessage2);
        taskScheduler.addMessage(lcMessage3);
        taskScheduler.addMessage(lcMessage4);

        taskScheduler.start();
        sleep(2000);
        taskScheduler.shutdown();
    }

}
