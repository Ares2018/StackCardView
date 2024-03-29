package com.yyp.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyp.example.adapter.StackCardRightAdapter;
import com.yyp.example.bean.NewsBean;
import com.yyp.example.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import cn.daily.stack.card.StackCardViewPager;
import cn.daily.stack.card.config.PageTransformerConfig;
import cn.daily.stack.card.transformer.StackCardPageTransformer;

/**
 * 右层叠展示界面
 *
 * Created by yyp on 2019/2/20
 */
public class StackCardRightActivity extends AppCompatActivity {

    private StackCardViewPager stackCardViewPager;
    private StackCardRightAdapter stackCardRightAdapter;

    private ImageView back;
    private TextView imageShowPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_card_right);

        stackCardViewPager = findViewById(R.id.stack_card_vp);
        back = findViewById(R.id.image_back);
        imageShowPosition = findViewById(R.id.image_show_position);

        configRightStackCardViewPager();
        loadData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 配置右层叠 StackCardViewPager
     */
    private void configRightStackCardViewPager() {
        stackCardViewPager.setPageTransformer(true, StackCardPageTransformer.getBuild()
                .setViewType(PageTransformerConfig.RIGHT) //层叠方向
                .setTranslationOffset(DensityUtils.dp2px(this, 45f)) //左右位置偏移量
                .setScaleOffset(DensityUtils.dp2px(this, 50f)) //缩放偏移量
                .setAlphaOffset(0.5f) //卡片透明度偏移量
                .setRotationOffset(10) //卡片滑动时的最大旋转角度
                .setMaxShowPage(3) //最大显示的页数
                .create(stackCardViewPager));

        //创建适配器
        stackCardRightAdapter = new StackCardRightAdapter(getSupportFragmentManager());
        stackCardViewPager.setAdapter(stackCardRightAdapter);

        stackCardViewPager.addOnPageChangeListener(new StackCardViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //显示浏览到第几张
                imageShowPosition.setText(String.format("%s/%s",
                        stackCardRightAdapter.toRealShowPosition(i), stackCardRightAdapter.getData().size()));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 加载右层叠卡片数据
     */
    private void loadData(){
        List<NewsBean> list = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            list.add(new NewsBean("", "你好！外星人" + i, getResources().getString(R.string.content),
                    "在浙里", 100 + i * 10));
        }

        stackCardRightAdapter.setList(list, false);
        stackCardViewPager.setCurrentItem(stackCardRightAdapter.getMiddlePosition(), false);
    }
}
