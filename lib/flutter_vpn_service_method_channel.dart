import 'package:flutter/services.dart';
import 'flutter_vpn_service_platform_interface.dart';

/// An implementation of [FlutterVpnServicePlatform] that uses method channels.
class MethodChannelFlutterVpnService extends FlutterVpnServicePlatform {
  /// The method channel used to interact with the native platform.
  final methodChannel = const MethodChannel('flutter_vpn_service');

  @override
  Future<bool> prepare() async {
    try {
      final bool isPrepared = await methodChannel.invokeMethod('prepare');
      return isPrepared;
    } on PlatformException catch (e) {
      throw "Error preparing VPN: ${e.message}";
    }
  }

  @override
  Future<bool> protectSocket(int socket) async {
    try {
      final bool result =
          await methodChannel.invokeMethod('protectSocket', {'socket': socket});
      return result;
    } on PlatformException catch (e) {
      throw "Error protecting socket: ${e.message}";
    }
  }

  @override
  Future<bool> setSession(String session) async {
    try {
      final bool result =
          await methodChannel.invokeMethod('setSession', {'session': session});
      return result;
    } on PlatformException catch (e) {
      throw "Error setting session: ${e.message}";
    }
  }

  @override
  Future<bool> addAddress(String address, int prefixLength) async {
    try {
      final bool result = await methodChannel.invokeMethod(
          'addAddress', {'address': address, 'prefixLength': prefixLength});
      return result;
    } on PlatformException catch (e) {
      throw "Error adding address: ${e.message}";
    }
  }

  @override
  Future<bool> establishVpn() async {
    try {
      final bool result = await methodChannel.invokeMethod('establishVpn');
      return result;
    } on PlatformException catch (e) {
      throw "Error establishing VPN: ${e.message}";
    }
  }
}
