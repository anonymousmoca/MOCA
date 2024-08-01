package VirtualDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmartSpeaker {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean powerOn;
    private int volume; // Volume level from 0 to 100
    private String currentTrack; // Currently playing track
    private boolean isPlaying;

    public SmartSpeaker() {
        this.powerOn = false;
        this.volume = 0; // Default volume level
        this.currentTrack = "";
        this.isPlaying = false;
    }

    public SmartSpeaker(boolean powerOn, int volume, String currentTrack, boolean isPlaying) {
        this.powerOn = powerOn;
        this.volume = volume;
        this.currentTrack = currentTrack;
        this.isPlaying = isPlaying;
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
            isPlaying = false; // Stop playing when turned off
            return "success";
        } else {
            return "skip";
        }
    }

    public String playTrack(String track) {
        if (powerOn) {
            currentTrack = track;
            isPlaying = true;
            return "success";
        } else {
            return "skip";
        }
    }

    public String stopTrack() {
        if (powerOn && isPlaying) {
            isPlaying = false;
            currentTrack = "";
            return "success";
        } else {
            return "skip";
        }
    }

    public String setVolume(int volume) {
        if (powerOn && volume >= 0 && volume <= 100) {
            this.volume = volume;
            return "success";
        } else {
            return "skip";
        }
    }

    public String toSystemString() {
        boolean volumeFlag = volume > 0 && volume <= 100;
        return "SmartSpeaker{" +
                "'powerOn':" + powerOn +
                ", 'volume':" + volumeFlag +
                ", 'currentTrack':'" + currentTrack + "'" +
                ", 'isPlaying':" + isPlaying +
                "}";
    }

    @Override
    public String toString() {
        return "SmartSpeaker{" +
                "'powerOn':" + powerOn +
                ", 'volume':" + volume +
                ", 'currentTrack':'" + currentTrack + "'" +
                ", 'isPlaying':" + isPlaying +
                "}";
    }

    public static SmartSpeaker fromString(String state) {
        JSONObject json = JSON.parseObject(state.replace("SmartSpeaker", ""));
        return new SmartSpeaker(json.getBoolean("powerOn"), json.getInteger("volume"),
                json.getString("currentTrack"), json.getBoolean("isPlaying"));
    }

    public static void main(String[] args) {
        SmartSpeaker speaker = new SmartSpeaker();
        LOGGER.info("Initial State: " + speaker.toString());

        // Test turning on
        LOGGER.info("Turn on: " + speaker.turnOn());
        LOGGER.info("State after turning on: " + speaker.toString());

        // Test playing track
        LOGGER.info("Play track 'Song A': " + speaker.playTrack("Song A"));
        LOGGER.info("State after playing track: " + speaker.toString());

        // Test setting volume
        LOGGER.info("Set volume to 75: " + speaker.setVolume(75));
        LOGGER.info("State after setting volume: " + speaker.toString());

        // Test stopping track
        LOGGER.info("Stop track: " + speaker.stopTrack());
        LOGGER.info("State after stopping track: " + speaker.toString());

        // Test turning off
        LOGGER.info("Turn off: " + speaker.turnOff());
        LOGGER.info("State after turning off: " + speaker.toString());

        // Test restoring state from string
        String content = speaker.toString();
        SmartSpeaker restoredSpeaker = SmartSpeaker.fromString(content);
        LOGGER.info("Restored SmartSpeaker: " + restoredSpeaker.toString());
    }
}
