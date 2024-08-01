from miio.integrations.zhimi.heater.heater_miot import HeaterMiot
import json
heater = HeaterMiot("192.168.3.140", "f1aca47e0dcee799e5583d044c257ac5")
heater.on()
status = heater.status()

data_dict = {key: value for key, value in status.data.items()}
json_string = json.dumps(data_dict)

print(json_string)
