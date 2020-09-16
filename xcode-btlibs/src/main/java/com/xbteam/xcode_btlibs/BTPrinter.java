package com.xbteam.xcode_btlibs;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public final class BTPrinter {
    private static final UUID a = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothSocket b;
    private OutputStream c;
    private BT d;
    private List e = new ArrayList();

    public BTPrinter() {
    }

    public final void addListener(Btlistener var1) {
        this.e.clear();
        this.e.add(var1);
    }

    public final synchronized boolean connect(BluetoothDevice var1) {
        try {
            this.b = var1.createInsecureRfcommSocketToServiceRecord(a);
        } catch (Exception var4) {
            Log.e("BTPrinter", "CreateRfcommSocket failed!", var4);
            return false;
        }

        try {
            this.b.connect();
        } catch (IOException var3) {
            Log.e("BTPrinter", "Connection failed", var3);
            return false;
        }

        try {
            this.c = this.b.getOutputStream();
            this.d = new BT(this, this.b.getInputStream());
            this.d.a();
            return true;
        } catch (IOException var2) {
            Log.e("BTPrinter", "GetOutputStream failed", var2);
            return false;
        }
    }

    public final boolean isConnected() {
        return this.b != null && this.c != null;
    }

    public final synchronized void close() {
        try {
            if (this.d != null) {
                this.d.b();
            }
        } catch (Exception var3) {
        }

        this.d = null;

        try {
            if (this.c != null) {
                this.c.close();
            }
        } catch (Exception var2) {
        }

        this.c = null;

        try {
            if (this.b != null) {
                this.b.close();
            }
        } catch (Exception var1) {
        }

        this.b = null;
    }

    public final void write(String var1) {
        try {
            this.c.write(var1.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public final void write(byte[] var1) {
        int var2 = var1.length;
        int var3 = 0;
        boolean var4 = false;

        while(var3 < var2) {
            int var6;
            if (var2 - var3 > 160) {
                var6 = 160;
            } else {
                var6 = var2 - var3;
            }

            try {
                this.c.write(var1, var3, var6);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            var3 += var6;

            try {
                Thread.sleep(25L);
            } catch (Exception var5) {
            }
        }

    }

    public final void write(int var1) {
        try {
            this.c.write(var1);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public final void flush() {
        try {
            this.c.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public final void reset() {
        this.write(BTCommands.RESET);
        this.write(BTCommands.DEFAULT_NORMAL_FONT);
        try {
            this.c.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public final void changeSmallFont() {
        this.write(BTCommands.DEFAULT_SMALL_FONT);
        try {
            this.c.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public final void println(String var1) {
        this.println(var1.getBytes());
    }

    public final void println(byte[] var1) {
        this.write(var1);
        this.write(BTCommands.LF);
        try {
            this.c.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public final void print(String var1) {
        this.println(var1.getBytes());
    }

    public final void print(byte[] var1) {
        this.write(var1);
        try {
            this.c.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public final void printBarcode(byte[] var1, byte var2, int var3) {
        this.write(new byte[]{29, 104, (byte)var3});
        byte[] var6;
        (var6 = new byte[4 + var1.length])[0] = 29;
        var6[1] = 107;
        var6[2] = var2;
        var6[3] = (byte)var1.length;

        for(int var5 = 4; var5 < var1.length + 4; ++var5) {
            var6[var5] = var1[var5 - 4];
        }

        this.write(var6);
        try {
            this.c.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            Thread.sleep(25L);
        } catch (Exception var4) {
        }
    }

    public final void printBitmap(Bitmap var1) {
        this.a(var1);
    }

    private void a(Bitmap var1) {
        int var2 = var1.getWidth();
        int var3 = var1.getHeight();
        ArrayList var4 = new ArrayList();
        byte[] var5 = new byte[]{29, 118, 48, 0};
        var4.add(var5);
        int var16 = (var2 % 8 == 0 ? var2 / 8 : var2 / 8 + 1) % 256;
        int var6 = (var2 % 8 == 0 ? var2 / 8 : var2 / 8 + 1) / 256;
        int var7 = var3 % 256;
        int var8 = var3 / 256;
        var4.add(new byte[]{(byte)var16, (byte)var6, (byte)var7, (byte)var8});
        var16 = var2 % 8;
        StringBuffer var17 = new StringBuffer();
        if (var16 > 0) {
            for(var7 = 0; var7 < 8 - var16; ++var7) {
                var17.append("0");
            }
        }

        boolean var18 = false;
        Object var19 = null;
        StringBuffer var9 = new StringBuffer();

        for(int var10 = 0; var10 < var3; ++var10) {
            var7 = 0;
            var9.setLength(0);
            byte[] var20 = new byte[var16 == 0 ? var2 / 8 : var2 / 8 + 1];

            for(int var11 = 0; var11 < var2; ++var11) {
                int var12;
                int var13 = (var12 = var1.getPixel(var11, var10)) >> 16 & 255;
                int var14 = var12 >> 8 & 255;
                var12 &= 255;
                if (var13 <= 200 && var14 <= 200 && var12 <= 200) {
                    var9.append("1");
                } else {
                    var9.append("0");
                }

                if (var9.length() == 8) {
                    var20[var7++] = (byte) Integer.parseInt(var9.toString(), 2);
                    var9.setLength(0);
                }
            }

            if (var16 > 0) {
                var9.append(var17);
                var20[var7] = (byte) Integer.parseInt(var9.toString(), 2);
            }

            var4.add(var20);
        }

        this.write(a((List)var4));
        this.flush();

        try {
            Thread.sleep(25L);
        } catch (Exception var15) {
        }
    }

    public final void finish() {
        this.write(BTCommands.LF);
        try {
            this.c.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            Thread.sleep(25L);
        } catch (Exception var2) {
        }
    }

    private static byte[] a(List var0) {
        int var1 = 0;

        byte[] var2;
        for(Iterator var3 = var0.iterator(); var3.hasNext(); var1 += var2.length) {
            var2 = (byte[])var3.next();
        }

        var2 = new byte[var1];
        int var8 = 0;

        byte[] var5;
        for(Iterator var6 = var0.iterator(); var6.hasNext(); var8 += var5.length) {
            System.arraycopy(var5 = (byte[])var6.next(), 0, var2, var8, var5.length);
        }

        var5 = var2;
        StringBuilder var7 = new StringBuilder("");

        for(var8 = 0; var8 < var5.length; ++var8) {
            String var4;
            if ((var4 = Integer.toHexString(var5[var8] & 255)).length() < 2) {
                var7.append(0);
            }

            var7.append(var4);
        }

        Log.e("BTPrinter", var7.toString());
        return var2;
    }

    public final void onParseData(String var1) {
        Iterator var3 = this.e.iterator();

        while(var3.hasNext()) {
            ((Btlistener)var3.next()).paresDate(var1);
        }

    }
}
