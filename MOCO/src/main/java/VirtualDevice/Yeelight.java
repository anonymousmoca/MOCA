package VirtualDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Yeelight {
    private static final Logger LOGGER = LogManager.getLogger();
    private int brightness;
    private int colorMode;
    private boolean powerOn;
    private int[] rgb;

    public Yeelight() {
        this.rgb = new int[] {-1, -1, -1};
        this.brightness = -1;
        this.colorMode = -1;
        this.powerOn = false;
    }

    public Yeelight(int brightness, int colorMode, boolean isOn, int r, int g, int b) {
        this.brightness = brightness;
        this.colorMode = colorMode;
        this.powerOn = isOn;
        this.rgb = new int[] {r, g, b};
    }

    public Yeelight(int brightness, boolean isOn, int r, int g, int b) {
        this.brightness = brightness;
        this.powerOn = isOn;
        this.rgb = new int[] {r, g, b};
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

    public String setBrightness(int brightness) {
//        if (brightness >= 0 && brightness <= 100) {
//        if (!powerOn)
//            return "success";
        if (powerOn && brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
            return "success";
        } else {
            return "skip";
        }
    }

    public String setRGB(int r, int g, int b) {
//        if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255) {
//        if (!powerOn)
//            return "success";
        if (powerOn && r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255) {
            this.rgb = new int[] {r, g, b};
            return "success";
        } else {
            return "skip";
        }
    }

    public String setColorMode(int colorMode) {
//        if (colorMode >= 1 && colorMode <= 3) {
//        if (!powerOn)
//            return "success";
        if (powerOn && colorMode >= 1 && colorMode <= 3) {
            this.colorMode = colorMode;
            return "success";
        } else {
            return "skip";
        }
    }

    public String toSystemString() {
        boolean brightnessInRange = brightness > 0 && brightness <= 100;
        int rbgInt = rgb[0]+rgb[1]+rgb[2];
        boolean rgbInRange = rbgInt >= 0;
        boolean colorModeInRange = colorMode >= 1 && colorMode <= 3;
        return "Yeelight{" +
                "'powerOn':" + powerOn +
                ", 'brightness':" +  brightnessInRange +
//                ", 'colorMode':" + colorModeInRange +
                ", 'rgb':" + rgbInRange +
                "}";
    }

    @Override
    public String toString() {
        String rgbString = rgb[0]+","+rgb[1]+","+rgb[2];
        return "Yeelight{" +
                "'brightness':" + brightness +
//                ", 'colorMode':" + colorMode +
                ", 'powerOn':" + powerOn +
                ", 'rgb':'" + rgbString +
                "'}";
    }

    public static Yeelight fromString(String state) {
//        LOGGER.info("Yeelight fromString: " + state);
        JSONObject json = JSON.parseObject(state.replace("Yeelight", ""));
        String rbg[] = json.getString("rgb").trim().split(",");
        return new Yeelight(json.getInteger("brightness"), json.getBoolean("powerOn"), Integer.parseInt(rbg[0]), Integer.parseInt(rbg[1]), Integer.parseInt(rbg[2]));
    }

    public static void main(String[] args) {
        String content = new Yeelight(50, true, 255, 255, 255).toString();
        Yeelight test = Yeelight.fromString(content);
        LOGGER.info("Yeelight: " + test.toString());
    }

}

