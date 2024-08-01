import json

from miio.integrations.chuangmi.camera.chuangmi_camera import ChuangmiCamera

# IP:       192.168.3.113
# TOKEN:    694859656c476d7543775439704a434d

camera = ChuangmiCamera("192.168.3.113", "694859656c476d7543775439704a434d")


def print_status():
    status = camera.status()
    for key, value in status.data.items():
        print("{}: {}".format(key, value))
    print("----------------------")


def turn_on():
    print("Turning on...")
    camera.on()
    print_status()


def turn_off():
    print("Turning off...")
    camera.off()
    print_status()


def flip_on():
    print("Flipping on...")
    camera.flip_on()
    print_status()


def flip_off():
    print("Flipping off...")
    camera.flip_off()
    print_status()


def light_on():
    print("Lighting on...")
    camera.light_on()
    print_status()


def light_off():
    print("Lighting off...")
    camera.light_off()
    print_status()


def night_mode_auto():
    print("Night mode auto...")
    camera.night_mode_auto()
    print_status()


def night_mode_off():
    print("Night mode off...")
    camera.night_mode_off()
    print_status()


def night_mode_on():
    print("Night mode on...")
    camera.night_mode_on()
    print_status()


def watermark_on():
    print("Watermark on...")
    camera.watermark_on()
    print_status()


def watermark_off():
    print("Watermark off...")
    camera.watermark_off()
    print_status()



turn_on()
flip_off()
light_off()
night_mode_auto()
watermark_on()
turn_off()
