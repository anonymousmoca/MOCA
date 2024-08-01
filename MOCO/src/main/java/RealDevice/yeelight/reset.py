from miio.integrations.yeelight.light.yeelight import Yeelight
import json

light = Yeelight("192.168.3.109", "d3ba6e7595569e5a3e40697f07d7fdf6")
light.on()
light.set_brightness(1)
light.set_rgb([255, 255, 255])
light.off()
print(light.status())