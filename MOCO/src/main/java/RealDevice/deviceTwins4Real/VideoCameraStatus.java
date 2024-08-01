package RealDevice.deviceTwins4Real;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VideoCameraStatus {
    private static final Logger LOGGER = LogManager.getLogger();

    private String power;
    private String motion_record;
    private String light;
    private int night_mode;

    public VideoCameraStatus() {
    }

    public VideoCameraStatus(String power, String motion_record, String light, int night_mode) {
        this.power = power;
        this.motion_record = motion_record;
        this.light = light;
        this.night_mode = night_mode;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getMotion_record() {
        return motion_record;
    }

    public void setMotion_record(String motion_record) {
        this.motion_record = motion_record;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public int getNight_mode() {
        return night_mode;
    }

    public void setNight_mode(int night_mode) {
        this.night_mode = night_mode;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "'power':'" + power +
                "', 'motion_record':'" + motion_record +
                "', 'light':'" + light +
                "', 'night_mode':'" + night_mode +
                "'}";
    }

    public String toSystemString() {
        return "Camera{" +
                "'power':'" + power +
                "', 'motion_record':'" + motion_record +
                "', 'light':'" + light +
                "', 'night_mode':'" + night_mode +
                "'}";
    }

    public static VideoCameraStatus fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("Camera", ""));
        return new VideoCameraStatus(json.get("power").toString(), json.get("motion_record").toString(), json.get("light").toString(), Integer.parseInt(json.get("night_mode").toString()));
    }

    public static VideoCameraStatus parse(String logLine) {
        try {
            JSONObject json = JSON.parseObject(logLine);
            return new VideoCameraStatus(json.getString("power"), json.getString("motion_record"), json.getString("light"), json.getInteger("night_mode"));
        } catch (Exception e) {
            LOGGER.error("Error parsing CameraStatus: " + logLine, e);
        }
        return null;
    }

    public static void main(String[] args) {
        String testLine = "{\"power\": \"on\", \"motion_record\": \"off\", \"light\": \"on\", \"full_color\": \"on\", \"flip\": \"off\", \"improve_program\": \"off\", \"wdr\": \"off\", \"track\": \"off\", \"sdcard_status\": \"0\", \"watermark\": \"on\", \"max_client\": \"0\", \"night_mode\": \"0\", \"mini_level\": \"1\"}\n";
        VideoCameraStatus status = new VideoCameraStatus().parse(testLine);
        System.out.println(status.toString());
    }
}
