from miio.integrations.chuangmi.camera.chuangmi_camera import ChuangmiCamera
import json

camera = ChuangmiCamera("192.168.3.113", "694859656c476d7543775439704a434d")

camera.improve_program_off()
status = camera.status()

data_dict = {key: value for key, value in status.data.items()}
json_string = json.dumps(data_dict)

print(json_string)