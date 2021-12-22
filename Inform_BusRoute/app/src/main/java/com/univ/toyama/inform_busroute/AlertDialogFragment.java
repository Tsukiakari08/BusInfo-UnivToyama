package com.univ.toyama.inform_busroute;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

// DialogFragment を継承したクラス
public class AlertDialogFragment extends DialogFragment {

    // 選択肢のリスト

    public String[] menulist = {"0123","4567","8901"};
    public String Address;
    public AlertDialogFragment(String Address){
        this.Address=Address;
    }
    public void menu(){
        MainActivity menu = new MainActivity();
        if(Address == ""){
            menulist[0] = "選択(1)";

        }
        else if(Address.contains("98") && Address.contains("D3") && Address.contains("B1")
        && Address.contains("FD") && Address.contains("C5") && Address.contains("8A")){
            menulist[0] = "ぶりかにバス";
        }
        else{
            menulist[0] = "test";
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        // タイトル
        alert.setTitle("Test AlertDialog");
        alert.setItems(menulist, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {

                // 選択１
                if (idx == 0) {
                    menu();
                    setMassage(menulist[0]);
                }
                // 選択２
                else if (idx == 1) {
                    setMassage(menulist[1]);
                }
                // 選択３, idx == 2
                else{
                    setMassage(menulist[2]);
                }
            }
        });

        return alert.create();
    }

    public void setMassage(String message) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity!= null) {
            //mainActivity.setTextView(message);
        }
    }
}