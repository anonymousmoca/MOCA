package IoTSystem.DeviceTwin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

public class WMTwin {
    private boolean powerOn;
    private boolean doorOpen;
    private boolean waterIn;
    private boolean washing;
    private boolean rinsing;
    private boolean spinning;

    public WMTwin() {
        this.powerOn = false;
        this.doorOpen = false;
        this.waterIn = false;
        this.washing = false;
        this.rinsing = false;
        this.spinning = false;
    }

    public WMTwin(Boolean powerOn, Boolean doorOpen, Boolean waterIn, Boolean washing, Boolean rinsing, Boolean spinning) {
        this.powerOn = powerOn;
        this.doorOpen = doorOpen;
        this.waterIn = waterIn;
        this.washing = washing;
        this.rinsing = rinsing;
        this.spinning = spinning;
    }

    public boolean isPowerOn() {
        return powerOn;
    }

    public void setPowerOn(boolean powerOn) {
        this.powerOn = powerOn;
    }

    public boolean isDoorOpen() {
        return doorOpen;
    }

    public void setDoorOpen(boolean doorOpen) {
        this.doorOpen = doorOpen;
    }

    public boolean isWaterIn() {
        return waterIn;
    }

    public void setWaterIn(boolean waterIn) {
        this.waterIn = waterIn;
    }

    public boolean isWashing() {
        return washing;
    }

    public void setWashing(boolean washing) {
        this.washing = washing;
    }

    public boolean isRinsing() {
        return rinsing;
    }

    public void setRinsing(boolean rinsing) {
        this.rinsing = rinsing;
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
    }

    @Override
    public String toString() {
        return "WMTwin{" +
                "'powerOn':" + powerOn +
                ", 'doorOpen':" + doorOpen +
                ", 'waterIn':" + waterIn +
                ", 'washing':" + washing +
                ", 'rinsing':" + rinsing +
                ", 'spinning':" + spinning +
                "}";
    }

    public void setTargetState(String input) {
        JSONObject json = JSON.parseObject(input.replace("WashingMachine", ""));
        this.powerOn = json.getBoolean("powerOn");
        this.doorOpen = json.getBoolean("doorOpen");
        this.waterIn = json.getBoolean("waterIn");
        this.washing = json.getBoolean("washing");
        this.rinsing = json.getBoolean("rinsing");
        this.spinning = json.getBoolean("spinning");
    }
    public String toDeviceString() {
        return "WashingMachine{" +
                "'powerOn':" + powerOn +
                ", 'doorOpen':" + doorOpen +
                ", 'waterIn':" + waterIn +
                ", 'washing':" + washing +
                ", 'rinsing':" + rinsing +
                ", 'spinning':" + spinning +
                "}";
    }

    public static WMTwin fromString(String input) {
        JSONObject json = JSON.parseObject(input.replace("WMTwin", ""));
        return new WMTwin(json.getBoolean("powerOn"), json.getBoolean("doorOpen"), json.getBoolean("waterIn"), json.getBoolean("washing"), json.getBoolean("rinsing"), json.getBoolean("spinning"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WMTwin)) return false;
        WMTwin wmTwin = (WMTwin) o;
        return isPowerOn() == wmTwin.isPowerOn() && isDoorOpen() == wmTwin.isDoorOpen() && isWaterIn() == wmTwin.isWaterIn() && isWashing() == wmTwin.isWashing() && isRinsing() == wmTwin.isRinsing() && isSpinning() == wmTwin.isSpinning();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPowerOn(), isDoorOpen(), isWaterIn(), isWashing(), isRinsing(), isSpinning());
    }
}
