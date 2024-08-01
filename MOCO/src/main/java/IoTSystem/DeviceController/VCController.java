package IoTSystem.DeviceController;

import IoTSystem.DeviceTwin.VCTwin;
import VirtualDevice.VideoCamera;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VCController {
    private static final Logger LOGGER = LogManager.getLogger();
    String deviceId;
    VideoCamera vc;
    VCTwin vcTwin;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public VideoCamera getVc() {
        return vc;
    }

    public void setVc(VideoCamera vc) {
        this.vc = vc;
    }

    public VCTwin getVcTwin() {
        return vcTwin;
    }

    public void setVcTwin(VCTwin vcTwin) {
        this.vcTwin = vcTwin;
    }

    public VCController(String deviceId, VideoCamera vc, VCTwin vcTwin) {
        this.deviceId = deviceId;
        this.vc = vc;
        this.vcTwin = vcTwin;
    }

    public void turnOn(){
        String result = vc.turnOn();
        if (result.equals("success")) {
            vcTwin.setPowerOn(true);
        }
    }

    public void turnOff(){
        String result = vc.turnOff();
        if (result.equals("success")) {
            vcTwin.setPowerOn(false);
        }
    }

    public void turnOnMotionRecord(){
        String result = vc.turnOnMotionRecord();
        if (result.equals("success")) {
            vcTwin.setMotionRecord(true);
        }
    }

    public void turnOffMotionRecord(){
        String result = vc.turnOffMotionRecord();
        if (result.equals("success")) {
            vcTwin.setMotionRecord(false);
        }
    }

    public void turnOnLight(){
        String result = vc.turnOnLight();
        if (result.equals("success")) {
            vcTwin.setLight(true);
        }
    }

    public void turnOffLight(){
        String result = vc.turnOffLight();
        if (result.equals("success")) {
            vcTwin.setLight(false);
        }
    }

    public void turnOnFullColor(){
        String result = vc.turnOnFullColor();
        if (result.equals("success")) {
            vcTwin.setFullColor(true);
        }
    }

    public void turnOffFullColor(){
        String result = vc.turnOffFullColor();
        if (result.equals("success")) {
            vcTwin.setFullColor(false);
        }
    }

    public void turnOnFlip(){
        String result = vc.turnOnFlip();
        if (result.equals("success")) {
            vcTwin.setFlip(true);
        }
    }

    public void turnOffFlip(){
        String result = vc.turnOffFlip();
        if (result.equals("success")) {
            vcTwin.setFlip(false);
        }
    }

    public void turnOnImproveProgram(){
        String result = vc.turnOnImproveProgram();
        if (result.equals("success")) {
            vcTwin.setImproveProgram(true);
        }
    }

    public void turnOffImproveProgram(){
        String result = vc.turnOffImproveProgram();
        if (result.equals("success")) {
            vcTwin.setImproveProgram(false);
        }
    }

    public void turnOnWdr(){
        String result = vc.turnOnWdr();
        if (result.equals("success")) {
            vcTwin.setWdr(true);
        }
    }

    public void turnOffWdr(){
        String result = vc.turnOffWdr();
        if (result.equals("success")) {
            vcTwin.setWdr(false);
        }
    }

    public void turnOnTrack(){
        String result = vc.turnOnTrack();
        if (result.equals("success")) {
            vcTwin.setTrack(true);
        }
    }

    public void turnOffTrack(){
        String result = vc.turnOffTrack();
        if (result.equals("success")) {
            vcTwin.setTrack(false);
        }
    }

    public void turnOffWatermark(){
        String result = vc.turnOffWatermark();
        if (result.equals("success")) {
            vcTwin.setWatermark(false);
        }
    }

    public void setMaxClient(int maxClient){
        String result = vc.setMaxClient(maxClient);
        if (result.equals("success")) {
            vcTwin.setMaxClient(maxClient);
        }
    }

    public void setNightMode(int nightMode){
        String result = vc.setNightMode(nightMode);
        if (result.equals("success")) {
            vcTwin.setNightMode(nightMode);
        }
    }

    public void setMiniLevel(int miniLevel){
        String result = vc.setMiniLevel(miniLevel);
        if (result.equals("success")) {
            vcTwin.setMiniLevel(miniLevel);
        }
    }

    public void printInternalState(){
        LOGGER.info("Device ID: " + deviceId);
        String vc2String = vc.toString();
        LOGGER.info("Video Camera: " + vc2String);
        String vcTwin2String = vcTwin.toString();
        LOGGER.info("Video Camera Twin: " + vcTwin2String);
        boolean equal = vc2String.replace("VideoCamera","").equals(vcTwin2String.replace("VCTwin",""));
        LOGGER.info("Equal: " + equal);
    }

    public static void main(String[] args) {
        VCController vcController = new VCController("vc001", new VideoCamera(), new VCTwin());
        vcController.turnOn();
        LOGGER.info("[*] Turn on the video camera");
        vcController.printInternalState();
        vcController.turnOnMotionRecord();
        LOGGER.info("[*] Turn on motion record");
        vcController.printInternalState();
        vcController.turnOnLight();
        LOGGER.info("[*] Turn on light");
        vcController.printInternalState();
        vcController.turnOnFullColor();
        LOGGER.info("[*] Turn on full color");
        vcController.printInternalState();
        vcController.turnOnFlip();
        LOGGER.info("[*] Turn on flip");
        vcController.printInternalState();
        vcController.turnOnImproveProgram();
        LOGGER.info("[*] Turn on improve program");
        vcController.printInternalState();
        vcController.turnOnWdr();
        LOGGER.info("[*] Turn on WDR");
        vcController.printInternalState();
        vcController.turnOnTrack();
        LOGGER.info("[*] Turn on track");
        vcController.printInternalState();

//        vcController.turnOff();
//        LOGGER.info("[*] Turn off the video camera");
//        vcController.printInternalState();

        vcController.turnOffWatermark();
        LOGGER.info("[*] Turn off watermark");
        vcController.printInternalState();
        vcController.setMaxClient(10);
        LOGGER.info("[*] Set max client to 10");
        vcController.printInternalState();
        vcController.setNightMode(1);
        LOGGER.info("[*] Set night mode to 1");
        vcController.printInternalState();
        vcController.setMiniLevel(2);
        LOGGER.info("[*] Set mini level to 2");
        vcController.printInternalState();
    }


}
