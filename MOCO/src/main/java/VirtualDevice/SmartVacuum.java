package VirtualDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmartVacuum {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean powerOn;
    private boolean cleaning;
    private int batteryLevel; // Battery level from 0 to 100
    private String mode; // Cleaning mode: "auto", "spot", "edge"

    public SmartVacuum() {
        this.powerOn = false;
        this.cleaning = false;
        this.batteryLevel = 100;
        this.mode = "auto";
    }

    public SmartVacuum(boolean powerOn, boolean cleaning, int batteryLevel, String mode) {
        this.powerOn = powerOn;
        this.cleaning = cleaning;
        this.batteryLevel = batteryLevel;
        this.mode = mode;
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
            cleaning = false; // Stop cleaning when turned off
            return "success";
        } else {
            return "skip";
        }
    }

    public String startCleaning() {
        if (powerOn && !cleaning) {
            cleaning = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String stopCleaning() {
        if (powerOn && cleaning) {
            cleaning = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String setMode(String mode) {
        if (powerOn && (mode.equals("auto") || mode.equals("spot") || mode.equals("edge"))) {
            this.mode = mode;
            return "success";
        } else {
            return "skip";
        }
    }

    public String toSystemString() {
        return "SmartVacuum{" +
                "'powerOn':" + powerOn +
                ", 'cleaning':" + cleaning +
                ", 'batteryLevel':" + batteryLevel +
                ", 'mode':'" + mode + "'" +
                "}";
    }

    @Override
    public String toString() {
        return "SmartVacuum{" +
                "'powerOn':" + powerOn +
                ", 'cleaning':" + cleaning +
                ", 'batteryLevel':" + batteryLevel +
                ", 'mode':'" + mode + "'" +
                "}";
    }

    public static SmartVacuum fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("SmartVacuum", ""));
        return new SmartVacuum(json.getBoolean("powerOn"), json.getBoolean("cleaning"),
                json.getInteger("batteryLevel"), json.getString("mode"));
    }

    public static void main(String[] args) {
        SmartVacuum vacuum = new SmartVacuum();
        LOGGER.info("Initial State: " + vacuum.toString());

        // Test turning on
        LOGGER.info("Turn on: " + vacuum.turnOn());
        LOGGER.info("State after turning on: " + vacuum.toString());

        // Test starting cleaning
        LOGGER.info("Start cleaning: " + vacuum.startCleaning());
        LOGGER.info("State after starting cleaning: " + vacuum.toString());

        // Test setting mode
        LOGGER.info("Set mode to 'spot': " + vacuum.setMode("spot"));
        LOGGER.info("State after setting mode: " + vacuum.toString());

        // Test stopping cleaning
        LOGGER.info("Stop cleaning: " + vacuum.stopCleaning());
        LOGGER.info("State after stopping cleaning: " + vacuum.toString());

        // Test turning off
        LOGGER.info("Turn off: " + vacuum.turnOff());
        LOGGER.info("State after turning off: " + vacuum.toString());

        // Test restoring state from string
        String content = vacuum.toString();
        SmartVacuum restoredVacuum = SmartVacuum.fromString(content);
        LOGGER.info("Restored SmartVacuum: " + restoredVacuum.toString());
    }
}
