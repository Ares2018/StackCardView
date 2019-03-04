package com.yyp.stackcardview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义ViewPager
 *
 * 处理ViewPager滑动与子页面点击事件的冲突
 *
 * Created by yyp on 2019/3/1
 */
public class StackCardViewPager extends ViewPager {

    private float x1 = 0, x2 = 0;
    private float y1 = 0, y2 = 0;

    public StackCardViewPager(@NonNull Context context) {
        super(context);
    }

    public StackCardViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = ev.getX();
                y1 = ev.getY();
            case MotionEvent.ACTION_MOVE:
                x2 = ev.getX();
                y2 = ev.getY();
            case MotionEvent.ACTION_UP:
                if((x2 - x1) != 0){
                    float k = (y2 - y1) / (x2 - x1);
                    if(Math.abs(k) > 0){ //滑动路径斜率大于0 进行拦截
                        return true;
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
