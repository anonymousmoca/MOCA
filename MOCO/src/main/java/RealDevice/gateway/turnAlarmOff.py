# miiocli device --ip 192.168.3.108 --token ce9569a39ce43eb21aba012884c2e6ac info

from miio.integrations.lumi.gateway.gateway import Gateway

ip = "192.168.3.108"
token = "ce9569a39ce43eb21aba012884c2e6ac"

gateway = Gateway(ip, token)

gateway.alarm.off()
