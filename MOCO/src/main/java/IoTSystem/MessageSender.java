package IoTSystem;

import MOCO.MessageProxy;
import static IoTSystem.TaskScheduler.sleep;

public class MessageSender {

    public static void sendMessageYeelight(MessageProxy messageProxy) {
        Message lcMessage1 = new Message("Yeelight", "turnOn", new String[]{});
        String currentState = Main.getCurrentStateBasedOnMsg(lcMessage1);
        boolean flag1 = messageProxy.addMessage(lcMessage1, currentState);
        if (flag1)
            sleep(100);

        Message lcMessage2 = new Message("Yeelight", "turnOff", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(lcMessage2);
        boolean flag2 = messageProxy.addMessage(lcMessage2, currentState);
        if (flag2)
            sleep(100);

        Message lcMessage5 = new Message("Yeelight", "turnOn", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(lcMessage1);
        boolean flag3 = messageProxy.addMessage(lcMessage5, currentState);
        if (flag3)
            sleep(100);

        Message lcMessage3 = new Message("Yeelight", "setBrightness", new String[]{"50"});
        currentState = Main.getCurrentStateBasedOnMsg(lcMessage3);
        boolean flag4 = messageProxy.addMessage(lcMessage3, currentState);
        if (flag4)
            sleep(100);

        Message lcMessage4 = new Message("Yeelight", "setRGB", new String[]{"120", "130", "111"});
        currentState = Main.getCurrentStateBasedOnMsg(lcMessage4);
        boolean flag5 = messageProxy.addMessage(lcMessage4, currentState);
        if (flag5)
            sleep(100);
    }

    public static void sendMessageVideoCamera(MessageProxy messageProxy) {
        Message vcMessage1 = new Message("VideoCamera", "turnOn", new String[]{});
        String currentState = Main.getCurrentStateBasedOnMsg(vcMessage1);
        boolean flag = messageProxy.addMessage(vcMessage1, currentState);
        if (flag)
            sleep(100);

        Message vcMessage2 = new Message("VideoCamera", "turnOnLight", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(vcMessage2);
        flag = messageProxy.addMessage(vcMessage2, currentState);
        if (flag)
            sleep(100);

        Message vcMessage3 = new Message("VideoCamera", "turnOnFullColor", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(vcMessage3);
        flag = messageProxy.addMessage(vcMessage3, currentState);
        if (flag)
            sleep(100);

        Message vcMessage4 = new Message("VideoCamera", "turnOnFlip", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(vcMessage4);
        flag = messageProxy.addMessage(vcMessage4, currentState);
        if (flag)
            sleep(100);

        Message vcMessage5 = new Message("VideoCamera", "turnOnImproveProgram", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(vcMessage5);
        flag = messageProxy.addMessage(vcMessage5, currentState);
        if (flag)
            sleep(100);

        Message vcMessage6 = new Message("VideoCamera", "turnOnWdr", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(vcMessage6);
        flag = messageProxy.addMessage(vcMessage6, currentState);
        if (flag)
            sleep(100);

        Message vcMessage7 = new Message("VideoCamera", "turnOnTrack", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(vcMessage7);
        flag = messageProxy.addMessage(vcMessage7, currentState);
        if (flag)
            sleep(100);

        Message vcMessage8 = new Message("VideoCamera", "turnOnTrack", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(vcMessage8);
        flag = messageProxy.addMessage(vcMessage8, currentState);
        if (flag)
            sleep(100);

        Message vcMessage9 = new Message("VideoCamera", "turnOff", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(vcMessage9);
        flag = messageProxy.addMessage(vcMessage9, currentState);
        if (flag)
            sleep(100);

        Message vcMessage10 = new Message("VideoCamera", "turnOffTrack", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(vcMessage10);
        flag = messageProxy.addMessage(vcMessage10, currentState);
        if (flag)
            sleep(100);
    }

    public static void sendMessageGateway(MessageProxy messageProxy) {
        Message gwMessage0 = new Message("Gateway", "turnLightOn", new String[]{});
        String currentState = Main.getCurrentStateBasedOnMsg(gwMessage0);
        boolean flag = messageProxy.addMessage(gwMessage0, currentState);
        if (flag)
            sleep(100);

        Message gwMessage1 = new Message("Gateway", "turnLightOff", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(gwMessage1);
        flag = messageProxy.addMessage(gwMessage1, currentState);
        if (flag)
            sleep(100);

        Message gwMessage2 = new Message("Gateway", "turnLightOn", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(gwMessage2);
        flag = messageProxy.addMessage(gwMessage2, currentState);
        if (flag)
            sleep(100);

        Message gwMessage3 = new Message("Gateway", "setLightBrightness", new String[]{"50"});
        currentState = Main.getCurrentStateBasedOnMsg(gwMessage3);
        flag = messageProxy.addMessage(gwMessage3, currentState);
        if (flag)
            sleep(100);

        Message gwMessage4 = new Message("Gateway", "turnAlarmOn", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(gwMessage4);
        flag = messageProxy.addMessage(gwMessage4, currentState);
        if (flag)
            sleep(100);

        Message gwMessage5 = new Message("Gateway", "turnAlarmOff", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(gwMessage5);
        flag = messageProxy.addMessage(gwMessage5, currentState);
        if (flag)
            sleep(100);

        Message gwMessage6 = new Message("Gateway", "addDevice", new String[]{"device1"});
        currentState = Main.getCurrentStateBasedOnMsg(gwMessage6);
        flag = messageProxy.addMessage(gwMessage6, currentState);
        if (flag)
            sleep(100);

        Message gwMessage7 = new Message("Gateway", "removeDevice", new String[]{"device1"});
        currentState = Main.getCurrentStateBasedOnMsg(gwMessage7);
        flag = messageProxy.addMessage(gwMessage7, currentState);
        if (flag)
            sleep(100);

        Message gwMessage8 = new Message("Gateway", "turnLightOn", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(gwMessage0);
        flag = messageProxy.addMessage(gwMessage8, currentState);
        if (flag)
            sleep(100);

        Message gwMessage9 = new Message("Gateway", "addDevice", new String[]{"device1"});
        currentState = Main.getCurrentStateBasedOnMsg(gwMessage9);
        flag = messageProxy.addMessage(gwMessage9, currentState);
        if (flag)
            sleep(100);
    }

    public static void sendMessageWashingMachine(MessageProxy messageProxy) {
        Message wmMessage1 = new Message("WashingMachine", "turnOn", new String[]{});
        String currentState = Main.getCurrentStateBasedOnMsg(wmMessage1);
        boolean flag = messageProxy.addMessage(wmMessage1, currentState);
        if (flag)
            sleep(100);

        Message wmMessage2 = new Message("WashingMachine", "openDoor", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(wmMessage2);
        flag = messageProxy.addMessage(wmMessage2, currentState);
        if (flag)
            sleep(100);

        Message wmMessage3 = new Message("WashingMachine", "closeDoor", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(wmMessage3);
        flag = messageProxy.addMessage(wmMessage3, currentState);
        if (flag)
            sleep(100);

        Message wmMessage4 = new Message("WashingMachine", "fillWater", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(wmMessage4);
        flag = messageProxy.addMessage(wmMessage4, currentState);
        if (flag)
            sleep(100);

        Message wmMessage5 = new Message("WashingMachine", "startWashing", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(wmMessage5);
        flag = messageProxy.addMessage(wmMessage5, currentState);
        if (flag)
            sleep(100);

        Message wmMessage6 = new Message("WashingMachine", "startRinsing", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(wmMessage6);
        flag = messageProxy.addMessage(wmMessage6, currentState);
        if (flag)
            sleep(100);

        Message wmMessage7 = new Message("WashingMachine", "startSpinning", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(wmMessage7);
        flag = messageProxy.addMessage(wmMessage7, currentState);
        if (flag)
            sleep(100);

        Message wmMessage8 = new Message("WashingMachine", "stop", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(wmMessage8);
        flag = messageProxy.addMessage(wmMessage8, currentState);
        if (flag)
            sleep(100);

        Message wmMessage9 = new Message("WashingMachine", "turnOff", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(wmMessage9);
        flag = messageProxy.addMessage(wmMessage9, currentState);
        if (flag)
            sleep(100);

        Message wmMessage10 = new Message("WashingMachine", "stop", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(wmMessage10);
        flag = messageProxy.addMessage(wmMessage10, currentState);
        if (flag)
            sleep(100);
    }

    public static void sendMessageCoffeeMachine(MessageProxy messageProxy) {
        Message cmMessage1 = new Message("CoffeeMachine", "turnOn", new String[]{});
        String currentState = Main.getCurrentStateBasedOnMsg(cmMessage1);
        boolean flag = messageProxy.addMessage(cmMessage1, currentState);
        if (flag)
            sleep(100);

        Message cmMessage2 = new Message("CoffeeMachine", "turnOff", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(cmMessage2);
        flag = messageProxy.addMessage(cmMessage2, currentState);
        if (flag)
            sleep(100);

        Message cmMessage3 = new Message("CoffeeMachine", "addWater", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(cmMessage3);
        flag = messageProxy.addMessage(cmMessage3, currentState);
        if (flag)
            sleep(100);

        Message cmMessage4 = new Message("CoffeeMachine", "addCoffeeBean", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(cmMessage4);
        flag = messageProxy.addMessage(cmMessage4, currentState);
        if (flag)
            sleep(100);

        Message cmMessage5 = new Message("CoffeeMachine", "addMilk", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(cmMessage5);
        flag = messageProxy.addMessage(cmMessage5, currentState);
        if (flag)
            sleep(100);

        Message cmMessage6 = new Message("CoffeeMachine", "placeCup", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(cmMessage6);
        flag = messageProxy.addMessage(cmMessage6, currentState);
        if (flag)
            sleep(100);

        Message cmMessage7 = new Message("CoffeeMachine", "fetchCoffee", new String[]{});
        currentState = Main.getCurrentStateBasedOnMsg(cmMessage7);
        flag = messageProxy.addMessage(cmMessage7, currentState);
        if (flag)
            sleep(100);

        for (int i = 1; i <= 3; i++) {
            Message cmMessageBrew = new Message("CoffeeMachine", "brewCoffee" , new String[]{i+""});
            currentState = Main.getCurrentStateBasedOnMsg(cmMessageBrew);
            flag = messageProxy.addMessage(cmMessageBrew, currentState);
            if (flag)
                sleep(100);
        }
    }
}
