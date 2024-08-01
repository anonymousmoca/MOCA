from miio.integrations.zhimi.humidifier.airhumidifier_miot import AirHumidifierMiot
import json
humidifier = AirHumidifierMiot("192.168.3.129", "e5425414e9aa3804b6b3749e6c818429")

humidifier.on()
humidifier.set_buzzer(False)    # True & False
humidifier.set_speed(200) # [200, 2000] x%10=0, int
humidifier.set_target_humidity(30) # [30, 80], int
humidifier.set_dry(False)   # True & False
humidifier.set_clean_mode(False)    # True & False
humidifier.off()

status = humidifier.status()

data_dict = {key: value for key, value in status.data.items()}
json_string = json.dumps(data_dict)

print(json_string)