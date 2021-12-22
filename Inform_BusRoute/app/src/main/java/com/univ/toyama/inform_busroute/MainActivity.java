package com.univ.toyama.inform_busroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button timetable = findViewById(R.id.timetable);
        Button bluetooth = findViewById(R.id.bluetooth);

        timetable.setOnClickListener(new T_ClickListener());
        bluetooth.setOnClickListener(new B_ClickListener());
    }

    private class T_ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent display_Prefectures = new Intent(MainActivity.this, Display_Prefectures.class);
            startActivity(display_Prefectures);
        }
    }

    private class B_ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent Bluetooth = new Intent(MainActivity.this, BluetoothActivity.class);
            startActivity(Bluetooth);
        }
    }
}