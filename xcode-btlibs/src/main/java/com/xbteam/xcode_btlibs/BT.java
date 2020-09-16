package com.xbteam.xcode_btlibs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class BT implements Runnable {
    private boolean a = false;
    private BTPrinter b;
    private InputStream c;
    private StringBuffer d = new StringBuffer();
    private List e = Collections.synchronizedList(new LinkedList());

    public BT(BTPrinter var1, InputStream var2) {
        this.b = var1;
        this.c = var2;
    }

    public final void a() {
        this.a = true;
        (new Thread(this)).start();
    }

    public final void b() {
        this.a = false;

        try {
            this.c.close();
        } catch (IOException var1) {
        }

        this.c = null;
        this.e.clear();
        this.e = null;
    }

    public final void run() {
        byte[] var1 = new byte[8];
        boolean var2 = false;

        while(this.a) {
            int var8;
            try {
                while((var8 = this.c.read(var1)) > 0) {
                    for(int var3 = 0; var3 < var8; ++var3) {
                        String var4;
                        if ((var4 = Integer.toHexString(var1[var3])).length() == 1) {
                            this.e.add("0" + var4);
                        } else if (var4.length() == 2) {
                            this.e.add(var4);
                        } else {
                            this.e.add(var4.substring(var4.length() - 2, var4.length()));
                        }

                        if (this.e != null && ((String)this.e.get(0)).equals("62") && this.e.size() == 9 && ((String)this.e.get(this.e.size() - 1)).equalsIgnoreCase("6b")) {
                            for(int var9 = 0; var9 < this.e.size(); ++var9) {
                                this.d.append((String)this.e.get(var9));
                            }

                            try {
                                var4 = BTConvertion.a(this.d.toString());
                                this.b.onParseData(var4);
                            } catch (Exception var6) {
                            }

                            this.e.clear();
                            this.d.setLength(0);
                        }
                    }
                }
            } catch (IOException var7) {
                System.out.println("Read data fail! " + var7.getMessage());
                this.a = false;
            }

            try {
                Thread.sleep(20L);
            } catch (Exception var5) {
            }
        }

    }
}