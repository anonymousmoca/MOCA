package VirtualDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class CoffeeMachine {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean waterReady;
    private boolean beanReady;
    private boolean milkReady;

    private boolean cupReady;
    private boolean thisTimeCoffeeReady;

    private boolean isWorking;
    private boolean isPowerOn;

    private static final int MAX_WATER_VOLUME = 1800;
    private static final int MAX_COFFEE_BEAN_VOLUME = 250;
    private static final int MAX_MILK_VOLUME = 400;

    private int currentWaterVolume;
    private int currentBeanVolume;
    private int currentMilkVolume;

    // Fixed Resource Consumption
    // Type 1. Americano: 15g coffee, 240ml water, 0ml milk
    // Type 2. Latte: 15g coffee, 40ml water, 200ml milk
    // Type 3. Cappuccino: 15g coffee, 40ml water, 100ml milk, 100ml milk foam (consumes 40ml milk)

    public CoffeeMachine() {
        this.waterReady = false;
        this.beanReady = false;
        this.milkReady = false;
        this.cupReady = false;
        this.thisTimeCoffeeReady = false;
        this.isWorking = false;
        this.isPowerOn = false;
        this.currentWaterVolume = 0;
        this.currentBeanVolume = 0;
        this.currentMilkVolume = 0;
    }

    public CoffeeMachine(boolean waterReady, boolean beanReady, boolean milkReady, boolean cupReady, boolean thisTimeCoffeeReady, boolean isWorking, boolean isPowerOn, int currentWaterVolume, int currentBeanVolume, int currentMilkVolume) {
        this.waterReady = waterReady;
        this.beanReady = beanReady;
        this.milkReady = milkReady;
        this.cupReady = cupReady;
        this.thisTimeCoffeeReady = thisTimeCoffeeReady;
        this.isWorking = isWorking;
        this.isPowerOn = isPowerOn;
        this.currentWaterVolume = currentWaterVolume;
        this.currentBeanVolume = currentBeanVolume;
        this.currentMilkVolume = currentMilkVolume;
    }

    public CoffeeMachine(Object waterReady, String beanReady, String milkReady, String cupReady, String thisTimeCoffeeReady, String isWorking, String isPowerOn, String currentWaterVolume, String currentBeanVolume, String currentMilkVolume) {
        this.waterReady = Boolean.parseBoolean(waterReady.toString());
        this.beanReady = Boolean.parseBoolean(beanReady);
        this.milkReady = Boolean.parseBoolean(milkReady);
        this.cupReady = Boolean.parseBoolean(cupReady);
        this.thisTimeCoffeeReady = Boolean.parseBoolean(thisTimeCoffeeReady);
        this.isWorking = Boolean.parseBoolean(isWorking);
        this.isPowerOn = Boolean.parseBoolean(isPowerOn);
        this.currentWaterVolume = Integer.parseInt(currentWaterVolume);
        this.currentBeanVolume = Integer.parseInt(currentBeanVolume);
        this.currentMilkVolume = Integer.parseInt(currentMilkVolume);
    }

    public boolean isWaterReady() {
        return waterReady;
    }

    public void setWaterReady(boolean waterReady) {
        this.waterReady = waterReady;
    }

    public boolean isBeanReady() {
        return beanReady;
    }

    public void setBeanReady(boolean beanReady) {
        this.beanReady = beanReady;
    }

    public boolean isMilkReady() {
        return milkReady;
    }

    public void setMilkReady(boolean milkReady) {
        this.milkReady = milkReady;
    }

    public boolean isCupReady() {
        return cupReady;
    }

    public void setCupReady(boolean cupReady) {
        this.cupReady = cupReady;
    }

    public boolean isThisTimeCoffeeReady() {
        return thisTimeCoffeeReady;
    }

    public void setThisTimeCoffeeReady(boolean thisTimeCoffeeReady) {
        this.thisTimeCoffeeReady = thisTimeCoffeeReady;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public void setPowerOn(boolean powerOn) {
        isPowerOn = powerOn;
    }

    public boolean isPowerOn() {
        return isPowerOn;
    }

    public int getCurrentWaterVolume() {
        return currentWaterVolume;
    }

    public void setCurrentWaterVolume(int currentWaterVolume) {
        this.currentWaterVolume = currentWaterVolume;
    }

    public int getCurrentBeanVolume() {
        return currentBeanVolume;
    }

    public void setCurrentBeanVolume(int currentBeanVolume) {
        this.currentBeanVolume = currentBeanVolume;
    }

    public int getCurrentMilkVolume() {
        return currentMilkVolume;
    }

    public void setCurrentMilkVolume(int currentMilkVolume) {
        this.currentMilkVolume = currentMilkVolume;
    }

    public String turnOn() {
        if (!isPowerOn) {
            isPowerOn = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOff() {
        if (isPowerOn) {
            isPowerOn = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String addWater(){
        if (isPowerOn){
            int addWaterVolume = new Random().nextInt(MAX_WATER_VOLUME);
            currentWaterVolume = Math.min(currentWaterVolume + addWaterVolume, MAX_WATER_VOLUME);
            if (currentWaterVolume > 250) {
                waterReady = true;
            }
            return "success";
        }else {
            return "skip";
        }
    }

    public String addCoffeeBean(){
        if (isPowerOn) {
            int addCoffeeBeanVolume = new Random().nextInt(MAX_COFFEE_BEAN_VOLUME);
            currentBeanVolume = Math.min(currentBeanVolume + addCoffeeBeanVolume, MAX_COFFEE_BEAN_VOLUME);
            if (currentBeanVolume > 15) {
                beanReady = true;
            }
            return "success";
        }else {
            return "skip";
        }
    }

    public String addMilk(){
        if (isPowerOn) {
            int addMilkVolume = new Random().nextInt(MAX_MILK_VOLUME);
            currentMilkVolume = Math.min(currentMilkVolume + addMilkVolume, MAX_MILK_VOLUME);
            if (currentMilkVolume > 200) {
                milkReady = true;
            }
            return "success";
        }else {
            return "skip";
        }
    }

    public String placeCup(){
        if (!cupReady){
            this.cupReady = true;
            return "success";
        }else {
            return "skip";
        }
    }

    public String brewCoffee(int coffeeType) {
        if (!isPowerOn){
            return "Please turn on the machine first.";
        }
        if (!cupReady){
            return "Please place a cup first.";
        }
        if (thisTimeCoffeeReady){
            return "Please take the previous coffee first.";
        }
        switch (coffeeType){
            case 1:
                if (currentWaterVolume < 240){
                    this.waterReady = false;
                    return "Please add water first.";
                }
                if (currentBeanVolume < 15){
                    this.beanReady = false;
                    return "Please add coffee beans first.";
                }
                break;
            case 2:
                if (currentWaterVolume < 40){
                    this.waterReady = false;
                    return "Please add water first.";
                }
                if (currentBeanVolume < 15){
                    this.beanReady = false;
                    return "Please add coffee beans first.";
                }
                if (currentMilkVolume < 200){
                    this.milkReady = false;
                    return "Please add milk first.";
                }
                break;
            case 3:
                if (currentWaterVolume < 40){
                    this.waterReady = false;
                    return "Please add water first.";
                }
                if (currentBeanVolume < 15){
                    this.beanReady = false;
                    return "Please add coffee beans first.";
                }
                if (currentMilkVolume < 140){
                    this.milkReady = false;
                    return "Please add milk first.";
                }
                break;
            default:
                return "Please enter a valid coffee type.";
        }
        makingCoffee(coffeeType);
        this.thisTimeCoffeeReady = true;
        return "Success";
    }

    private String makingCoffee(int coffeeType){
        this.isWorking = true;
        switch (coffeeType){
            case 1:
                currentWaterVolume -= 240;
                currentBeanVolume -= 15;
                break;
            case 2:
                currentWaterVolume -= 40;
                currentBeanVolume -= 15;
                currentMilkVolume -= 200;
                break;
            case 3:
                currentWaterVolume -= 40;
                currentBeanVolume -= 15;
                currentMilkVolume -= 140;
                break;
        }
//        wait(5);
        if (currentBeanVolume < 15){
            this.beanReady = false;
        } else {
            this.beanReady = true;
        }

        if (currentMilkVolume < 200){
            this.milkReady = false;
        } else {
            this.milkReady = true;
        }

        if (currentWaterVolume < 250){
            this.waterReady = false;
        } else {
            this.waterReady = true;
        }

        this.isWorking = false;
        return "success";
    }

    public String fetchCoffee(){
        if (this.thisTimeCoffeeReady){
            this.thisTimeCoffeeReady = false;
        }
        this.cupReady = false;
        return "success";
    }


    public String toString() {
        return "CoffeeMachine{" +
                "'waterReady':'" + waterReady + '\'' +
                ", 'beanReady':'" + beanReady + '\'' +
                ", 'milkReady':'" + milkReady + '\'' +
                ", 'cupReady':'" + cupReady + '\'' +
                ", 'thisTimeCoffeeReady':'" + thisTimeCoffeeReady + '\'' +
                ", 'isWorking':'" + isWorking + '\'' +
                ", 'isPowerOn':'" + isPowerOn + '\'' +
                ", 'currentWaterVolume':'" + currentWaterVolume + '\'' +
                ", 'currentBeanVolume':'" + currentBeanVolume + '\'' +
                ", 'currentMilkVolume':'" + currentMilkVolume + '\'' +
                '}';
    }

    public static CoffeeMachine fromString(String input) {
        JSONObject json = JSON.parseObject(input.replace("CoffeeMachine", ""));
        return new CoffeeMachine(json.get("waterReady"), json.get("beanReady").toString(), json.get("milkReady").toString(), json.get("cupReady").toString(), json.get("thisTimeCoffeeReady").toString(), json.get("isWorking").toString(), json.get("isPowerOn").toString(), json.get("currentWaterVolume").toString(), json.get("currentBeanVolume").toString(), json.get("currentMilkVolume").toString());
    }

    public String toSystemStateString() {
        return "CoffeeMachine{" +
                "'waterReady':'" + waterReady + '\'' +
                ", 'beanReady':'" + beanReady + '\'' +
                ", 'milkReady':'" + milkReady + '\'' +
                ", 'cupReady':'" + cupReady + '\'' +
                ", 'thisTimeCoffeeReady':'" + thisTimeCoffeeReady + '\'' +
                ", 'isWorking':'" + isWorking + '\'' +
                ", 'isPowerOn':'" + isPowerOn + '\'' +
                '}';
    }

    public JSONObject getJsonState() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("waterReady", waterReady);
        jsonObject.put("beanReady", beanReady);
        jsonObject.put("milkReady", milkReady);
        jsonObject.put("cupReady", cupReady);
        jsonObject.put("thisTimeCoffeeReady", thisTimeCoffeeReady);
        jsonObject.put("isWorking", isWorking);
        jsonObject.put("isPowerOn", isPowerOn);
        jsonObject.put("currentWaterVolume", currentWaterVolume);
        jsonObject.put("currentBeanVolume", currentBeanVolume);
        jsonObject.put("currentMilkVolume", currentMilkVolume);
        return jsonObject;
    }

    private void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // Test power on and power off functionality
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.turnOn();
        // Verify the power on state
        assert coffeeMachine.isPowerOn();
        coffeeMachine.turnOff();
        // Verify the power off state
        assert !coffeeMachine.isPowerOn();

        // Test water adding functionality
        coffeeMachine.turnOn();

        coffeeMachine.addWater();
        // Verify that the current water volume is less than or equal to the maximum water volume
        assert coffeeMachine.getCurrentWaterVolume() <= CoffeeMachine.MAX_WATER_VOLUME;

        // Add water twice continuously, and verify that the current water volume is less than or equal to the maximum water volume
        coffeeMachine.addWater();
        assert coffeeMachine.getCurrentWaterVolume() <= CoffeeMachine.MAX_WATER_VOLUME;

        // Test coffee bean adding functionality
        coffeeMachine.addCoffeeBean();
        // Verify that the current coffee bean volume is less than or equal to the maximum coffee bean volume
        assert coffeeMachine.getCurrentBeanVolume() <= CoffeeMachine.MAX_COFFEE_BEAN_VOLUME;

        // Add coffee beans twice continuously, and verify that the current coffee bean volume is less than or equal to the maximum coffee bean volume
        coffeeMachine.addCoffeeBean();
        assert coffeeMachine.getCurrentBeanVolume() <= CoffeeMachine.MAX_COFFEE_BEAN_VOLUME;

        // Test milk adding functionality
        coffeeMachine.addMilk();
        // Verify that the current milk volume is less than or equal to the maximum milk volume
        assert coffeeMachine.getCurrentMilkVolume() <= CoffeeMachine.MAX_MILK_VOLUME;

        // Add milk twice continuously, and verify that the current milk volume is less than or equal to the maximum milk volume
        coffeeMachine.addMilk();
        assert coffeeMachine.getCurrentMilkVolume() <= CoffeeMachine.MAX_MILK_VOLUME;

        // Test cup placement functionality
        coffeeMachine.placeCup();
        // Verify that the cup readiness state is true
        assert coffeeMachine.isCupReady();

        // Test coffee brewing functionality
        coffeeMachine.brewCoffee(1);
        // Verify that the return result is "Success"
        assert coffeeMachine.isThisTimeCoffeeReady();
        // Verify that the current water volume has decreased by 240
        assert coffeeMachine.getCurrentWaterVolume() == 1800 - 240;
        // Verify that the current coffee bean volume has decreased by 15
        assert coffeeMachine.getCurrentBeanVolume() == 250 - 15;

        coffeeMachine.brewCoffee(2);
        coffeeMachine.brewCoffee(3);
        // Verify that the return result is "Success"
        assert coffeeMachine.isThisTimeCoffeeReady();
        // Verify that the current water volume has decreased by 40 * 2
        assert coffeeMachine.getCurrentWaterVolume() == 1800 - 240 - 40 * 2;
        // Verify that the current coffee bean volume has decreased by 15 * 2
        assert coffeeMachine.getCurrentBeanVolume() == 250 - 15 * 2;
        // Verify that the current milk volume has decreased by 200 + 140
        assert coffeeMachine.getCurrentMilkVolume() == 400 - 200 - 140;

        System.out.println("All tests passed!");
    }
}

