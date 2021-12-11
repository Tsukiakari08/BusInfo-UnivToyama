package com.univ.toyama.inform_busroute;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.firestore.FirebaseFirestore;

public class Display_Times extends AppCompatActivity implements Async_Callback{
    public static final int PLACE_TO_TIME = 4;
    public ListView listView;
    public ArrayAdapter<String> adapter;
    Spinner spinner;
    Common common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_times);

        Intent display_Times = getIntent();
        common = (Common)getApplication();
        common.initCurrent_str();

        spinner = findViewById(R.id.spinner);
        SetSpinnerItem();
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        listView = findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.emptyView));
    }

    public void SetSpinnerItem() {
        adapter = new ArrayAdapter<>(Display_Times.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.days));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
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

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            GetData_Firestore data = new GetData_Firestore(db);

            if(common.getDirection() != common.getCurrent_str()) {
                common.setDay(string);
                common.setState(PLACE_TO_TIME);

                data.getDataList(common, new Async_Callback() {
                    public void sendData(List<String> list) {
                        if (list.isEmpty() || list.size() == 0) {
                            Log.d("List Error", "Empty List");
                        } else {
                            common.setTime_list(list);
                            adapter = new ArrayAdapter<>(Display_Times.this,
                                    android.R.layout.simple_list_item_1, common.getTime_list());

                            if(common.getTime_list().size() == 1) {
                                adapter.clear();
                            } else {

                            }
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