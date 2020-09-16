package com.xbteam.xcode_btlibs;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

public final class BTAdapter {
    private static boolean a = false;

    public static void a() {
        BluetoothAdapter var0;
        if ((var0 = BluetoothAdapter.getDefaultAdapter()) != null) {
            a = var0.isEnabled();
        } else {
            a = false;
        }
    }

    public static void b() {
        BluetoothAdapter var0;
        if ((var0 = BluetoothAdapter.getDefaultAdapter()) != null) {
            if (a && !var0.isEnabled()) {
                var0.enable();
                return;
            }

            if (!a && var0.isEnabled()) {
                var0.disable();
            }
        }

    }

    public static boolean c() {
        BluetoothAdapter var0;
        return (var0 = BluetoothAdapter.getDefaultAdapter()) != null && !var0.isEnabled() ? var0.enable() : false;
    }

    public static boolean d() {
        BluetoothAdapter var0;
        return (var0 = BluetoothAdapter.getDefaultAdapter()) == null ? false : var0.startDiscovery();
    }

    public static boolean e() {
        BluetoothAdapter var0;
        return (var0 = BluetoothAdapter.getDefaultAdapter()) == null ? false : var0.cancelDiscovery();
    }

    public static boolean a(BluetoothDevice var0) {
        try {
            return (Boolean) BluetoothDevice.class.getMethod("createBond").invoke(var0);
        } catch (Exception var2) {
            Log.e("BTUtils", "CreateBond failed!", var2);
            return false;
        }
    }

    public static boolean a(BluetoothDevice var0, String var1) {
        try {
            return (Boolean) BluetoothDevice.class.getMethod("setPin", byte[].class).invoke(var0, var1.getBytes());
        } catch (Exception var3) {
            Log.e("BTUtils", "SetPin failed!", var3);
            return false;
        }
    }
}