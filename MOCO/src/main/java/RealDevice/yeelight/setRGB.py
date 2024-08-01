import sys
from miio.integrations.yeelight.light.yeelight import Yeelight
import json

if len(sys.argv) != 4:
    print("Usage: python setRGB.py <r> <g> <b>")
    sys.exit(1)

r, g, b = sys.argv[1:]
r, g, b = map(int, [r, g, b])

light = Yeelight("192.168.3.109", "d3ba6e7595569e5a3e40697f07d7fdf6")

light.set_rgb([r, g, b])

print(light.status())
