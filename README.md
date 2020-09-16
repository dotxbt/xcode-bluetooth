# xcode-bluetooth
_**Bluetooth Thermal Printer Library**_

[![](https://jitpack.io/v/dotxbt/xcode-bluetooth.svg)](https://jitpack.io/#dotxbt/xcode-bluetooth)
### How to implement?
Add this library for AndroidX :
```gradle
dependencies {
  implementation 'com.github.dotxbt:xcode-bluetooth:0.0.3'
}
```

Add this library for AppCompat 28.0.0 :
```gradle
dependencies {
  implementation 'com.github.dotxbt:xcode-bluetooth:0.0.2'
}
```

### Example Custom Printer

```java
public class CustomPrinter {
    private BTPrinter printer;
    private Context context;

    // COMMAND
    private byte[] command = { 0x1B, 0x74, (byte) 0x80 };
    byte[] ChCommand = { 0x1B, 0x74, (byte) 0x80 };
    byte[] JaCommand = { 0x1B, 0x74, (byte) 0x82 };
    byte[] KoCommand = { 0x1B, 0x74, (byte) 0x84 };

    // ECNDOING
    private static String encode = "GB2312";// default
    private static final String ChCode = "GB2312";
    private static final String JaCode = "shift-jis";
    private static final String KoCode = "euc-kr";
    private static final String EnCode = "GBK";

    // FONT SIZE
    private static final byte[] FONT_CUSTOM_NORMAL = new byte[] {12, 21, 8};
    private static final byte[] FONT_22 = new byte[]{29, 33, 0};
    private static final byte[] BIG_FONT = new byte[]{29, 33, 1};

    public CustomPrinter(Context context) {
        this.context = context;
        printer = new BTPrinter();
    }

    private void printContent(String header, String content, String footer) {
        printer.reset();
        printer.write(BTCommands.ALIGN_CENTER);
        printer.write(BTCommands.DEFAULT_NORMAL_FONT);
        printer.print(header);
        printer.write(command);
        printer.write(BTCommands.DEFAULT_NORMAL_FONT);
        printer.print("--------------------------------");
        printer.write(BTCommands.ALIGN_LEFT);
        printer.print(content);
        printer.write(BTCommands.ALIGN_CENTER);
        printer.print("\n--------------------------------");
        printer.print(footer);
        printer.print("\n--------------------------------");
        printer.flush();
        printer.finish();
    }

    public void directPrint(String macAdress, String header, final String content, final  String footer) {
        Toast.makeText(context, "Scanning bluetooth...", Toast.LENGTH_SHORT).show();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(context, "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        } else if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(context, "Bluetooth is not enabled",Toast.LENGTH_SHORT).show();
        } else {
            if (macAdress != null && macAdress !=" ") {
                if (tryToConnect(macAdress)){
                    printContent(header, content, footer);
                    printer.close();
                } else {
                    Toast.makeText(context, "Connection Failed!",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Connection Failed!",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void printBitmapFromResource(int resource) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.outWidth = 512;
        InputStream is = context.getResources().openRawResource(resource);
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
        printer.write(BTCommands.ALIGN_CENTER);
        printer.printBitmap(bitmap);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = null;
    }

    private boolean tryToConnect(String macAdressBluetooth) {
        if (macAdressBluetooth != " " && macAdressBluetooth !=null) {
            try {
                BluetoothDevice bluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(macAdressBluetooth);
                boolean isConnect = printer.connect(bluetoothDevice);
                return isConnect;
            } catch (Exception e) {
                return false;
            }
        } else {
            return  false;
        }
    }
}
```

### Use in Activity or Fragment
> Example Bluetooth MacAddress : DC:0D:30:0A:79:39
- Activity
```java
CustomPrinter printer = new CustomPrinter(this);
printer.directPrint("DC:0D:30:0A:79:39", "THIS IS HEADER", "This is contents", "THIS IS FOOTER");
```

- Fragment
```java
CustomPrinter printer = new CustomPrinter(getContext());
printer.directPrint("DC:0D:30:0A:79:39", "THIS IS HEADER", "This is contents", "THIS IS FOOTER");
```
