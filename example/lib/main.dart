import 'package:flutter/material.dart';
import 'package:flutter_vpn_service/flutter_vpn_service.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter VPN Service',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter VPN Service Example'),
        ),
        body: Center(
          child: ElevatedButton(
            onPressed: () async {
              await FlutterVpnService.prepare();
              await FlutterVpnService.setSession("My VPN Session");
              await FlutterVpnService.addAddress("192.168.1.1", 32);
              await FlutterVpnService.establishVpn();
            },
            child: const Text('Start VPN'),
          ),
        ),
      ),
    );
  }
}
