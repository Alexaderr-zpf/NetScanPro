package com.example.netscanpro;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.nio.ByteOrder;

/**
 * NetworkUtils — utilidades de red para NetScan Pro
 * Detecta IP local, subred /24 y estado del WiFi
 */
public class NetworkUtils {

    /**
     * Obtiene la IP local del dispositivo via WiFi.
     * Retorna String "192.168.x.x" o null si no hay WiFi.
     */
    public static String getLocalIp(Context ctx) {
        try {
            WifiManager wm = (WifiManager) ctx.getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            if (wm == null) return null;

            int ipInt = wm.getConnectionInfo().getIpAddress();
            if (ipInt == 0) return null;

            if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
                ipInt = Integer.reverseBytes(ipInt);
            }
            byte[] bytes = {
                    (byte)(ipInt >> 24),
                    (byte)(ipInt >> 16),
                    (byte)(ipInt >> 8),
                    (byte) ipInt
            };
            return InetAddress.getByAddress(bytes).getHostAddress();

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retorna los primeros 3 octetos de la IP local.
     * Ejemplo: "192.168.1" para hacer el sweep /24.
     * Retorna null si no hay WiFi o no se puede detectar.
     */
    public static String getSubnetBase(Context ctx) {
        String ip = getLocalIp(ctx);
        if (ip == null) return null;
        int lastDot = ip.lastIndexOf('.');
        if (lastDot == -1) return null;
        return ip.substring(0, lastDot);
    }

    /**
     * Verifica si el dispositivo tiene WiFi activo.
     * Usa NetworkCapabilities (API 31+).
     * Retorna true si hay WiFi, false si no.
     */
    public static boolean isWifiConnected(Context ctx) {
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) return false;
            Network network = cm.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities nc = cm.getNetworkCapabilities(network);
            return nc != null &&
                    nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        } catch (Exception e) {
            return false;
        }
    }
}