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

public class Display_Lines extends AppCompatActivity implements Async_Callback{
    public ListView listView;
    Common common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lines);
        Intent display_Lines = getIntent();

        common = (Common)getApplication();
        common.initCurrent_str();

        listView = findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Display_Lines.this,
                android.R.layout.simple_list_item_1,common.getLine_list());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ClickListener());
    }

    private class ClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            String string =(String)parent.getItemAtPosition(position);

            common.setLine_name(string);

            if(common.getLine_name() != common.getCurrent_str()) {
                common.setCurrent_str(string);
                common.setState(2);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                GetData_Firestore data = new GetData_Firestore(db);

                data.getDataList(common, new Async_Callback() {
                    public void sendData(List<String> list) {
                        if (list.isEmpty() || list.size() == 0) {
                            Log.d("List Error", "Empty List");
                        } else {
                            common.setDirection_list(list);
                            common.setDirection(common.getDirection_list().get(0));
                            common.setState(3);

                            data.getDataList(common, new Async_Callback() {
                                public void sendData(List<String> list) {
                                    if (list.isEmpty() || list.size() == 0) {
                                        Log.d("List Error", "Empty List");
                                    } else {
                                        common.setPlace_list(list);
                                        Intent display_Places = new Intent(Display_Lines.this, Display_Places.class);
                                        startActivity(display_Places);
                                    }
                                }
                            });
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