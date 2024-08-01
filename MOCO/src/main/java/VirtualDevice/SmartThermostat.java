package VirtualDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmartThermostat {
    private static final Logger LOGGER = LogManager.getLogger();
    private double currentTemperature;
    private double targetTemperature;   // [10, 30]
    private String mode; // "cool", "heat", "auto", "off"
    private boolean isOn;

    public SmartThermostat() {
        this.currentTemperature = -1.0;
        this.targetTemperature = -1.0;
        this.mode = "off";
        this.isOn = false;
    }

    public SmartThermostat(double currentTemperature, double targetTemperature, String mode, boolean isOn) {
        this.currentTemperature = currentTemperature;
        this.targetTemperature = targetTemperature;
        this.mode = mode;
        this.isOn = isOn;
    }

    public String turnOn() {
        if (!isOn) {
            isOn = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOff() {
        if (isOn) {
            isOn = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String setTargetTemperature(double targetTemperature) {
        if (isOn && targetTemperature >= 10.0 && targetTemperature <= 30.0) {
            this.targetTemperature = targetTemperature;
            return "success";
        } else {
            return "skip";
        }
    }

    public String setMode(String mode) {
        if (isOn && (mode.equals("cool") || mode.equals("heat") || mode.equals("auto") || mode.equals("off"))) {
            this.mode = mode;
            return "success";
        } else {
            return "skip";
        }
    }

    public String toSystemString() {
        boolean targetTempInRange = targetTemperature >= 10.0 && targetTemperature <= 30.0;
        return "SmartThermostat{" +
                "'isOn':" + isOn +
                ", 'targetTemperature':" + targetTempInRange +
                ", 'mode':" + mode +
                "}";
    }

    @Override
    public String toString() {
        return "SmartThermostat{" +
                "'currentTemperature':" + currentTemperature +
                ", 'targetTemperature':" + targetTemperature +
                ", 'mode':'" + mode + '\'' +
                ", 'isOn':" + isOn +
                "}";
    }

    public static SmartThermostat fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("SmartThermostat", ""));
        return new SmartThermostat(
                json.getDouble("currentTemperature"),
                json.getDouble("targetTemperature"),
                json.getString("mode"),
                json.getBoolean("isOn")
        );
    }

    public static void main(String[] args) {
        String content = new SmartThermostat(20.0, 22.0, "auto", true).toString();
        SmartThermostat test = SmartThermostat.fromString(content);
        LOGGER.info("SmartThermostat: " + test.toString());
    }
}

