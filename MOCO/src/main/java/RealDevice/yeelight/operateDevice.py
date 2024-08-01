from miio.integrations.yeelight.light.yeelight import Yeelight
import sys
# miiocli device --ip 192.168.3.109 --token d3ba6e7595569e5a3e40697f07d7fdf6 info
import json

dev = Yeelight("192.168.3.109", "d3ba6e7595569e5a3e40697f07d7fdf6")

dev.on()
dev.set_rgb([3, 3, 9])
dev.set_rgb([33, 33, 39])
dev.set_rgb([233, 133, 39])
dev.set_rgb([3, 23, 255])
dev.off()
dev.set_rgb([33, 33, 39])
