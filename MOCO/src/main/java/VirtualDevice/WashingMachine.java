package VirtualDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WashingMachine {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean powerOn;
    private boolean doorOpen;
    private boolean waterIn;
    private boolean washing;
    private boolean rinsing;
    private boolean spinning;

    public WashingMachine() {
        this.powerOn = false;
        this.doorOpen = false;
        this.waterIn = false;
        this.washing = false;
        this.rinsing = false;
        this.spinning = false;
    }

    public WashingMachine(Boolean powerOn, Boolean doorOpen, Boolean waterIn, Boolean washing, Boolean rinsing, Boolean spinning) {
        this.powerOn = powerOn;
        this.doorOpen = doorOpen;
        this.waterIn = waterIn;
        this.washing = washing;
        this.rinsing = rinsing;
        this.spinning = spinning;
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

    public String openDoor() {
        if (!doorOpen) {
            doorOpen = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String closeDoor() {
        if (doorOpen) {
            doorOpen = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String fillWater() {
        if (powerOn && !waterIn) {
            waterIn = true;
            return "success";
        } else if (!powerOn) {
            return "skip";
        } else {
            return "skip";
        }
    }

    public String startWashing() {
        if (powerOn && !doorOpen && waterIn && !washing && !rinsing && !spinning) {
            washing = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String startRinsing() {
        if (powerOn && !doorOpen && waterIn && washing && !rinsing && !spinning) {
            rinsing = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String startSpinning() {
        if (powerOn && !doorOpen && waterIn && washing && rinsing && !spinning) {
            spinning = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String stop() {
        if (washing || rinsing || spinning) {
            washing = false;
            rinsing = false;
            spinning = false;
            return "success";
        } else {
            return "skip";
        }
    }

    @Override
    public String toString() {
        return "WashingMachine{" +
                "'powerOn':" + powerOn +
                ", 'doorOpen':" + doorOpen +
                ", 'waterIn':" + waterIn +
                ", 'washing':" + washing +
                ", 'rinsing':" + rinsing +
                ", 'spinning':" + spinning +
                "}";
    }

    public static WashingMachine fromString(String input) {
        JSONObject json = JSON.parseObject(input.replace("WashingMachine", ""));
        return new WashingMachine(json.getBoolean("powerOn"), json.getBoolean("doorOpen"), json.getBoolean("waterIn"), json.getBoolean("washing"), json.getBoolean("rinsing"), json.getBoolean("spinning"));
    }


    public static void main(String[] args) {
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.turnOn();
        washingMachine.fillWater();
        washingMachine.startWashing();
        washingMachine.startRinsing();
        washingMachine.startSpinning();
        washingMachine.stop();
        washingMachine.turnOff();
        LOGGER.info(washingMachine.toString());
    }
}
