package com.univ.toyama.inform_busroute;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BluetoothActivity extends AppCompatActivity
{

    // 定数
    public static final int REQUEST_ENABLEBLUETOOTH = 1; // Bluetooth機能の有効化要求時の識別コード
    public static final int REQUEST_CONNECTDEVICE   = 2; // デバイス接続要求時の識別コード

    // メンバー変数
    public BluetoothAdapter mBluetoothAdapter;    // BluetoothAdapter : Bluetooth処理で必要
    public String mDeviceAddress = "";    // デバイスアドレス

    // DialogFragment
    public TextView textView;
    public DialogFragment dialogFragment;
    public FragmentManager fragmentManager;

    // 画面タップで表示切り替え
    String str1;
    String str2;

    //画像とテキストの切り替え
    private  boolean showCanvas;
    private TestCanvasView testCanvasView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        Intent intent = getIntent();

        testCanvasView = this.findViewById(R.id.test_view1);
        testCanvasView.showCanvas(true);

        showCanvas = true;

        Button button3 = findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showCanvas) {
                    testCanvasView.showCanvas(false);
                    showCanvas = false;
                } else {
                    testCanvasView.showCanvas(true);
                    showCanvas = true;
                }
            }
        });

        //画面遷移

        textView = findViewById(R.id.text_view);

        Button button = findViewById(R.id.button);


        // ボタンタップでAlertを表示させる
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager = getSupportFragmentManager();

                // DialogFragment を継承したAlertDialogFragmentのインスタンス
                dialogFragment = new com.univ.toyama.inform_busroute.AlertDialogFragment(mDeviceAddress);
                // DialogFragmentの表示
                dialogFragment.show(fragmentManager, "test alert dialog");
            }
        });

        // Bluetoothアダプタの取得
        BluetoothManager bluetoothManager = (BluetoothManager)getSystemService( Context.BLUETOOTH_SERVICE );
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if( null == mBluetoothAdapter )
        {    // Android端末がBluetoothをサポートしていない
            Toast.makeText( this, R.string.bluetooth_is_not_supported, Toast.LENGTH_SHORT ).show();
            finish();    // アプリ終了宣言
            return;
        }
    }

    public void setTextView(String message){
        textView.setText(message);
    }


    // 初回表示時、および、ポーズからの復帰時
    @Override
    protected void onResume()
    {
        super.onResume();

        // Android端末のBluetooth機能の有効化要求
        requestBluetoothFeature();
    }

    // Android端末のBluetooth機能の有効化要求
    public void requestBluetoothFeature()
    {
        if( mBluetoothAdapter.isEnabled() )
        {
            return;
        }
        // デバイスのBluetooth機能が有効になっていないときは、有効化要求（ダイアログ表示）
        Intent enableBtIntent = new Intent( BluetoothAdapter.ACTION_REQUEST_ENABLE );
        //startActivityForResult(enableBtIntent, REQUEST_ENABLEBLUETOOTH);
    }

    // 機能の有効化ダイアログの操作結果
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        switch( requestCode )
        {
            case REQUEST_ENABLEBLUETOOTH: // Bluetooth有効化要求
                if( Activity.RESULT_CANCELED == resultCode )
                {    // 有効にされなかった
                    Toast.makeText( this, R.string.bluetooth_is_not_working, Toast.LENGTH_SHORT ).show();
                    finish();    // アプリ終了宣言
                    return;
                }
                break;
            case REQUEST_CONNECTDEVICE: // デバイス接続要求
                String strDeviceName;
                if( Activity.RESULT_OK == resultCode )
                {

                    // デバイスリストアクティビティからの情報の取得
                    strDeviceName = data.getStringExtra( com.univ.toyama.inform_busroute.DeviceListActivity.EXTRAS_DEVICE_NAME );
                    mDeviceAddress = data.getStringExtra( com.univ.toyama.inform_busroute.DeviceListActivity.EXTRAS_DEVICE_ADDRESS );

                }
                else
                {

                    strDeviceName = "";
                    mDeviceAddress = "";
                }

                ( (TextView)findViewById( R.id.textview_devicename ) ).setText( strDeviceName );
                ( (TextView)findViewById( R.id.textview_deviceaddress ) ).setText( mDeviceAddress );
                break;
        }
        super.onActivityResult( requestCode, resultCode, data );
    }

    // オプションメニュー作成時の処理
    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.activity_bluetooth, menu );
        return true;
    }

    // オプションメニューのアイテム選択時の処理
    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        switch( item.getItemId() )
        {
            case R.id.menuitem_search:
                Intent devicelistactivityIntent = new Intent( this, DeviceListActivity.class );
                startActivityForResult( devicelistactivityIntent, REQUEST_CONNECTDEVICE );
                return true;
        }
        return false;
    }


    // タッチイベント
    public boolean onTouchEvent(MotionEvent event){

        TextView textView = (TextView)findViewById(R.id.text_view);
        textView.setMovementMethod(new ScrollingMovementMethod());
        String tv = textView.getText().toString();

        str1 = "information";

        if(tv == str1 && mDeviceAddress.contains("98")){
            str2 = "富山駅前\n　　↑\n海王丸パーク\n　 　|\n新港きっときと市場\n　　 |\n川の駅新港" +
                    "\n　 　|\nクロスベイ新港\n　 　|\n氷見漁港前\n　　↓\nひみ番屋街";
        }
        else{
            str2 = str1;
        }

        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                textView.setText(str2);
        }

        return false;
    }
}
