from miio.integrations.zhimi.humidifier.airhumidifier_miot import AirHumidifierMiot
import json
humidifier = AirHumidifierMiot("192.168.3.129", "e5425414e9aa3804b6b3749e6c818429")

humidifier.on()

status = humidifier.status()

data_dict = {key: value for key, value in status.data.items()}
json_string = json.dumps(data_dict)

print(json_string)
