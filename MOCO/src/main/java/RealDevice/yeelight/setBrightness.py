import sys
from miio.integrations.yeelight.light.yeelight import Yeelight
import json

if len(sys.argv) != 2:
    print("Usage: python setBrightness.py <brightness>")
    sys.exit(1)

brightness = int(sys.argv[1])

light = Yeelight("192.168.3.109", "d3ba6e7595569e5a3e40697f07d7fdf6")

light.set_brightness(brightness)

print(light.status())
