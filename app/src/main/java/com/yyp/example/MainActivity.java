package com.yyp.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.daily.stack.card.StackCardViewPager;
import cn.daily.stack.card.view.pager.PagerAdapter;


/**
 * 主界面
 *
 * Created by yyp on 2019/2/20
 */
public class MainActivity extends AppCompatActivity {

    StackCardViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager = findViewById(R.id.viewpager);

        final List<View> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            View view = LayoutInflater.from(this)
                    .inflate(R.layout.fragment_card_left, null);
            CardView cardView = view.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                }
            });
            list.add(view);
        }
        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));
                return list.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.left_stack:
                startActivity(new Intent(MainActivity.this, StackCardLeftActivity.class));
                break;
            case R.id.right_stack:
                startActivity(new Intent(MainActivity.this, StackCardRightActivity.class));
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
