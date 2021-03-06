package com.univ.toyama.inform_busroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Display_Prefectures extends AppCompatActivity implements Async_Callback{
    public static final int PREFECTURE_TO_CITY = 0;
    public ListView listView;
    Common common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_prefectures);
        Intent intent = getIntent();
        common = (Common)getApplication();
        common.initCurrent_str();

        listView = findViewById(R.id.list);
        listView.setOnItemClickListener(new ClickListener());
    }

    private class ClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            String string =(String)parent.getItemAtPosition(position);

            common.setPrefecture_name(string);

            if(common.getPrefecture_name() != common.getCurrent_str()) {
                common.setCurrent_str(string);
                common.setState(PREFECTURE_TO_CITY);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                GetData_Firestore data = new GetData_Firestore(db);

                data.getDataList(common, new Async_Callback() {
                    public void sendData(List<String> list) {
                        if (list.isEmpty() || list.size() == 0) {
                            Log.d("List Error", "Empty List");
                        } else {
                            common.setCity_list(list);
                            Intent display_Cities = new Intent(Display_Prefectures.this, Display_Cities.class);
                            startActivity(display_Cities);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void sendData(List<String> list) {

    }

}