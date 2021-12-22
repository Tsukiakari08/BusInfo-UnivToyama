package com.univ.toyama.inform_busroute;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TestCanvasView extends View {
    private Paint paint;
    private Boolean viewflg;
    private Bitmap bmp = null;

    public TestCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        viewflg = true;
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sampleimage);
    }

    public void showCanvas(boolean flg){
        viewflg = flg;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 背景、半透明
        canvas.drawColor(Color.argb(125, 0, 0, 255));

        // Bitmap 画像を表示
        if(viewflg){
            canvas.drawBitmap(bmp, 80, 200, paint);
        }
        // Textの表示
        else{
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(5);
            paint.setTextSize(100);
            paint.setColor(Color.argb(255, 255, 255, 0));
            canvas.drawText("Toyama Station", 120, 600, paint);
        }

    }
}
