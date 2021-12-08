package com.univ.toyama.inform_busroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity{
    public Button timetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timetable = findViewById(R.id.timetable);
        timetable.setOnClickListener(new ClickListener());
    }

    private class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent display_Prefectures = new Intent(MainActivity.this, Display_Prefectures.class);
            startActivity(display_Prefectures);
        }
    }
}