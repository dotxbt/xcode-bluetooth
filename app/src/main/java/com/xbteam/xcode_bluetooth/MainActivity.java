package com.xbteam.xcode_bluetooth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // example
        CustomPrinter printer = new CustomPrinter(this);
        printer.directPrint("DC:0D:30:0A:79:39", "THIS IS HEADER", "This is contents", "THIS IS FOOTER");
    }
}