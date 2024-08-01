from miio.integrations.chuangmi.camera.chuangmi_camera import ChuangmiCamera
import json

camera = ChuangmiCamera("192.168.3.113", "3056304e425542707471336d635a4672")

camera.night_mode_auto()
status = camera.status()

data_dict = {key: value for key, value in status.data.items()}
json_string = json.dumps(data_dict)

print(json_string)