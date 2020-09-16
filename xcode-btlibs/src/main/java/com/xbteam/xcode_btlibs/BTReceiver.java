package com.xbteam.xcode_btlibs;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import static com.xbteam.xcode_btlibs.BTAdapter.a;
import static com.xbteam.xcode_btlibs.BTAdapter.c;
import static com.xbteam.xcode_btlibs.BTAdapter.d;
import static com.xbteam.xcode_btlibs.BTAdapter.e;

public abstract class BTReceiver extends BroadcastReceiver {
    private Context a;
    private String b;
    private boolean c;

    public BTReceiver(Context var1) {
        this.a = var1;
        this.b = null;
        this.c = false;
    }

    public BTReceiver(Context var1, String var2) {
        this.a = var1;
        this.b = var2;
        this.c = true;
    }

    public void registerReceiver() {
        a();
        c();
        IntentFilter var1;
        (var1 = new IntentFilter()).addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        var1.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        var1.addAction("android.bluetooth.device.action.FOUND");
        var1.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
        this.a.registerReceiver(this, var1);
    }

    public void unregisterReceiver() {
        this.a.unregisterReceiver(this);
        BTAdapter.b();
    }

    public void setNeedsPin(boolean var1) {
        this.c = var1;
    }

    public boolean isNeedsPin() {
        return this.c;
    }

    public void start() {
        d();
    }

    public void stop() {
        e();
    }

    public void onReceive(Context var1, Intent var2) {
        String var3 = var2.getAction();
        BluetoothDevice var4;
        if ("android.bluetooth.device.action.FOUND".equalsIgnoreCase(var3)) {
            var4 = (BluetoothDevice)var2.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            Log.d("BTReceiver", "Bluetooth device: " + var4.getName() + " state: " + var4.getBondState());
            if (this.isPrinter(var4)) {
                if (var4.getBondState() == 10) {
                    boolean var5;
                    if (!(var5 = a(var4))) {
                        Log.d("BTReceiver", "CreateBond failed!");
                        return;
                    }

                    Log.d("BTReceiver", "CreateBond: " + var4.getName() + " " + var5);
                    if (!this.c) {
                        e();
                        this.print(var4);
                        return;
                    }
                } else if (var4.getBondState() == 12) {
                    e();
                    this.print(var4);
                    return;
                }
            }
        } else {
            if ("android.bluetooth.device.action.PAIRING_REQUEST".equalsIgnoreCase(var3)) {
                if (a(var4 = (BluetoothDevice)var2.getParcelableExtra("android.bluetooth.device.extra.DEVICE"), this.b)) {
                    Log.d("BTReceiver", "Bonded: " + var4.getName());
                    e();
                    this.print(var4);
                    return;
                }

                Log.d("BTReceiver", "Bond failed!");
                return;
            }

            if ("android.bluetooth.adapter.action.DISCOVERY_STARTED".equalsIgnoreCase(var3)) {
                Log.d("BTReceiver", "Started Discovery");
                this.startedDiscovery();
                return;
            }

            if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equalsIgnoreCase(var3)) {
                Log.d("BTReceiver", "Finished Discovery");
                this.finishedDiscovery();
            }
        }

    }

    public abstract boolean isPrinter(BluetoothDevice var1);

    public abstract void print(BluetoothDevice var1);

    public abstract void startedDiscovery();

    public abstract void finishedDiscovery();
}