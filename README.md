# flutter_vpn_service

A Flutter plugin for managing VPN services on Android. This plugin allows you to programmatically establish and configure VPN connections, protect sockets, and handle network routing. It is primarily designed to be used within Flutter apps that need VPN capabilities.

# Changelog

## [1.0.0] - 2024-11-30

### Added

- Initial release with the following features:
  - VPN preparation.
  - Socket protection.
  - Session management.
  - Address and route management.

## Installation

### 1. Add the dependency to your `pubspec.yaml`:

```yaml
dependencies:
  flutter_vpn_service:
    git:
      url: https://github.com/your_username/flutter_vpn_service.git
```

### Alternatively, if you have published the package on pub.dev, you can add it like this:

```yaml
dependencies:
  flutter_vpn_service: ^<latest_version>
```

### 2. Install the dependencies:

```bash
flutter pub get
```

### 3. Configure your Android project:

Ensure that your Android app has the necessary permissions and configurations for using the VPN service. Add the following to your AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.VPN"/>
<uses-feature android:name="android.hardware.vpn" android:required="true"/>
```

### 4. Platform-Specific Configuration:

Android: The plugin currently supports Android and does not require any special configurations beyond the AndroidManifest.xml permissions above.

## Usage

Here’s a basic guide on how to use the flutter_vpn_service plugin:

### 1. Prepare VPN

Before you can start using the VPN, you need to prepare it.

```dart
import 'package:flutter_vpn_service/flutter_vpn_service.dart';

Future<void> prepareVpn() async {
  bool isPrepared = await FlutterVpnService.prepare();
  if (isPrepared) {
    print("VPN is prepared and ready to use.");
  } else {
    print("VPN is already prepared.");
  }
}
```

### 2. Protect a Socket

You can protect specific sockets to use the VPN.

```dart
Future<void> protectSocket(int socket) async {
  bool success = await FlutterVpnService.protectSocket(socket);
  if (success) {
    print("Socket is now protected by VPN.");
  } else {
    print("Failed to protect the socket.");
  }
}
```

### 3. Set Session Name

Set a session name for the VPN connection.

```dart
Future<void> setSession(String session) async {
  bool success = await FlutterVpnService.setSession(session);
  if (success) {
    print("Session set to $session.");
  } else {
    print("Failed to set session.");
  }
}
```

### 4. Add Address

Add an IP address and its associated prefix length to the VPN configuration.

```dart
Future<void> addAddress(String address, int prefixLength) async {
  bool success = await FlutterVpnService.addAddress(address, prefixLength);
  if (success) {
    print("Address $address added with prefix length $prefixLength.");
  } else {
    print("Failed to add address.");
  }
}
```

### 5. Establish VPN

Finally, establish the VPN connection.

```dart
Future<void> establishVpn() async {
  bool success = await FlutterVpnService.establishVpn();
  if (success) {
    print("VPN connection established.");
  } else {
    print("Failed to establish VPN connection.");
  }
}
```

## Example

Here is a complete example of how you can use the flutter_vpn_service plugin in your app:

```dart
import 'package:flutter/material.dart';
import 'package:flutter_vpn_service/flutter_vpn_service.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter VPN Service',
      home: Scaffold(
        appBar: AppBar(
          title: Text('Flutter VPN Service Example'),
        ),
        body: Center(
          child: ElevatedButton(
            onPressed: () async {
              await FlutterVpnService.prepare();
              await FlutterVpnService.setSession("My VPN Session");
              await FlutterVpnService.addAddress("192.168.1.1", 32);
              await FlutterVpnService.establishVpn();
            },
            child: Text('Start VPN'),
          ),
        ),
      ),
    );
  }
}
```

## Notes

Permissions: Make sure your Android app has the required permissions for using the VPN

```xml
<uses-permission android:name="android.permission.VPN"/>
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

Testing: This plugin is designed to work on Android. Make sure to test it on a physical device rather than an emulator, as VPN functionality may not work properly on an emulator.

## Contributing

Contributions are welcome! Please feel free to open issues, submit pull requests, or suggest improvements.

1- Fork the repository.
2- Create your branch (git checkout -b feature-branch).
3- Commit your changes (git commit -am 'Add new feature').
4- Push to the branch (git push origin feature-branch).
5- Create a new Pull Request.
