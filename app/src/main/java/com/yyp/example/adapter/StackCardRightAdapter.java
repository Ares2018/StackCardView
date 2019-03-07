package com.yyp.example.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.yyp.example.bean.NewsBean;
import com.yyp.example.fragment.CardRightFragment;

import cn.daily.stack.card.adapter.BaseStackCardAdapter;

/**
 * 右层叠卡片适配器
 *
 * Created by yyp on 2019/2/18
 */
public class StackCardRightAdapter extends BaseStackCardAdapter<NewsBean> {

    public StackCardRightAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getFragment(int pos) {
        if(getData() == null){
            return CardRightFragment.getInstance(null);
        }else{
            return CardRightFragment.getInstance(getData().get(pos));
        }
    }
}
