import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'flutter_vpn_service_method_channel.dart';

abstract class FlutterVpnServicePlatform extends PlatformInterface {
  /// Constructs a FlutterVpnServicePlatform.
  FlutterVpnServicePlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterVpnServicePlatform _instance = MethodChannelFlutterVpnService();

  /// The default instance of [FlutterVpnServicePlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterVpnService].
  static FlutterVpnServicePlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterVpnServicePlatform] when
  /// they register themselves.
  static set instance(FlutterVpnServicePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<bool> prepare();

  Future<bool> protectSocket(int socket);

  Future<bool> setSession(String session);

  Future<bool> addAddress(String address, int prefixLength);

  Future<bool> establishVpn();

  Future<void> addRoute(String address, int prefixLength);

  Future<void> disconnectVpn();
}
