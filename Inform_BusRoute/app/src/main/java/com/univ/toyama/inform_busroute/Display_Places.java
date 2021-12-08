package com.univ.toyama.inform_busroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.NullValue;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Display_Places extends AppCompatActivity implements Async_Callback{
    public static final int PLACE_TO_TIME = 4;

    public ListView listView;
    public ArrayAdapter<String> adapter;
    FirebaseFirestore db;
    GetData_Firestore data;
    Spinner spinner;
    Common common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_places);
        Intent display_Places = getIntent();

        common = (Common)getApplication();
        common.initCurrent_str();

        db = FirebaseFirestore.getInstance();
        data = new GetData_Firestore(db);

        SetSpinnerItem();
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        listView = findViewById(R.id.list);
        listView.setOnItemClickListener(new ClickListener());

        adapter = new ArrayAdapter<>(Display_Places.this, android.R.layout.simple_list_item_1,common.getPlace_list());
        listView.setAdapter(adapter);

    }

    private class ClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            String string =(String)parent.getItemAtPosition(position);
            common.setPlace_name(string);

            if(common.getPlace_name() != common.getCurrent_str()) {
                common.setCurrent_str(string);
                common.setDay("平日");
                common.setState(PLACE_TO_TIME);

                data.getDataList(common, new Async_Callback() {
                    public void sendData(List<String> list) {
                        if (list.isEmpty() || list.size() == 0) {
                            Log.d("List Error", "Empty List");
                        } else {
                            common.setTime_list(list);
                            Intent display_Times = new Intent(Display_Places.this, Display_Times.class);
                            startActivity(display_Times);
                        }
                    }
                });
            }
        }
    }

    public void SetSpinnerItem(){
        spinner = findViewById(R.id.spinner);
        adapter = new ArrayAdapter<>(Display_Places.this,  android.R.layout.simple_spinner_item, common.getDirection_list());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            // Spinner を取得
            Spinner spinner = (Spinner) parent;
            // 選択されたアイテムのテキストを取得
            String string = spinner.getSelectedItem().toString();
            common.setCurrent_str(string);
            if(common.getDirection() != common.getCurrent_str()) {
                common.setDirection(string);
                common.setState(3);

                data.getDataList(common, new Async_Callback() {
                    public void sendData(List<String> list) {
                        if (list.isEmpty() || list.size() == 0) {
                            Log.d("List Error", "Empty List");
                        } else {
                            common.setPlace_list(list);
                            adapter = new ArrayAdapter<>(Display_Places.this,
                                    android.R.layout.simple_list_item_1, common.getPlace_list());
                            listView.setAdapter(adapter);
                        }
                    }
                });
            }
        }

        public void onNothingSelected(AdapterView parent) {
        }
    }



    @Override
    public void sendData(List<String> list) {

    }
}