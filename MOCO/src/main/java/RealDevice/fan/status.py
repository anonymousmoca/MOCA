from miio.integrations.zhimi.fan.fan import Fan
import json
fan = Fan("192.168.3.121", "687c8694197333dd5ada5de6de908986")
status = fan.status()

data_dict = {key: value for key, value in status.data.items()}
json_string = json.dumps(data_dict)

print(json_string)