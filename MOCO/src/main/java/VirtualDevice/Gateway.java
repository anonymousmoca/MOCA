package VirtualDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Gateway {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean lightOn;
    private int lightBrightness;
    private boolean alarmOn;
    private int deviceList;

    public String addDevice(String device) {
        if (deviceList < 3) {
            deviceList++;
            return "success";
        } else {
            return "skip";
        }
    }

    public String removeDevice(String device) {
        if (deviceList > 0) {
            deviceList--;
            return "success";
        } else {
            return "skip";
        }
    }

    public Gateway() {
        lightOn = false;
        lightBrightness = 0;
        alarmOn = false;
    }

    public Gateway (boolean lightOn, int lightBrightness, boolean alarmOn, int deviceList) {
        this.lightOn = lightOn;
        this.lightBrightness = lightBrightness;
        this.alarmOn = alarmOn;
        this.deviceList = deviceList;
    }

    public String turnLightOn() {
        if (!lightOn) {
            lightOn = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnLightOff() {
        if (lightOn) {
            lightOn = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String setLightBrightness(int brightness) {
        if (lightOn && brightness >= 0 && brightness <= 100) {
            lightBrightness = brightness;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnAlarmOn() {
        if (!alarmOn) {
            alarmOn = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnAlarmOff() {
        if (alarmOn) {
            alarmOn = false;
            return "success";
        } else {
            return "skip";
        }
    }


    public String toSystemString() {
        boolean brightnessInRange = lightBrightness > 0 && lightBrightness <= 100;
        return "Gateway{" +
                "'lightOn':" + lightOn +
                ", 'lightBrightness':" +  brightnessInRange +
                ", 'alarmOn':" + alarmOn +
                ", 'deviceList':" + deviceList +
                "}";
    }

    @Override
    public String toString() {
        return "Gateway{" +
                "'lightOn':" + lightOn +
                ", 'lightBrightness':" +  lightBrightness +
                ", 'alarmOn':" + alarmOn +
                ", 'deviceList':" + deviceList +
                "}";
    }

    public static Gateway fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("Gateway", ""));
        return new Gateway(json.getBoolean("lightOn"), json.getInteger("lightBrightness"), json.getBoolean("alarmOn"), json.getInteger("deviceList"));
    }

    public static void main(String[] args) {
        Gateway gateway = new Gateway(true, 50, true, 2);
        Gateway test = Gateway.fromString(gateway.toString());
        LOGGER.info(test.toString());
    }
}
