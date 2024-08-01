package VirtualDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmartPlug {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean powerOn;
    private int timer; // Timer in minutes

    public SmartPlug() {
        this.powerOn = false;
        this.timer = 0;
    }

    public SmartPlug(boolean powerOn, int timer) {
        this.powerOn = powerOn;
        this.timer = timer;
    }

    public String turnOn() {
        if (!powerOn) {
            powerOn = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOff() {
        if (powerOn) {
            powerOn = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String setTimer(int timer) {
        if (timer >= 0) {
            this.timer = timer;
            return "success";
        } else {
            return "skip";
        }
    }

    public String toSystemString() {
        boolean timerFlag = timer > 0;
        return "SmartPlug{" +
                "'powerOn':" + powerOn +
                ", 'timer':" + timerFlag +
                "}";
    }

    @Override
    public String toString() {
        return "SmartPlug{" +
                "'powerOn':" + powerOn +
                ", 'timer':" + timer +
                "}";
    }

    public static SmartPlug fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("SmartPlug", ""));
        return new SmartPlug(json.getBoolean("powerOn"), json.getInteger("timer"));
    }

    public static void main(String[] args) {
        SmartPlug plug = new SmartPlug();
        LOGGER.info("Initial State: " + plug.toString());

        // Test turning on
        LOGGER.info("Turn on: " + plug.turnOn());
        LOGGER.info("State after turning on: " + plug.toString());

        // Test turning off
        LOGGER.info("Turn off: " + plug.turnOff());
        LOGGER.info("State after turning off: " + plug.toString());

        // Test setting timer
        LOGGER.info("Set timer to 30 minutes: " + plug.setTimer(30));
        LOGGER.info("State after setting timer: " + plug.toString());

        // Test restoring state from string
        String content = plug.toString();
        SmartPlug restoredPlug = SmartPlug.fromString(content);
        LOGGER.info("Restored SmartPlug: " + restoredPlug.toString());
    }
}
