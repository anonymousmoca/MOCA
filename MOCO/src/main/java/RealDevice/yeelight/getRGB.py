from miio.integrations.yeelight.light.yeelight import Yeelight, YeelightStatus
import json

# light = Yeelight("192.168.3.110", "8abc18c479329aa02755102c1d7a1346")
light = Yeelight("192.168.3.109", "d3ba6e7595569e5a3e40697f07d7fdf6")
lightStatus = light.status()
# print(lightStatus.rgb[0], lightStatus.rgb[1], lightStatus.rgb[2])
print(lightStatus)
light_status = light.status()
print(light_status.brightness)
print(light_status.is_on)
print(light_status.lights[0].rgb)
# <YeelightStatus brightness=50 color_flow_params=None color_flowing=False color_mode=1 color_temp=None delay_off=0 developer_mode=False hsv=None is_on=True lights=[<YeelightSubLight brightness=50 color_flow_params=None color_flowing=False color_mode=1 color_temp=None hsv=None is_on=True rgb=(255, 255, 255) rgb_int=16777215>] moonlight_mode=None moonlight_mode_brightness=None music_mode=False name= rgb=(255, 255, 255) rgb_int=16777215 save_state_on_change=False>

print(light_status.color_mode)


#255 255 255\n
