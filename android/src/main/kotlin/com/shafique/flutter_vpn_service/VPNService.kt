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

class FlutterVpnService() : VpnService(), MethodChannel.MethodCallHandler {

    private var vpnBuilder: VpnBuilder? = null
    private var vpnInterface: ParcelFileDescriptor? = null

    companion object {
        lateinit var context: Context
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "prepareVpn" -> prepareVpn(result)
            "protectSocket" -> protectSocket(call, result)
            "setSession" -> setSession(call, result)
            "addAddress" -> addAddress(call, result)
            "addRoute" -> addRoute(call, result)
            "establishVpn" -> establishVpn(result)
            "disconnectVpn" -> disconnectVpn(result)
            else -> result.notImplemented()
        }
    }

    // Method to prepare the VPN
    private fun prepareVpn(result: MethodChannel.Result) {
        val intent = VpnService.prepare(context)
        result.success(intent == null) // If null, VPN is already prepared.
    }

    // Method to protect a socket
    private fun protectSocket(call: MethodCall, result: MethodChannel.Result) {
        val socket = call.argument<Int>("socket")
        if (socket != null) {
            result.success(protect(socket))
        } else {
            result.error("INVALID_ARGUMENT", "Socket is null", null)
        }
    }

    // Method to set the session for the VPN
    private fun setSession(call: MethodCall, result: MethodChannel.Result) {
        val sessionName = call.argument<String>("session")
        if (sessionName != null) {
            vpnBuilder = VpnBuilder().setSession(sessionName)
            result.success(true)
        } else {
            result.error("INVALID_ARGUMENT", "Session name is null", null)
        }
    }

    // Method to add an address to the VPN
    private fun addAddress(call: MethodCall, result: MethodChannel.Result) {
        val address = call.argument<String>("address")
        val prefixLength = call.argument<Int>("prefixLength") ?: 32
        if (address != null) {
            vpnBuilder?.addAddress(address, prefixLength)
            result.success(true)
        } else {
            result.error("INVALID_ARGUMENT", "Address is null", null)
        }
    }

    // Method to add a route to the VPN
    private fun addRoute(call: MethodCall, result: MethodChannel.Result) {
        val address = call.argument<String>("address")
        val prefixLength = call.argument<Int>("prefixLength") ?: 32
        if (address != null) {
            vpnBuilder?.addRoute(address, prefixLength)
            result.success(true)
        } else {
            result.error("INVALID_ARGUMENT", "Address is null", null)
        }
    }

    // Method to establish the VPN connection
    private fun establishVpn(result: MethodChannel.Result) {
        try {
            vpnInterface = vpnBuilder?.establish()
            result.success(vpnInterface != null)
        } catch (e: Exception) {
            result.error("ESTABLISH_FAILED", e.message, null)
        }
    }

    // Method to disconnect the VPN
    private fun disconnectVpn(result: MethodChannel.Result) {
        try {
            vpnInterface?.close()  // Close the VPN interface
            vpnInterface = null
            result.success(true)  // Indicate that VPN has been disconnected
        } catch (e: Exception) {
            result.error("DISCONNECT_FAILED", "Failed to disconnect VPN: ${e.message}", null)
        }
    }

    inner class VpnBuilder {
        private val builder = Builder()

        fun setSession(session: String): VpnBuilder {
            builder.setSession(session)
            return this
        }

        fun setMtu(mtu: Int): VpnBuilder {
            builder.setMtu(mtu)
            return this
        }

        fun addAddress(address: String, prefixLength: Int): VpnBuilder {
            builder.addAddress(address, prefixLength)
            return this
        }

        fun addRoute(address: String, prefixLength: Int): VpnBuilder {
            builder.addRoute(address, prefixLength)
            return this
        }

        fun setConfigureIntent(intent: PendingIntent): VpnBuilder {
            builder.setConfigureIntent(intent)
            return this
        }

        fun establish(): ParcelFileDescriptor? {
            return builder.establish()
        }
    }
}
