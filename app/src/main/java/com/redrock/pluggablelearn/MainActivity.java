package com.redrock.pluggablelearn;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(v -> {
            try {
                Class<?> clazz = Class.forName("com.redrock.plugin.pluginNormalClass");
                Method print = clazz.getMethod("print");
                print.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
