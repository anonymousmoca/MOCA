package IoTSystem;

import java.util.Arrays;

public class Message {
    private String deviceType;
    private String deviceAPI;
    private String[] deviceAPIArgs;

    public Message(String deviceType, String deviceAPI, String[] deviceAPIArgs) {
        this.deviceType = deviceType;
        this.deviceAPI = deviceAPI;
        this.deviceAPIArgs = deviceAPIArgs;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getDeviceAPI() {
        return deviceAPI;
    }

    public String[] getDeviceAPIArgs() {
        return deviceAPIArgs;
    }


    public String toString() {
        return "Message{" +
                "'deviceType':'" + deviceType +
                "', 'deviceAPI':'" + deviceAPI +
                "', 'deviceAPIArgs':'" + Arrays.toString(deviceAPIArgs) +
                "'}";
    }
}
