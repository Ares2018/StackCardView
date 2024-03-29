package com.yyp.example.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.yyp.example.bean.NewsBean;
import com.yyp.example.fragment.CardLeftFragment;

import cn.daily.stack.card.adapter.BaseStackCardAdapter;

/**
 * 左层叠卡片适配器
 * <p>
 * Created by yyp on 2019/2/18
 */
public class StackCardLeftAdapter extends BaseStackCardAdapter<NewsBean> {

    public StackCardLeftAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getFragment(int pos) {
        if(getData() == null){
            return CardLeftFragment.getInstance(null);
        }else{
            return CardLeftFragment.getInstance(getData().get(pos));
        }
    }

}
