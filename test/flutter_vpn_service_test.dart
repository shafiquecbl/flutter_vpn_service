import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_vpn_service/flutter_vpn_service_platform_interface.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterVpnServicePlatform
    with MockPlatformInterfaceMixin
    implements FlutterVpnServicePlatform {
  @override
  Future<bool> addAddress(String address, int prefixLength) async {
    return Future.value(true);
  }

  @override
  Future<bool> establishVpn() async {
    return Future.value(true);
  }

  @override
  Future<bool> prepare() async {
    return Future.value(true);
  }

  @override
  Future<bool> protectSocket(int socket) async {
    return Future.value(true);
  }

  @override
  Future<bool> setSession(String session) async {
    return Future.value(true);
  }

  @override
  Future<void> addRoute(String address, int prefixLength) async {
    return Future.value();
  }

  @override
  Future<void> disconnectVpn() async {
    return Future.value();
  }
}

void main() {}
