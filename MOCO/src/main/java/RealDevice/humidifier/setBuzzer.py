import sys
from miio.integrations.zhimi.humidifier.airhumidifier_miot import AirHumidifierMiot
import json

if len(sys.argv) != 2:
    print("Usage: python setBuzzer.py <humidifier-buzzer>")
    sys.exit(1)
buzzer = bool(sys.argv[1])
# buzzer = False

humidifier = AirHumidifierMiot("192.168.3.129", "e5425414e9aa3804b6b3749e6c818429")

humidifier.set_buzzer(buzzer)
status = humidifier.status()

data_dict = {key: value for key, value in status.data.items()}
json_string = json.dumps(data_dict)

print(json_string)

