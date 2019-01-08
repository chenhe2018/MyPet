package com.pet.ch.mypet;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
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
        if(mFloatLayout!=null)
            mWindowManager.removeView(mFloatLayout);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //主编码区
    /**
     * 定义浮动窗口布局
     */
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    /**
     *创建浮动窗口，设置布局参数对象
     */
    WindowManager mWindowManager;

    /**
     * 动画对象
     */
    private AnimationDrawable animationDrawable;
    /**
     * 图像视图
     */
    private ImageView imageView;

    private void createFlowView() {
        //获取WM
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //获取wm参数
        wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;//背景透明
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//浮动窗口不可聚焦
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        //设置屏幕左上角,设置悬浮窗口长款数据
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        //添加布局文件
        mWindowManager.addView(mFloatLayout, wmParams);
        //添加浮动窗口按钮
        imageView =(ImageView) mFloatLayout.findViewById(R.id.imageview);
        imageView.setBackgroundResource(R.drawable.ali_scan);//设置一个可变换的背景
        animationDrawable=(AnimationDrawable)imageView.getBackground();
        //开启动画
        animationDrawable.start();
        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED));
        //设置监听浮动窗口的触摸移动
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //getRawX为触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                wmParams.x = (int) event.getRawX() - imageView.getMeasuredWidth() / 2;
                wmParams.y = (int) event.getRawY() - imageView.getMeasuredHeight() / 2 - 25;
                //刷新
                mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                return false;
            }
        });

        //设置点击监听
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PetService.this, "onClick", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
