package com.univ.toyama.inform_busroute;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class GetData_Firestore implements Async_Callback{

    public String get_data;                                 //取得したデータの格納先
    private static final String TAG = "Error";              //Logのタグ
    public static final int PREFECTURE_TO_CITY = 0;
    public static final int CITY_TO_LINE= 1;
    public static final int DIRECTION = 2;
    public static final int LINE_TO_PLACE = 3;
    public static final int PLACE_TO_TIME = 4;
    private final FirebaseFirestore db;

    //コンストラクタ
    GetData_Firestore(FirebaseFirestore db) {
        this.get_data = null;
        this.db = db;
    }

    //データのリスト取得
    public void getDataList(Common common, Async_Callback cb) {
        List<String> list = new ArrayList<>();
        CollectionReference collectionRef = collectionPath(db, common);
        Query q = executeQuery(collectionRef, common);

        q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ArrayList<String> al = toArrayList(document, common);
                        if (al.isEmpty() || al.size() == 0) {
                            //リストが空の場合
                        } else {
                            list.addAll(al);  //listにデータの追加
                        }
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
                cb.sendData(list);  //コールバック
            }
        });
    }

    //pathの設定
    public CollectionReference collectionPath(FirebaseFirestore db, Common common){
        CollectionReference collectionRef = db.collection("NULL");
        switch (common.getState()) {
            case PREFECTURE_TO_CITY:
                collectionRef = db.collection(common.getPrefecture_name());
                break;

            case CITY_TO_LINE:
                collectionRef = db.collection(common.getPrefecture_name() + "/City/"
                        + common.getCity_name());
                break;

            case DIRECTION:
            case LINE_TO_PLACE:
                collectionRef = db.collection(common.getPrefecture_name() + "/City/"
                        + common.getCity_name() + "/Line/" + common.getLine_name());
                break;

            case PLACE_TO_TIME:
                collectionRef = db.collection(common.getPrefecture_name() + "/City/"
                        + common.getCity_name() + "/Line/" + common.getLine_name() + "/"+ common.getDirection() +"/" + common.getPlace_name());
                break;
        }
        return collectionRef;
    }

    //条件付きクエリ
    public Query executeQuery(CollectionReference collectionRef, Common common){
        Query query = collectionRef;
        switch (common.getState()) {
            case DIRECTION:
                query = collectionRef.whereEqualTo("direction_data", true);
                break;

            case LINE_TO_PLACE:
                query = collectionRef.whereEqualTo("方向", common.getDirection());
                break;

            case PLACE_TO_TIME:
                query = collectionRef.whereEqualTo("day", common.getDay());
                break;
        }
        return query;
    }


    //documentをBusクラスにしてデータの取得
    public ArrayList<String> toArrayList(QueryDocumentSnapshot document, Common common) {
        Bus bus = document.toObject(Bus.class);       //documentからBusクラスの作成
        ArrayList<String> list = new ArrayList<>();
        switch (common.getState()) {
            case PREFECTURE_TO_CITY: list = bus.getCity();
            break;

            case CITY_TO_LINE: list = bus.getLine();
            break;

            case DIRECTION: list = bus.getDirection();
            break;

            case LINE_TO_PLACE: list = bus.getPlace();
            break;

            case PLACE_TO_TIME: list = bus.getTime();
            break;
        }
        return list;
    }

    //コールバック用
    @Override
    public void sendData(List<String> list) {

    }
}


