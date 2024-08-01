# miiocli device --ip 192.168.3.108 --token ce9569a39ce43eb21aba012884c2e6ac info

from miio.integrations.lumi.gateway.gateway import Gateway

ip = "192.168.3.108"
token = "ce9569a39ce43eb21aba012884c2e6ac"

gateway = Gateway(ip, token)

gateway.alarm.on()
gateway.light.set_rgb(10, [255, 255, 255])
gateway.light.set_rgb(0, [255, 255, 255])
gateway.alarm.off()
