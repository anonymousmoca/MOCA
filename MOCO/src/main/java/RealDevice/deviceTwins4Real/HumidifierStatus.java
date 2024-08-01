package RealDevice.deviceTwins4Real;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HumidifierStatus {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean isOn; // on or off
    private boolean buzzer; // buzzer on or off
    private boolean cleanMode; // clean mode on or off
    private boolean dry; // dry mode on or off
    private int speed; // [200, 2000] speed % 10 == 0
    private int targetHumidity; // [30, 80]

    public HumidifierStatus() {
    }

    public HumidifierStatus(boolean isOn, boolean buzzer, boolean cleanMode, boolean dry, int speed, int targetHumidity) {
        this.isOn = isOn;
        this.buzzer = buzzer;
        this.cleanMode = cleanMode;
        this.dry = dry;
        setSpeed(speed); // Ensure the speed is valid
        setTargetHumidity(targetHumidity); // Ensure the targetHumidity is within the valid range
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isBuzzer() {
        return buzzer;
    }

    public void setBuzzer(boolean buzzer) {
        this.buzzer = buzzer;
    }

    public boolean isCleanMode() {
        return cleanMode;
    }

    public void setCleanMode(boolean cleanMode) {
        this.cleanMode = cleanMode;
    }

    public boolean isDry() {
        return dry;
    }

    public void setDry(boolean dry) {
        this.dry = dry;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (speed < 200 || speed > 2000 || speed % 10 != 0) {
            throw new IllegalArgumentException("Speed must be between 200 and 2000 and a multiple of 10.");
        }
        this.speed = speed;
    }

    public int getTargetHumidity() {
        return targetHumidity;
    }

    public void setTargetHumidity(int targetHumidity) {
        if (targetHumidity < 30 || targetHumidity > 80) {
            throw new IllegalArgumentException("Target humidity must be between 18 and 28.");
        }
        this.targetHumidity = targetHumidity;
    }

    @Override
    public String toString() {
        return "HumidifierStatus{" +
                "'isOn':" + isOn +
                ", 'buzzer':" + buzzer +
                ", 'cleanMode':" + cleanMode +
                ", 'dry':" + dry +
                ", 'speed':" + speed +
                ", 'targetHumidity':" + targetHumidity +
                "}";
    }

    public String toSystemString() {
        boolean speedInRange = speed > 200 && speed <= 2000 && speed % 10 == 0; // 200 reset
        boolean humidityInRange = targetHumidity > 30 && targetHumidity <= 80;  // 30 reset
        return "HumidifierStatus{" +
                "'isOn':" + isOn +
                ", 'buzzer':" + buzzer +
                ", 'cleanMode':" + cleanMode +
                ", 'dry':" + dry +
                ", 'speed':" + speedInRange +
                ", 'targetHumidity':" + humidityInRange +
                "}";
    }

    public static HumidifierStatus fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("HumidifierStatus", ""));
        return new HumidifierStatus(
                json.getBoolean("isOn"),
                json.getBoolean("buzzer"),
                json.getBoolean("cleanMode"),
                json.getBoolean("dry"),
                json.getInteger("speed"),
                json.getInteger("targetHumidity")
        );
    }

    // Parsing method
    public static HumidifierStatus parse(String logline) {
        try {
            LOGGER.info("Parsing logline: " + logline);
            JSONObject jsonObject = JSON.parseObject(logline);
            HumidifierStatus status = new HumidifierStatus();

            // Extract power field
            Boolean power = jsonObject.getBoolean("power");
            if (power != null) {
                status.setOn(power);
            }

            // Extract buzzer field
            Boolean buzzer = jsonObject.getBoolean("buzzer");
            if (buzzer != null) {
                status.setBuzzer(buzzer);
            }

            // Extract cleanMode field
            Boolean cleanMode = jsonObject.getBoolean("clean_mode");
            if (cleanMode != null) {
                status.setCleanMode(cleanMode);
            }

            // Extract dry field
            Boolean dry = jsonObject.getBoolean("dry");
            if (dry != null) {
                status.setDry(dry);
            }

            // Extract speed field
            Integer speed = jsonObject.getInteger("speed_level");
            if (speed != null) {
                status.setSpeed(speed);
            }

            // Extract targetHumidity field
            Integer targetHumidity = jsonObject.getInteger("target_humidity");
            if (targetHumidity != null) {
                status.setTargetHumidity(targetHumidity);
            }

            return status;
        } catch (Exception e) {
            LOGGER.error("Error parsing HumidifierStatus: " + logline, e);
        }
        return null;
    }

    public static void main(String[] args) {
        String logline = "{\"power\": \"on\", \"buzzer\": 1, \"cleanMode\": 1, \"dry\": 0, \"speed\": 500, \"targetHumidity\": 22}";
        logline = "{\"power\": true, \"fault\": 0, \"mode\": 3, \"target_humidity\": 30, \"water_level\": 127, \"dry\": false, \"use_time\": 1214252, \"button_pressed\": 0, \"speed_level\": 200, \"temperature\": 27.7, \"fahrenheit\": 81.9, \"humidity\": 69, \"buzzer\": true, \"led_brightness\": 1, \"child_lock\": false, \"actual_speed\": 57, \"power_time\": 20048, \"clean_mode\": false}\n";
        HumidifierStatus status = HumidifierStatus.parse(logline);
        System.out.println(status);
    }
}
