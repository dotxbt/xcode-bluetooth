package com.xbteam.xcode_btlibs;

public final class BTCommands {
    public static final int MAX_WIDTH = 384;
    public static final byte[] HT = new byte[]{9};
    public static final byte[] LF = new byte[]{10};
    public static final byte[] FF = new byte[]{12};
    public static final byte[] CR = new byte[]{13};
    public static final byte[] DEFAULT_ESC_SP_R = new byte[]{27, 32, 0};
    public static final byte[] DEFAULT_ESC_SP_LINE = new byte[]{27, 51, 0};
    public static final byte[] RESET = new byte[]{27, 64};
    public static final byte[] ALIGN_LEFT = new byte[]{27, 97, 0};
    public static final byte[] ALIGN_CENTER = new byte[]{27, 97, 1};
    public static final byte[] ALIGN_RIGHT = new byte[]{27, 97, 2};
    public static final byte[] DEFAULT_NORMAL_FONT = new byte[]{29, 33, 0};
    public static final byte[] DEFAULT_BIG_FONT = new byte[]{29, 33, 1};
    public static final byte[] DEFAULT_SMALL_FONT = new byte[]{27, 116, 13};
    public static final byte UPC_A = 65;
    public static final byte UPC_E = 66;
    public static final byte EAN13 = 67;
    public static final byte EAN8 = 68;
    public static final byte CODE39 = 69;
    public static final byte ITF = 70;
    public static final byte CODEBAR = 71;
    public static final byte CODE93 = 72;
    public static final byte CODE128 = 73;

    public BTCommands() {
    }
}