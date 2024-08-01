package IoTSystem.DeviceController;

import IoTSystem.DeviceTwin.GatewayTwin;
import VirtualDevice.Gateway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GatewayController {
    private static final Logger LOGGER = LogManager.getLogger();
    String deviceId;
    Gateway gateway;
    GatewayTwin gatewayTwin;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    public GatewayTwin getGatewayTwin() {
        return gatewayTwin;
    }

    public void setGatewayTwin(GatewayTwin gatewayTwin) {
        this.gatewayTwin = gatewayTwin;
    }

    public GatewayController(String deviceId, Gateway gateway, GatewayTwin gatewayTwin) {
        this.deviceId = deviceId;
        this.gateway = gateway;
        this.gatewayTwin = gatewayTwin;
    }

    public void turnLightOn() {
        String result = gateway.turnLightOn();
        if (result.equals("success"))
            gatewayTwin.setLightOn(true);
    }

    public void turnLightOff() {
        String result = gateway.turnLightOff();
        if (result.equals("success"))
            gatewayTwin.setLightOn(false);
    }

    public void setLightBrightness(int brightness) {
        String result = gateway.setLightBrightness(brightness);
        if (result.equals("success"))
            gatewayTwin.setLightBrightness(brightness);
    }

    public void turnAlarmOn() {
        String result = gateway.turnAlarmOn();
        if (result.equals("success"))
            gatewayTwin.setAlarmOn(true);
    }

    public void turnAlarmOff() {
        String result = gateway.turnAlarmOff();
        if (result.equals("success"))
            gatewayTwin.setAlarmOn(false);
    }

    public void addDevice(String device) {
        String result = gateway.addDevice(device);
        if (result.equals("success")) {
            int deviceList = gatewayTwin.getDeviceList()+1;
            gatewayTwin.setDeviceList(deviceList);
        }
    }

    public void removeDevice(String device) {
        String result = gateway.removeDevice(device);
        if (result.equals("success")) {
            int deviceList = gatewayTwin.getDeviceList()-1;
            gatewayTwin.setDeviceList(deviceList);
        }
    }



    public void printInternalState(){
        LOGGER.info("Device ID: " + deviceId);
        String gateway2String = gateway.toString();
        LOGGER.info("Gateway: " + gateway2String);
        String gatewayTwin2String = gatewayTwin.toString();
        LOGGER.info("Gateway Twin: " + gatewayTwin2String);
        boolean equal = gateway2String.replace("Gateway","").equals(gatewayTwin2String.replace("GatewayTwin",""));
        LOGGER.info("Equal: " + equal);
    }

    public static void main(String[] args) {
        Gateway gateway = new Gateway();
        GatewayTwin gatewayTwin = new GatewayTwin();
        GatewayController transGatewayController = new GatewayController("gateway1", gateway, gatewayTwin);
        transGatewayController.turnLightOn();
        LOGGER.info("[*] Turn light on");
        transGatewayController.printInternalState();
        transGatewayController.setLightBrightness(50);
        LOGGER.info("[*] Set light brightness to 50");
        transGatewayController.printInternalState();
        transGatewayController.turnAlarmOn();
        LOGGER.info("[*] Turn alarm on");
        transGatewayController.printInternalState();
        transGatewayController.addDevice("device1");
        transGatewayController.addDevice("device2");
        transGatewayController.addDevice("device3");
        transGatewayController.printInternalState();
        transGatewayController.removeDevice("device1");
        transGatewayController.turnAlarmOff();
        transGatewayController.turnLightOff();
        LOGGER.info("[*] Turn alarm off and light off");
        transGatewayController.printInternalState();
    }

}
