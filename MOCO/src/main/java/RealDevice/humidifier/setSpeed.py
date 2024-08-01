import sys
from miio.integrations.zhimi.humidifier.airhumidifier_miot import AirHumidifierMiot
import json

if len(sys.argv) != 2:
    print("Usage: python setSpeed.py <humidifier-speed>")
    sys.exit(1)
speed = int(sys.argv[1])
# speed = False

humidifier = AirHumidifierMiot("192.168.3.129", "e5425414e9aa3804b6b3749e6c818429")

humidifier.set_speed(speed)
status = humidifier.status()

data_dict = {key: value for key, value in status.data.items()}
json_string = json.dumps(data_dict)

print(json_string)

