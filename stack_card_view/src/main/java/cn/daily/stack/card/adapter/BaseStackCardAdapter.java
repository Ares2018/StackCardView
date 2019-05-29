package cn.daily.stack.card.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import java.util.Collections;
import java.util.List;

import cn.daily.stack.card.view.custom.FragmentPagerAdapter;

/**
 * 层叠卡片适配器基类
 *
 * Created by yyp on 2019/2/21
 */
public abstract class BaseStackCardAdapter<T> extends FragmentPagerAdapter {

    private List<T> mData;
    private boolean isReverse = false; //数据是否倒序排列 默认正序

    public BaseStackCardAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 设置数据集合 处理排序
     *
     * @param list 数据集合
     * @param reverse 是否倒序排序
     */
    public void setList(@NonNull List<T> list, boolean reverse) {
        this.isReverse = reverse;
        if(reverse){
            Collections.reverse(list); //倒序排序
        }
        this.mData = list;
        notifyDataSetChanged();
    }

    /**
     * 获取数据集合
     *
     * @return .
     */
    public List<T> getData() {
        if(mData == null){ //空指针异常
            throw new NullPointerException("data isn't null.");
        }
        return mData;
    }

    /**
     * 获取在数据集合中的实际位置
     *
     * @param pos ViewPager中的页面位置
     * @return 数据集合中的实际位置
     */
    public int toRealPosition(int pos){
        return pos % getData().size();
    }

    /**
     * 获取实际浏览到第几张卡片
     *
     * @param pos ViewPager中的页面位置
     * @return 实际浏览到第几张卡片
     */
    public int toRealShowPosition(int pos) {
        if(isReverse){
            return getData().size() - toRealPosition(pos);
        }else{
            return toRealPosition(pos) + 1;
        }
    }

    /**
     * 获取中间且是第一个卡片的位置
     *
     * @return .
     */
    public int getMiddlePosition(){
        int middlePos = getCount() / 2;
        if(isReverse){
            return middlePos - (middlePos % getData().size()) + (getData().size() - 1);
        }else{
            return middlePos - (middlePos % getData().size());
        }
    }

    /**
     * 由子类实现 返回卡片Fragment
     *
     * @param pos 数据集合中的实际位置
     * @return Fragment
     */
    public abstract Fragment getFragment(int pos);

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Fragment getItem(int i) {
        return getFragment(toRealPosition(i));
    }
}
