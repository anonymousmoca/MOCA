package RealDevice.deviceTwins4Real;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FanStatus {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean isOn; // on or off
    private int speed; // (0, 100], reset -> 1
    private int angle; // [0, 120], reset -> 0

    public FanStatus() {
    }

    public FanStatus(boolean isOn, int speed, int angle) {
        this.isOn = isOn;
        this.speed = speed;
        this.angle = angle;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "FanStatus{" +
                "'isOn':" + isOn +
                ", 'speed':" + speed +
                ", 'angle':" + angle +
                "}";
    }

    public String toSystemString () {
        boolean speedInRange = speed > 1 && speed <= 100;   // 1/0 reset
        boolean angleInRange = angle > 0 && angle <= 120;   // 0 reset
        return "FanStatus{" +
                "'isOn':" + isOn +
                ", 'speed':" + speedInRange +
                ", 'angle':" + angleInRange +
                "}";
    }

    public static FanStatus fromString (String state) {
        JSONObject json = JSON.parseObject(state.replace("FanStatus", ""));
        return new FanStatus(json.getBoolean("isOn"), json.getInteger("speed"), json.getInteger("angle"));
    }

    // Parsing method
    public static FanStatus parse(String logline) {
        try{LOGGER.info("Parsing logline: " + logline);
            JSONObject jsonObject = JSON.parseObject(logline);
            FanStatus status = new FanStatus();

            // Extract power field
            String power = jsonObject.getString("power");
            if (power != null) {
                status.setOn("on".equalsIgnoreCase(power));
            }

            // Extract speed field
            Integer speed = jsonObject.getInteger("speed_level");
            if (speed != null) {
                status.setSpeed(speed);
            }

            // Extract angle field
            Integer angle = jsonObject.getInteger("angle");
            if (angle != null) {
                status.setAngle(angle);
            }

            return status;}
        catch (Exception e){
            LOGGER.error("Error parsing FanStatus: " + logline);
        }
        return null;
    }

    public static void main(String[] args) {
        String logline = "{\"angle\": 0, \"speed\": 305, \"poweroff_time\": 0, \"power\": \"on\", \"ac_power\": \"on\", \"angle_enable\": \"on\", \"speed_level\": 1, \"natural_level\": 0, \"child_lock\": \"off\", \"buzzer\": 0, \"led_b\": 0, \"use_time\": 2455}";
        FanStatus status = FanStatus.parse(logline);
        System.out.println(status);
    }
}
