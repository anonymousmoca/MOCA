package RealDevice.deviceTwins4Real;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HeaterStatus {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean isOn; // on or off
    // private boolean buzzer; // buzzer true or false
    private int targetTemp; // [18, 28]

    public HeaterStatus() {
    }

    public HeaterStatus(boolean isOn, /*boolean buzzer,*/ int targetTemp) {
        this.isOn = isOn;
        // this.buzzer = buzzer;
        setTargetTemp(targetTemp); // Ensure the targetTemp is within the valid range
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    // public boolean isBuzzer() {
    //     return buzzer;
    // }

    // public void setBuzzer(boolean buzzer) {
    //     this.buzzer = buzzer;
    // }

    public int getTargetTemp() {
        return targetTemp;
    }

    public void setTargetTemp(int targetTemp) {
        if (targetTemp < 18 || targetTemp > 28) {
            throw new IllegalArgumentException("Target temperature must be between 18 and 28.");
        }
        this.targetTemp = targetTemp;
    }

    @Override
    public String toString() {
        return "HeaterStatus{" +
                "'isOn':" + isOn +
                // ", 'buzzer':" + buzzer +
                ", 'targetTemp':" + targetTemp +
                "}";
    }

    public String toSystemString() {
        boolean tempInRange = targetTemp > 18 && targetTemp <= 28;  // 18 reset
        return "HeaterStatus{" +
                "'isOn':" + isOn +
                // ", 'buzzer':" + buzzer +
                ", 'targetTemp':" + tempInRange +
                "}";
    }

    public static HeaterStatus fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("HeaterStatus", ""));
        return new HeaterStatus(json.getBoolean("isOn"), /*json.getBoolean("buzzer"),*/ json.getInteger("targetTemp"));
    }

    // Parsing method
    public static HeaterStatus parse(String logline) {
        try {
            LOGGER.info("Parsing logline: " + logline);
            JSONObject jsonObject = JSON.parseObject(logline);
            HeaterStatus status = new HeaterStatus();

            // Extract power field
            String power = jsonObject.getString("power");
            LOGGER.info("Power: " + power);
            if (power != null) {
                status.setOn(power.equals("true"));
            }

            // Extract buzzer field
            // Boolean buzzer = jsonObject.getBoolean("buzzer");
            // LOGGER.info("Buzzer: " + buzzer);
            // if (buzzer != null) {
            //     status.setBuzzer(buzzer);
            // }

            // Extract targetTemp field
            Integer targetTemp = jsonObject.getInteger("target_temperature");
            if (targetTemp != null) {
                status.setTargetTemp(targetTemp);
            }

            return status;
        } catch (Exception e) {
            LOGGER.error("Error parsing HeaterStatus: " + logline, e);
        }
        return null;
    }

    public static void main(String[] args) {
        String logline = "{\"power\": false, \"target_temperature\": 18, \"countdown_time\": 0, \"temperature\": 26.0, \"relative_humidity\": 61, \"child_lock\": false, \"buzzer\": false, \"led_brightness\": 0}\n";
        logline = "{\"power\": true, \"target_temperature\": 25, \"countdown_time\": 0, \"temperature\": 26.1, \"relative_humidity\": 59, \"child_lock\": false, \"buzzer\": true, \"led_brightness\": 0}\n";
        HeaterStatus status = HeaterStatus.parse(logline);
        System.out.println(status);
    }
}
