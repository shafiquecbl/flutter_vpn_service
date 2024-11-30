import 'flutter_vpn_service_platform_interface.dart';

class FlutterVpnService {
  static Future<bool> prepare() {
    return FlutterVpnServicePlatform.instance.prepare();
  }

  static Future<bool> protectSocket(int socket) {
    return FlutterVpnServicePlatform.instance.protectSocket(socket);
  }

  static Future<bool> setSession(String session) {
    return FlutterVpnServicePlatform.instance.setSession(session);
  }

  static Future<bool> addAddress(String address, int prefixLength) {
    return FlutterVpnServicePlatform.instance.addAddress(address, prefixLength);
  }

  static Future<bool> establishVpn() {
    return FlutterVpnServicePlatform.instance.establishVpn();
  }
}
