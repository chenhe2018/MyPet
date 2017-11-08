package com.pet.ch.mypet;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PetService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        if (this.mFloatLayout == null)
            createFlowView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //
    LinearLayout mFloatLayout;//float layout
    WindowManager.LayoutParams wmParams;//params
    WindowManager mWindowManager;//manager

    private void createFlowView() {

        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //
        wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;//背景透明
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//浮动窗口不可聚焦
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        //屏幕左上角
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        //
        mWindowManager.addView(mFloatLayout, wmParams);
        final Button mFloatView = (Button) mFloatLayout.findViewById(R.id.float_id);
        mFloatView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //
        mFloatView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth() / 2;
                wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight() / 2;
                //refresh
                mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                return false;
            }
        });
        mFloatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PetService.this, "onClick", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
