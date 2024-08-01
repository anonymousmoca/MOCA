package IoTSystem.DeviceController;

import IoTSystem.DeviceTwin.CMTwin;
import VirtualDevice.CoffeeMachine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CMController {
    private static final Logger LOGGER = LogManager.getLogger();
    String deviceId;
    CoffeeMachine coffeeMachine;
    CMTwin cmTwin;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public CoffeeMachine getCoffeeMachine() {
        return coffeeMachine;
    }

    public void setCoffeeMachine(CoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
    }

    public CMTwin getCmTwin() {
        return cmTwin;
    }

    public void setCmTwin(CMTwin cmTwin) {
        this.cmTwin = cmTwin;
    }

    public CMController(String deviceId, CoffeeMachine coffeeMachine, CMTwin cmTwin) {
        this.deviceId = deviceId;
        this.coffeeMachine = coffeeMachine;
        this.cmTwin = cmTwin;
    }

    public void turnOn(){
        String result = coffeeMachine.turnOn();
        if (result.equals("success"))
            cmTwin.setPowerOn(true);
    }

    public void turnOff(){
        String result = coffeeMachine.turnOff();
        if (result.equals("success"))
            cmTwin.setPowerOn(false);
    }

    public void addCoffeeBean(){
        String result = coffeeMachine.addCoffeeBean();
        if (result.equals("success")){
            int currentBean = coffeeMachine.getCurrentBeanVolume();
            boolean beanStatus = coffeeMachine.isBeanReady();
            cmTwin.setCurrentBeanVolume(currentBean);
            cmTwin.setBeanReady(beanStatus);
        }
    }

    public void addWater(){
        String result = coffeeMachine.addWater();
        if (result.equals("success")){
            int currentWater = coffeeMachine.getCurrentWaterVolume();
            boolean waterStatus = coffeeMachine.isWaterReady();
            cmTwin.setCurrentWaterVolume(currentWater);
            cmTwin.setWaterReady(waterStatus);
        }
    }

    public void addMilk(){
        String result = coffeeMachine.addMilk();
        if (result.equals("success")){
            int currentMilk = coffeeMachine.getCurrentMilkVolume();
            boolean milkStatus = coffeeMachine.isMilkReady();
            cmTwin.setCurrentMilkVolume(currentMilk);
            cmTwin.setMilkReady(milkStatus);
        }
    }

    public void placeCup() {
        String result = coffeeMachine.placeCup();
        if (result.equals("success"))
            cmTwin.setCupReady(true);
    }

    public void fetchCoffee(){
        String result = coffeeMachine.fetchCoffee();
        if (result.equals("success")){
            cmTwin.setCupReady(false);
            cmTwin.setThisTimeCoffeeReady(false);
        }
    }

//    public void brewCoffee(Object[] type) {
//        String result = coffeeMachine.brewCoffee((Integer) type[0]);
//        cmTwin.updateAllFromString(coffeeMachine.toString());
//    }

    public void brewCoffee(int type) {
        String result = coffeeMachine.brewCoffee(type);
        cmTwin.updateAllFromString(coffeeMachine.toString());
    }

    public void printInternalState(){
        LOGGER.info("Device ID: " + deviceId);
        String coffeeMachineString = coffeeMachine.toString();
        LOGGER.info("Coffee Machine: " + coffeeMachineString);
        String cmTwinString = cmTwin.toString();
        LOGGER.info("Coffee Machine Twin: " + cmTwinString);
        boolean equal = coffeeMachineString.replace("CoffeeMachine","").equals(cmTwinString.replace("CMTwin",""));
        LOGGER.info("Equal: " + equal);
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        CMTwin cmTwin = new CMTwin();
        CMController cmController = new CMController("CM001", coffeeMachine, cmTwin);
        cmController.turnOn();
        LOGGER.info("[*] Coffee Machine is turned on.");
        cmController.printInternalState();
        cmController.addCoffeeBean();
        LOGGER.info("[*] Coffee bean is added.");
        cmController.printInternalState();
        cmController.addWater();
        LOGGER.info("[*] Water is added.");
        cmController.printInternalState();
        cmController.addMilk();
        LOGGER.info("[*] Milk is added.");
        cmController.printInternalState();
        cmController.placeCup();
        LOGGER.info("[*] Cup is placed.");
        cmController.printInternalState();
        cmController.brewCoffee(1);
        LOGGER.info("[*] Brewing coffee.");
        cmController.printInternalState();
        cmController.fetchCoffee();
        LOGGER.info("[*] Coffee is fetched.");
        cmController.printInternalState();
        cmController.turnOff();
        LOGGER.info("[*] Coffee Machine is turned off.");
        cmController.printInternalState();
    }

}
