package IoTSystem.DeviceTwin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

public class VCTwin {
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

    public VCTwin() {
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

    public VCTwin(boolean powerOn, boolean motionRecord, boolean light, boolean fullColor, boolean flip, boolean improveProgram, boolean wdr, boolean track, int sdcardStatus, boolean watermark, int maxClient, int nightMode, int miniLevel) {
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

    public boolean isPowerOn() {
        return powerOn;
    }

    public void setPowerOn(boolean powerOn) {
        this.powerOn = powerOn;
    }

    public boolean isMotionRecord() {
        return motionRecord;
    }

    public void setMotionRecord(boolean motionRecord) {
        this.motionRecord = motionRecord;
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public boolean isFullColor() {
        return fullColor;
    }

    public void setFullColor(boolean fullColor) {
        this.fullColor = fullColor;
    }

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public boolean isImproveProgram() {
        return improveProgram;
    }

    public void setImproveProgram(boolean improveProgram) {
        this.improveProgram = improveProgram;
    }

    public boolean isWdr() {
        return wdr;
    }

    public void setWdr(boolean wdr) {
        this.wdr = wdr;
    }

    public boolean isTrack() {
        return track;
    }

    public void setTrack(boolean track) {
        this.track = track;
    }

    public int getSdcardStatus() {
        return sdcardStatus;
    }

    public void setSdcardStatus(int sdcardStatus) {
        this.sdcardStatus = sdcardStatus;
    }

    public boolean isWatermark() {
        return watermark;
    }

    public void setWatermark(boolean watermark) {
        this.watermark = watermark;
    }

    public int getMaxClient() {
        return maxClient;
    }

    public void setMaxClient(int maxClient) {
        this.maxClient = maxClient;
    }

    public int getNightMode() {
        return nightMode;
    }

    public void setNightMode(int nightMode) {
        this.nightMode = nightMode;
    }

    public int getMiniLevel() {
        return miniLevel;
    }

    public void setMiniLevel(int miniLevel) {
        this.miniLevel = miniLevel;
    }

    public String toSystemString() {
        boolean sdcardStatusBoolean = sdcardStatus > 0;
        boolean maxClientBoolean = maxClient > 0;
        boolean nightModeBoolean = nightMode > 0;
        boolean miniLevelBoolean = miniLevel > 0;
        return "VCTwin{" +
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

    public void setTargetState(String input) {
        JSONObject json = JSON.parseObject(input.replace("VideoCamera", ""));
        this.powerOn = json.getBoolean("powerOn");
        this.motionRecord = json.getBoolean("motionRecord");
        this.light = json.getBoolean("light");
        this.fullColor = json.getBoolean("fullColor");
        this.flip = json.getBoolean("flip");
        this.improveProgram = json.getBoolean("improveProgram");
        this.wdr = json.getBoolean("wdr");
        this.track = json.getBoolean("track");
        this.watermark = json.getBoolean("watermark");
    }

    public String toSystemDeviceString() {
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
        return "VCTwin{" +
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

    public static VCTwin fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("VCTwin", ""));
        return new VCTwin(
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VCTwin)) return false;
        VCTwin vcTwin = (VCTwin) o;
        return isPowerOn() == vcTwin.isPowerOn() && isMotionRecord() == vcTwin.isMotionRecord() && isLight() == vcTwin.isLight() && isFullColor() == vcTwin.isFullColor() && isFlip() == vcTwin.isFlip() && isImproveProgram() == vcTwin.isImproveProgram() && isWdr() == vcTwin.isWdr() && isTrack() == vcTwin.isTrack() && getSdcardStatus() == vcTwin.getSdcardStatus() && isWatermark() == vcTwin.isWatermark() && getMaxClient() == vcTwin.getMaxClient() && getNightMode() == vcTwin.getNightMode() && getMiniLevel() == vcTwin.getMiniLevel();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPowerOn(), isMotionRecord(), isLight(), isFullColor(), isFlip(), isImproveProgram(), isWdr(), isTrack(), getSdcardStatus(), isWatermark(), getMaxClient(), getNightMode(), getMiniLevel());
    }
}
