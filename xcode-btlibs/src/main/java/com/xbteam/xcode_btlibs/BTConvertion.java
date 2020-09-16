package com.xbteam.xcode_btlibs;

public final class BTConvertion {
    public static String a(String var0) {
        if (var0 == null) {
            return null;
        } else {
            try {
                String[] var6 = var0.replaceAll("..", "$0 ").split(" ");
                StringBuffer var1 = new StringBuffer();
                String[] var4 = var6;
                int var3 = var6.length;

                for(int var2 = 0; var2 < var3; ++var2) {
                    var0 = var4[var2];
                    var1.append((char) Integer.parseInt(var0, 16));
                }

                return var1.toString();
            } catch (Exception var5) {
                return null;
            }
        }
    }
}
