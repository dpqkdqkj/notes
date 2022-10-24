## Connect wifi on adapter
```
sudo nmcli dev wifi connect internet password "YOUR_PASSWORD"
```

## Fix unmanaged
```
sudo nmcli dev set <device> managed yes
```
[source](https://askubuntu.com/questions/882806/ethernet-device-not-managed)
