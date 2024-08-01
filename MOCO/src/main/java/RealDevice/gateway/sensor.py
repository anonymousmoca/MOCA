from miio import Gateway

ip = "192.168.3.108"
token = "ce9569a39ce43eb21aba012884c2e6ac"

gateway = Gateway(ip, token)

gateway.discover_devices()

devices = gateway.devices

for k,v in devices.items():
    v.update()
    print(k,v)
    print(v.status)
    print(v.device_type)
    print(v.sid)