# Kiosk

Application permits to set an URL that will be opened on app start in a Webview with Lock Task mode active (Kiosk app). You can return to URL configuration screen clicking FAB button and enter an hardcoded PIN.
Inspired by [mrugacz95/kiosk](https://github.com/mrugacz95/kiosk)

## Article

[HOW TO TURN YOUR ANDROID APPLICATION INTO A KIOSK](https://snow.dog/blog/kiosk-mode-android/)

## Presentation

[COSU, czyli jak zamienić Androida w kiosk](https://drive.google.com/file/d/1uAX11bXR8aC-sg5VlybGaHo0vmuIw93l/view?usp=sharing)

## Usage

1. Make factory reset
2. Skip adding google acount
3. Install apk

```bash
adb install path/to/kiosk.apk
```

1. Set device owner

```bash
adb shell dpm set-device-owner pl.snowdog.kiosk/.MyDeviceAdminReceiver
```

## Usefull commands

`adb` command location `$HOME/Android/Sdk/platform-tools/adb`


### Remove device owner
```
adb shell dpm remove-active-admin pl.snowdog.kiosk/.MyDeviceAdminReceiver
```
### Uninstall apk
```
adb uninstall pl.snowdog.kiosk
```

## Screenshots

<img src="https://user-images.githubusercontent.com/12548284/37874490-775d37d6-3030-11e8-897c-e5d930a3d44f.png" width="292" height="519" /> <img src="https://user-images.githubusercontent.com/12548284/37874485-6c9b6a70-3030-11e8-8ea4-75ec19f10a59.png" width="292" height="519" />
