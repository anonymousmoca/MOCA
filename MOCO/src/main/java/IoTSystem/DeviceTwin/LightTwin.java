package IoTSystem.DeviceTwin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.Objects;

public class LightTwin {
    private int brightness;
    private int colorMode;
    private boolean powerOn;
    private int[] rgb;

    public LightTwin() {
        this.rgb = new int[] {-1, -1, -1};
        this.brightness = -1;
        this.colorMode = -1;
        this.powerOn = false;
    }

    public LightTwin(int brightness, int colorMode, boolean isOn, int r, int g, int b) {
        this.brightness = brightness;
        this.colorMode = colorMode;
        this.powerOn = isOn;
        this.rgb = new int[] {r, g, b};
    }

    public LightTwin(int brightness, boolean isOn, int r, int g, int b) {
        this.brightness = brightness;
        this.powerOn = isOn;
        this.rgb = new int[] {r, g, b};
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getColorMode() {
        return colorMode;
    }

    public void setColorMode(int colorMode) {
        this.colorMode = colorMode;
    }

    public boolean isPowerOn() {
        return powerOn;
    }

    public void setPowerOn(boolean powerOn) {
        this.powerOn = powerOn;
    }

    public int[] getRgb() {
        return rgb;
    }

    public void setRgb(int r, int g, int b) {
        this.rgb = new int[] {r, g, b};
    }

    public String toSystemString() {
        boolean brightnessInRange = brightness > 0 && brightness <= 100;
        int rbgInt = rgb[0]+rgb[1]+rgb[2];
        boolean rgbInRange = rbgInt >= 0;
        return "LightTwin{" +
                "'powerOn':" + powerOn +
                ", 'brightness':" +  brightnessInRange +
                ", 'rgb':" + rgbInRange +
                "}";
    }

    public void setTargetState(String input) {
        JSONObject json = JSON.parseObject(input.replace("Yeelight", ""));
        this.powerOn = json.getBoolean("powerOn");
    }

    public String toSystemDeviceString() {
        boolean brightnessInRange = brightness > 0 && brightness <= 100;
        int rbgInt = rgb[0]+rgb[1]+rgb[2];
        boolean rgbInRange = rbgInt >= 0;
        return "Yeelight{" +
                "'powerOn':" + powerOn +
                ", 'brightness':" +  brightnessInRange +
                ", 'rgb':" + rgbInRange +
                "}";
    }

    @Override
    public String toString() {
        String rgbString = rgb[0]+","+rgb[1]+","+rgb[2];
        return "LightTwin{" +
                "'brightness':" + brightness +
                ", 'powerOn':" + powerOn +
                ", 'rgb':'" + rgbString +
                "'}";
    }

    public static LightTwin fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("LightTwin", ""));
        String rbg[] = json.getString("rgb").trim().split(",");
        return new LightTwin(json.getInteger("brightness"), json.getBoolean("powerOn"), Integer.parseInt(rbg[0]), Integer.parseInt(rbg[1]), Integer.parseInt(rbg[2]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LightTwin)) return false;
        LightTwin lightTwin = (LightTwin) o;
        return getBrightness() == lightTwin.getBrightness() && getColorMode() == lightTwin.getColorMode() && isPowerOn() == lightTwin.isPowerOn() && Arrays.equals(getRgb(), lightTwin.getRgb());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getBrightness(), getColorMode(), isPowerOn());
        result = 31 * result + Arrays.hashCode(getRgb());
        return result;
    }
}
