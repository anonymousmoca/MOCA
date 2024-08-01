package IoTSystem.DeviceController;

import IoTSystem.DeviceTwin.WMTwin;
import VirtualDevice.WashingMachine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WMController {
    private static final Logger LOGGER = LogManager.getLogger();
    String deviceId;
    WashingMachine wm;
    WMTwin wmTwin;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public WashingMachine getWm() {
        return wm;
    }

    public void setWm(WashingMachine wm) {
        this.wm = wm;
    }

    public WMTwin getWmTwin() {
        return wmTwin;
    }

    public void setWmTwin(WMTwin wmTwin) {
        this.wmTwin = wmTwin;
    }

    public WMController(String deviceId, WashingMachine wm, WMTwin wmTwin) {
        this.deviceId = deviceId;
        this.wm = wm;
        this.wmTwin = wmTwin;
    }

    public void turnOn() {
        String result = wm.turnOn();
        if (result.equals("success"))
            wmTwin.setPowerOn(true);
    }

    public void turnOff() {
        String result = wm.turnOff();
        if (result.equals("success"))
            wmTwin.setPowerOn(false);
    }

    public void openDoor() {
        String result = wm.openDoor();
        if (result.equals("success"))
            wmTwin.setDoorOpen(true);
    }

    public void closeDoor() {
        String result = wm.closeDoor();
        if (result.equals("success"))
            wmTwin.setDoorOpen(false);
    }

    public void fillWater() {
        String result = wm.fillWater();
        if (result.equals("success"))
            wmTwin.setWaterIn(true);
    }

    public void startWashing() {
        String result = wm.startWashing();
        if (result.equals("success"))
            wmTwin.setWashing(true);
    }

    public void startRinsing() {
        String result = wm.startRinsing();
        if (result.equals("success"))
            wmTwin.setRinsing(true);
    }

    public void startSpinning() {
        String result = wm.startSpinning();
        if (result.equals("success"))
            wmTwin.setSpinning(true);
    }

    public void stop() {
        String result = wm.stop();
        if (result.equals("success")) {
            wmTwin.setWashing(false);
            wmTwin.setRinsing(false);
            wmTwin.setSpinning(false);
        }
    }


    public void printInternalState(){
        LOGGER.info("Device ID: " + deviceId);
        String wm2String = wm.toString();
        LOGGER.info("Washing Machine: " + wm2String);
        String wmTwin2String = wmTwin.toString();
        LOGGER.info("Washing Machine Twin: " + wmTwin2String);
        boolean equal = wm2String.replace("WashingMachine","").equals(wmTwin2String.replace("WMTwin",""));
        LOGGER.info("Equal: " + equal);
    }

    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine();
        WMTwin wmTwin = new WMTwin();
        WMController wmController = new WMController("wm1", wm, wmTwin);
        wmController.printInternalState();
        wmController.turnOff();
        LOGGER.info("[*] Turn off the washing machine");
        wmController.printInternalState();
        wmController.turnOn();
        LOGGER.info("[*] Turn on the washing machine");
        wmController.printInternalState();
        wmController.openDoor();
        LOGGER.info("[*] Open the door");
        wmController.printInternalState();
        wmController.closeDoor();
        LOGGER.info("[*] Close the door");
        wmController.printInternalState();
        wmController.fillWater();
        LOGGER.info("[*] Fill water");
        wmController.printInternalState();

//        wmController.turnOff();
//        LOGGER.info("[*] Turn off the washing machine");
//        wmController.printInternalState();

        wmController.startWashing();
        LOGGER.info("[*] Start washing");
        wmController.printInternalState();
        wmController.startRinsing();
        LOGGER.info("[*] Start rinsing");
        wmController.printInternalState();
        wmController.startSpinning();
        LOGGER.info("[*] Start spinning");
        wmController.printInternalState();
        wmController.stop();
        LOGGER.info("[*] Stop the washing machine");
        wmController.printInternalState();
    }

}
