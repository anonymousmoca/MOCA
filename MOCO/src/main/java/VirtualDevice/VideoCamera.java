package VirtualDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VideoCamera {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean powerOn;
    private boolean motionRecord;
    private boolean light;
    private boolean fullColor;
    private boolean flip;
    private boolean improveProgram;
    private boolean wdr;
    private boolean track;
    private int sdcardStatus;
    private boolean watermark;
    private int maxClient;
    private int nightMode;
    private int miniLevel;

    public VideoCamera() {
        // Initialize with default values (all off, defaults, etc.)
        this.powerOn = false;
        this.motionRecord = false;
        this.light = false;
        this.fullColor = false;
        this.flip = false;
        this.improveProgram = false;
        this.wdr = false;
        this.track = false;
        this.sdcardStatus = 0;
        this.watermark = false;
        this.maxClient = 0;
        this.nightMode = 0;
        this.miniLevel = 1;
    }

    public VideoCamera(boolean powerOn, boolean motionRecord, boolean light, boolean fullColor, boolean flip, boolean improveProgram, boolean wdr, boolean track, int sdcardStatus, boolean watermark, int maxClient, int nightMode, int miniLevel) {
        this.powerOn = powerOn;
        this.motionRecord = motionRecord;
        this.light = light;
        this.fullColor = fullColor;
        this.flip = flip;
        this.improveProgram = improveProgram;
        this.wdr = wdr;
        this.track = track;
        this.sdcardStatus = sdcardStatus;
        this.watermark = watermark;
        this.maxClient = maxClient;
        this.nightMode = nightMode;
        this.miniLevel = miniLevel;
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

    public String turnOnMotionRecord() {
        if (powerOn) {
            this.motionRecord = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOffMotionRecord() {
        if (powerOn) {
            this.motionRecord = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOnLight() {
        if (powerOn) {
            this.light = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOffLight() {
        if (powerOn) {
            this.light = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOnFullColor() {
        if (powerOn) {
            this.fullColor = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOffFullColor() {
        if (powerOn) {
            this.fullColor = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOnFlip() {
        if (powerOn) {
            this.flip = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOffFlip() {
        if (powerOn) {
            this.flip = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOnImproveProgram() {
        if (powerOn) {
            this.improveProgram = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOffImproveProgram() {
        if (powerOn) {
            this.improveProgram = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOnWdr() {
        if (powerOn) {
            this.wdr = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOffWdr() {
        if (powerOn) {
            this.wdr = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOnTrack() {
        if (powerOn) {
            this.track = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOffTrack() {
        if (powerOn) {
            this.track = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOnWatermark() {
        if (powerOn) {
            this.watermark = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String turnOffWatermark() {
        if (powerOn) {
            this.watermark = false;
            return "success";
        } else {
            return "skip";
        }
    }

    public String setMaxClient(int maxClient) {
        if (powerOn) {
            this.maxClient = maxClient;
            return "success";
        } else {
            return "skip";
        }
    }

    public String setNightMode(int nightMode) {
        if (powerOn) {
            this.nightMode = nightMode;
            return "success";
        } else {
            return "skip";
        }
    }

    public String setMiniLevel(int miniLevel) {
        if (powerOn) {
            this.miniLevel = miniLevel;
            return "success";
        } else {
            return "skip";
        }
    }


    public String toSystemString() {
        boolean sdcardStatusBoolean = sdcardStatus > 0;
        boolean maxClientBoolean = maxClient > 0;
        boolean nightModeBoolean = nightMode > 0;
        boolean miniLevelBoolean = miniLevel > 0;
        return "VideoCamera{" +
                "'powerOn':" + powerOn +
                ", 'motionRecord':" + motionRecord +
                ", 'light':" + light +
                ", 'fullColor':" + fullColor +
                ", 'flip':" + flip +
                ", 'improveProgram':" + improveProgram +
                ", 'wdr':" + wdr +
                ", 'track':" + track +
                ", 'sdcardStatus':" + sdcardStatusBoolean +
                ", 'watermark':" + watermark +
                ", 'maxClient':" + maxClientBoolean +
                ", 'nightMode':" + nightModeBoolean +
                ", 'miniLevel':" + miniLevelBoolean +
                '}';
    }

    @Override
    public String toString() {
        return "VideoCamera{" +
                "'powerOn':" + powerOn +
                ", 'motionRecord':" + motionRecord +
                ", 'light':" + light +
                ", 'fullColor':" + fullColor +
                ", 'flip':" + flip +
                ", 'improveProgram':" + improveProgram +
                ", 'wdr':" + wdr +
                ", 'track':" + track +
                ", 'sdcardStatus':" + sdcardStatus +
                ", 'watermark':" + watermark +
                ", 'maxClient':" + maxClient +
                ", 'nightMode':" + nightMode +
                ", 'miniLevel':" + miniLevel +
                '}';
    }

    public static VideoCamera fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("VideoCamera", ""));
        return new VideoCamera(
                json.getBoolean("powerOn"),
                json.getBoolean("motionRecord"),
                json.getBoolean("light"),
                json.getBoolean("fullColor"),
                json.getBoolean("flip"),
                json.getBoolean("improveProgram"),
                json.getBoolean("wdr"),
                json.getBoolean("track"),
                json.getInteger("sdcardStatus"),
                json.getBoolean("watermark"),
                json.getInteger("maxClient"),
                json.getInteger("nightMode"),
                json.getInteger("miniLevel")
        );
    }

    public static void main(String[] args) {
        VideoCamera videoCamera = new VideoCamera();
        VideoCamera test = VideoCamera.fromString(videoCamera.toString());
        LOGGER.info(test.toString());
        LOGGER.info(test.toSystemString());
    }

}
