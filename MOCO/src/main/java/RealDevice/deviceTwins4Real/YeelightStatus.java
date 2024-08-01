package RealDevice.deviceTwins4Real;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YeelightStatus {
    private int brightness;
    private boolean isOn;
    private int[] rgb;

    public YeelightStatus() {
    }

    public YeelightStatus(int brightness, boolean isOn, int r, int g, int b) {
        this.brightness = brightness;
        this.isOn = isOn;
        this.rgb = new int[]{r, g, b};
    }

    // getters and setters
    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    public int[] getRgb() {
        return rgb;
    }

    @Override
    public String toString() {
        String rgbString = rgb[0] + "," + rgb[1] + "," + rgb[2];
        return "Yeelight{" +
                "'brightness':" + brightness +
                ", 'isOn':" + isOn +
                ", 'rgb':'" + rgbString +
                "'}";
    }

    public String toSystemString() {
        boolean brightnessInRange = brightness > 1 && brightness <= 100;
        int rbgInt = rgb[0] + rgb[1] + rgb[2];
        boolean rgbInRange = rbgInt != 765;
        return "Yeelight{" +
                "'brightness':" + brightnessInRange +
                ", 'isOn':" + isOn +
                ", 'rgb':" + rgbInRange +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YeelightStatus)) return false;
        YeelightStatus that = (YeelightStatus) o;
        return getBrightness() == that.getBrightness() && isOn() == that.isOn() && Arrays.equals(getRgb(), that.getRgb());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getBrightness(), isOn());
        result = 31 * result + Arrays.hashCode(getRgb());
        return result;
    }

    public static YeelightStatus fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("Yeelight", ""));
        String[] rbg = json.getString("rgb").trim().split(",");
        return new YeelightStatus(json.getInteger("brightness"), json.getBoolean("isOn"),
                Integer.parseInt(rbg[0]), Integer.parseInt(rbg[1]), Integer.parseInt(rbg[2]));
    }

    public static YeelightStatus parse(String logLine) {
        Pattern brightnessPattern = Pattern.compile("brightness=(\\d+)");
        Matcher brightnessMatcher = brightnessPattern.matcher(logLine);
        int brightness = -1;
        if (brightnessMatcher.find()) {
            brightness = Integer.parseInt(brightnessMatcher.group(1));
        }

        Pattern colorModePattern = Pattern.compile("color_mode=(\\d+)");
        Matcher colorModeMatcher = colorModePattern.matcher(logLine);
        int colorMode = -1;
        if (colorModeMatcher.find()) {
            colorMode = Integer.parseInt(colorModeMatcher.group(1));
        }

        Pattern isOnPattern = Pattern.compile("is_on=([^ ]+)");
        Matcher isOnMatcher = isOnPattern.matcher(logLine);
        boolean isOn = false;
        if (isOnMatcher.find()) {
            isOn = isOnMatcher.group(1).equals("True");
        }

        Pattern rgbPattern = Pattern.compile("rgb=\\((\\d+),\\s*(\\d+),\\s*(\\d+)\\)");
        Matcher rgbMatcher = rgbPattern.matcher(logLine);
        int r = -1, g = -1, b = -1;
        if (rgbMatcher.find()) {
            r = Integer.parseInt(rgbMatcher.group(1));
            g = Integer.parseInt(rgbMatcher.group(2));
            b = Integer.parseInt(rgbMatcher.group(3));
        }

        return new YeelightStatus(brightness, isOn, r, g, b);
    }

    public static void main(String[] args) {
        String log = "<YeelightStatus brightness=50 color_flow_params=None color_flowing=False color_mode=1 color_temp=None delay_off=0 developer_mode=False hsv=None is_on=True lights=[<YeelightSubLight brightness=50 color_flow_params=None color_flowing=False color_mode=1 color_temp=None hsv=None is_on=True rgb=(1, 2, 3) rgb_int=16777215>] moonlight_mode=None moonlight_mode_brightness=None music_mode=False name= rgb=(255, 255, 255) rgb_int=16777215 save_state_on_change=False>";
        YeelightStatus status = YeelightStatus.parse(log);
        System.out.println(status);

        System.out.println(YeelightStatus.fromString("Yeelight{'brightness':50, 'isOn':true, 'rgb':'255,255,255'}"));
    }
}

