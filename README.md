# Kiosk

Application permits to set an URL (and a PIN) that will be opened on app start in a Webview with Lock Task mode active (Kiosk app). You can return to URL configuration screen clicking FAB button and enter the PIN.
Inspired by [mrugacz95/kiosk](https://github.com/mrugacz95/kiosk)

## Article

[HOW TO TURN YOUR ANDROID APPLICATION INTO A KIOSK](https://snow.dog/blog/kiosk-mode-android/)

## Presentation

[COSU, czyli jak zamienić Androida w kiosk](https://drive.google.com/file/d/1uAX11bXR8aC-sg5VlybGaHo0vmuIw93l/view?usp=sharing)

## Usage

1. Make factory reset
2. Skip adding google acount
3. Install apk (`-t` flag is needed for test mode apk)

```bash
adb install -t path/to/kiosk.apk
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

![screen01](https://user-images.githubusercontent.com/29949267/193298639-4faa5671-54b1-4abc-a025-56c487401ca8.png)
![screen02](https://user-images.githubusercontent.com/29949267/193298776-ab5d60af-e82f-4dfc-b09e-7dea18a66c72.png)
![screen03](https://user-images.githubusercontent.com/29949267/193298825-58ab131d-12c2-4450-9a5a-80c5ff148748.png)
![screen04](https://user-images.githubusercontent.com/29949267/193298859-15bbe6bf-0f97-4007-8ba7-3938815b9edf.png)
