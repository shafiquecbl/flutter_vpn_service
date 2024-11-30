package com.shafique.flutter_vpn_service

import android.content.Context
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterVpnServicePlugin */
class FlutterVpnServicePlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  private lateinit var channel : MethodChannel
  private var vpnService: FlutterVpnService? = null
  private var context: Context? = null

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_vpn_service")
    channel.setMethodCallHandler(this)
    context = flutterPluginBinding.applicationContext
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (vpnService == null) {
      vpnService = FlutterVpnService(context!!)
    }
    vpnService?.onMethodCall(call, result)
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    // Handle activity attachment if needed
  }

  override fun onDetachedFromActivityForConfigChanges() {
    // Handle activity detachment for config changes if needed
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    // Handle activity reattachment for config changes if needed
  }

  override fun onDetachedFromActivity() {
    // Handle activity detachment if needed
  }
}