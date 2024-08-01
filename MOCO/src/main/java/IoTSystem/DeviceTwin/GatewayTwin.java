package IoTSystem.DeviceTwin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

public class GatewayTwin {
    private boolean lightOn;
    private int lightBrightness;
    private boolean alarmOn;
    private int deviceList;

    public GatewayTwin() {
        lightOn = false;
        lightBrightness = 0;
        alarmOn = false;
        deviceList = 0;
    }

    public GatewayTwin(boolean lightOn, int lightBrightness, boolean alarmOn, int deviceList) {
        this.lightOn = lightOn;
        this.lightBrightness = lightBrightness;
        this.alarmOn = alarmOn;
        this.deviceList = deviceList;
    }

    public boolean isLightOn() {
        return lightOn;
    }

    public void setLightOn(boolean lightOn) {
        this.lightOn = lightOn;
    }

    public int getLightBrightness() {
        return lightBrightness;
    }

    public void setLightBrightness(int lightBrightness) {
        this.lightBrightness = lightBrightness;
    }

    public boolean isAlarmOn() {
        return alarmOn;
    }

    public void setAlarmOn(boolean alarmOn) {
        this.alarmOn = alarmOn;
    }

    public int getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(int deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public String toString() {
        return "GatewayTwin{" +
                "'lightOn':" + lightOn +
                ", 'lightBrightness':" +  lightBrightness +
                ", 'alarmOn':" + alarmOn +
                ", 'deviceList':" + deviceList +
                "}";
    }

    public String toSystemString() {
        boolean brightnessInRange = lightBrightness > 0 && lightBrightness <= 100;
        return "GatewayTwin{" +
                "'lightOn':" + lightOn +
                ", 'lightBrightness':" +  brightnessInRange +
                ", 'alarmOn':" + alarmOn +
                ", 'deviceList':" + deviceList +
                "}";
    }

    public void setTargetState(String input) {
        JSONObject json = JSON.parseObject(input.replace("Gateway", ""));
        this.lightOn = Boolean.parseBoolean(json.get("lightOn").toString());
        this.alarmOn = Boolean.parseBoolean(json.get("alarmOn").toString());
    }

    public String toSystemDeviceString() {
        boolean brightnessInRange = lightBrightness > 0 && lightBrightness <= 100;
        return "Gateway{" +
                "'lightOn':" + lightOn +
                ", 'lightBrightness':" +  brightnessInRange +
                ", 'alarmOn':" + alarmOn +
                ", 'deviceList':" + deviceList +
                "}";
    }

    public static GatewayTwin fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("GatewayTwin", ""));
        return new GatewayTwin(json.getBoolean("lightOn"), json.getInteger("lightBrightness"), json.getBoolean("alarmOn"), json.getInteger("deviceList"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GatewayTwin)) return false;
        GatewayTwin that = (GatewayTwin) o;
        return isLightOn() == that.isLightOn() && getLightBrightness() == that.getLightBrightness() && isAlarmOn() == that.isAlarmOn() && getDeviceList() == that.getDeviceList();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isLightOn(), getLightBrightness(), isAlarmOn(), getDeviceList());
    }
}
