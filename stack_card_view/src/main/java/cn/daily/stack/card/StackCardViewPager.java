package cn.daily.stack.card;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AdapterView;

import cn.daily.stack.card.view.custom.ViewPager;


/**
 * 自定义ViewPager
 *
 * 处理ViewPager滑动与子页面点击事件的冲突
 *
 * Created by yyp on 2019/3/1
 */
public class StackCardViewPager extends ViewPager {

    private float startX, startY;
    private float endX, endY;
    public int intervalTime;//滑动最小间隔时间

    public StackCardViewPager(@NonNull Context context) {
        super(context);
    }

    public StackCardViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private long nowTime;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        endX = ev.getX();
        endY = ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (intervalTime!=0&&System.currentTimeMillis()-nowTime<intervalTime){
                    return false;
                }else {
                    nowTime = System.currentTimeMillis();
                }
                startX = ev.getX();
                startY = ev.getY();
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if((endX - startX) != 0){
                    float k = calculateGradient(startX, startY, endX, endY);
                    if(Math.abs(k) > 0){ //滑动路径斜率大于0 进行拦截
                        return true;
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 计算斜率
     *
     * @param x1 起点x坐标
     * @param y1 起点y坐标
     * @param x2 终点x坐标
     * @param y2 终点y坐标
     * @return 斜率
     */
    private float calculateGradient(float x1, float y1, float x2, float y2){
        float deltaX = x2 - x1;
        float deltaY = y2 - y1;
        return  deltaY / deltaX;
    }

}
