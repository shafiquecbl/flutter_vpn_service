package com.shafique.flutter_vpn_service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.os.ParcelFileDescriptor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.Socket

class FlutterVpnService : VpnService(), MethodChannel.MethodCallHandler {

    private var vpnBuilder: Builder? = null
    private var vpnInterface: ParcelFileDescriptor? = null

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "prepare" -> {
                val context = applicationContext
                val intent = VpnService.prepare(context)
                result.success(intent == null) // If null, VPN is already prepared.
            }
            "protectSocket" -> {
                val socket = call.argument<Int>("socket")
                if (socket != null) {
                    result.success(protect(socket))
                } else {
                    result.error("INVALID_ARGUMENT", "Socket is null", null)
                }
            }
            "setSession" -> {
                val sessionName = call.argument<String>("session")
                if (sessionName != null) {
                    vpnBuilder = Builder().setSession(sessionName)
                    result.success(true)
                } else {
                    result.error("INVALID_ARGUMENT", "Session name is null", null)
                }
            }
            "addAddress" -> {
                val address = call.argument<String>("address")
                val prefixLength = call.argument<Int>("prefixLength") ?: 32
                if (address != null) {
                    vpnBuilder?.addAddress(address, prefixLength)
                    result.success(true)
                } else {
                    result.error("INVALID_ARGUMENT", "Address is null", null)
                }
            }
            "addRoute" -> {
                val address = call.argument<String>("address")
                val prefixLength = call.argument<Int>("prefixLength") ?: 32
                if (address != null) {
                    vpnBuilder?.addRoute(address, prefixLength)
                    result.success(true)
                } else {
                    result.error("INVALID_ARGUMENT", "Address is null", null)
                }
            }
            "establishVpn" -> {
                try {
                    vpnInterface = vpnBuilder?.establish()
                    result.success(vpnInterface != null)
                } catch (e: Exception) {
                    result.error("ESTABLISH_FAILED", e.message, null)
                }
            }
            else -> result.notImplemented()
        }
    }

    // Make the Builder class static
    companion object {
        class Builder {
            private val builder = VpnService.Builder()

            fun setSession(session: String): Builder {
                builder.setSession(session)
                return this
            }

            fun setMtu(mtu: Int): Builder {
                builder.setMtu(mtu)
                return this
            }

            fun addAddress(address: String, prefixLength: Int): Builder {
                builder.addAddress(address, prefixLength)
                return this
            }

            fun addRoute(address: String, prefixLength: Int): Builder {
                builder.addRoute(address, prefixLength)
                return this
            }

            fun setConfigureIntent(intent: PendingIntent): Builder {
                builder.setConfigureIntent(intent)
                return this
            }

            fun establish(): ParcelFileDescriptor? {
                return builder.establish()
            }
        }
    }
}
