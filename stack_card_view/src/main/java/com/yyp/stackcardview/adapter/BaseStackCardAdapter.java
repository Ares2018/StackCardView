package com.yyp.stackcardview.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * 层叠卡片适配器基类
 *
 * Created by yyp on 2019/2/21
 */
public abstract class BaseStackCardAdapter<T> extends FragmentPagerAdapter {

    private List<T> list;
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
    public void setList(List<T> list, boolean reverse) {
        this.isReverse = reverse;
        if(reverse){
            Collections.reverse(list); //倒序排序
        }
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 获取数据集合
     *
     * @return .
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 获取在数据集合中的实际位置
     *
     * @param pos ViewPager中的页面位置
     * @return 数据集合中的实际位置
     */
    public int toRealPosition(int pos){
        return pos % getList().size();
    }

    /**
     * 获取实际浏览到第几张卡片
     *
     * @param pos ViewPager中的页面位置
     * @return 实际浏览到第几张卡片
     */
    public int toRealShowPosition(int pos){
        if(isReverse){
            return getList().size() - toRealPosition(pos);
        }else{
            return toRealPosition(pos) + 1;
        }
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
