package IoTSystem.DeviceTwin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

public class CMTwin {
    private boolean waterReady;
    private boolean beanReady;
    private boolean milkReady;

    private boolean cupReady;
    private boolean thisTimeCoffeeReady;

    private boolean isWorking;
    private boolean isPowerOn;

    private int currentWaterVolume;
    private int currentBeanVolume;
    private int currentMilkVolume;

    public CMTwin(){
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

    public CMTwin(boolean waterReady, boolean beanReady, boolean milkReady, boolean cupReady, boolean thisTimeCoffeeReady, boolean isWorking, boolean isPowerOn, int currentWaterVolume, int currentBeanVolume, int currentMilkVolume) {
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

    public CMTwin(Object waterReady, String beanReady, String milkReady, String cupReady, String thisTimeCoffeeReady, String isWorking, String isPowerOn, String currentWaterVolume, String currentBeanVolume, String currentMilkVolume) {
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

    public boolean isPowerOn() {
        return isPowerOn;
    }

    public void setPowerOn(boolean powerOn) {
        isPowerOn = powerOn;
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

    public String toString() {
        return "CMTwin{" +
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

    public void setTargetState(String input) {
        JSONObject json = JSON.parseObject(input.replace("CoffeeMachine", ""));
        this.waterReady = Boolean.parseBoolean(json.get("waterReady").toString());
        this.beanReady = Boolean.parseBoolean(json.get("beanReady").toString());
        this.milkReady = Boolean.parseBoolean(json.get("milkReady").toString());
        this.cupReady = Boolean.parseBoolean(json.get("cupReady").toString());
        this.thisTimeCoffeeReady = Boolean.parseBoolean(json.get("thisTimeCoffeeReady").toString());
        this.isWorking = Boolean.parseBoolean(json.get("isWorking").toString());
        this.isPowerOn = Boolean.parseBoolean(json.get("isPowerOn").toString());
    }

    public static CMTwin fromString(String input) {
        JSONObject json = JSON.parseObject(input.replace("CMTwin", ""));
        return new CMTwin(json.get("waterReady"), json.get("beanReady").toString(), json.get("milkReady").toString(), json.get("cupReady").toString(), json.get("thisTimeCoffeeReady").toString(), json.get("isWorking").toString(), json.get("isPowerOn").toString(), json.get("currentWaterVolume").toString(), json.get("currentBeanVolume").toString(), json.get("currentMilkVolume").toString());
    }

    public void updateAllFromString (String input) {
        JSONObject json = JSON.parseObject(input.replace("CoffeeMachine", ""));
        this.waterReady = Boolean.parseBoolean(json.get("waterReady").toString());
        this.beanReady = Boolean.parseBoolean(json.get("beanReady").toString());
        this.milkReady = Boolean.parseBoolean(json.get("milkReady").toString());
        this.cupReady = Boolean.parseBoolean(json.get("cupReady").toString());
        this.thisTimeCoffeeReady = Boolean.parseBoolean(json.get("thisTimeCoffeeReady").toString());
        this.isWorking = Boolean.parseBoolean(json.get("isWorking").toString());
        this.isPowerOn = Boolean.parseBoolean(json.get("isPowerOn").toString());
        this.currentWaterVolume = Integer.parseInt(json.get("currentWaterVolume").toString());
        this.currentBeanVolume = Integer.parseInt(json.get("currentBeanVolume").toString());
        this.currentMilkVolume = Integer.parseInt(json.get("currentMilkVolume").toString());
    }

    public String toSystemStateString() {
        return "CMTwin{" +
                "'waterReady':'" + waterReady + '\'' +
                ", 'beanReady':'" + beanReady + '\'' +
                ", 'milkReady':'" + milkReady + '\'' +
                ", 'cupReady':'" + cupReady + '\'' +
                ", 'thisTimeCoffeeReady':'" + thisTimeCoffeeReady + '\'' +
                ", 'isWorking':'" + isWorking + '\'' +
                ", 'isPowerOn':'" + isPowerOn + '\'' +
                '}';
    }

    public String toSystemDeviceString() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CMTwin)) return false;
        CMTwin cmTwin = (CMTwin) o;
        return isWaterReady() == cmTwin.isWaterReady() && isBeanReady() == cmTwin.isBeanReady() && isMilkReady() == cmTwin.isMilkReady() && isCupReady() == cmTwin.isCupReady() && isThisTimeCoffeeReady() == cmTwin.isThisTimeCoffeeReady() && isWorking() == cmTwin.isWorking() && isPowerOn() == cmTwin.isPowerOn() && getCurrentWaterVolume() == cmTwin.getCurrentWaterVolume() && getCurrentBeanVolume() == cmTwin.getCurrentBeanVolume() && getCurrentMilkVolume() == cmTwin.getCurrentMilkVolume();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isWaterReady(), isBeanReady(), isMilkReady(), isCupReady(), isThisTimeCoffeeReady(), isWorking(), isPowerOn(), getCurrentWaterVolume(), getCurrentBeanVolume(), getCurrentMilkVolume());
    }

}
