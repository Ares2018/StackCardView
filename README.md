# 层叠片卡效果

## 说明

通过设置 ViewPager 的 PageTransformer 实现卡片层叠效果，支持左层叠和右层叠两种效果

## 添加依赖

1、 主工程 build.gradle 添加仓库地址:

```gradle
allprojects {
    repositories {
        maven { url "http://10.100.62.98:8086/nexus/content/groups/public/" }
    }
}
```

2、 项目工程 build.gradle 添加依赖:


```gradle
implementation 'cn.daily.android:stack-card-view:0.0.6'
```

## 用法

以左层叠效果为例

1、 控件使用 StackCardViewPager

2、 卡片使用 Fragment，布局示例如下：

主要是 CardView 的属性 android:layout_gravity 设置为 right，居右；右层叠反之。

注意：为了防止卡片旋转时，上下部分界面被切掉，需要给 CardView 的 android:layout_marginTop 和 android:layout_marginBottom 设置值，该值要大于等于卡片宽度的十分之一

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".fragment.CardRightFragment">

    <android.support.v7.widget.CardView
        android:id="@+id/card_view"
        android:layout_width="280dp"
        android:layout_marginTop="30dp"
        android:layout_marginBottom="30dp"
        android:layout_height="match_parent"
        android:layout_gravity="right">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <ImageView
                android:id="@+id/image"
                android:layout_width="match_parent"
                android:layout_height="200dp"
                android:src="@drawable/news_img1"
                android:scaleType="centerCrop"/>

            <TextView
                android:id="@+id/title"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textSize="21sp"
                android:padding="5dp"/>

            <TextView
                android:id="@+id/content"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="15sp"
                android:lineSpacingMultiplier="1.2"
                android:padding="5dp"/>

            <LinearLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:orientation="horizontal"
                android:padding="5dp"
                android:layout_marginTop="20dp"
                android:layout_marginBottom="10dp">
                <TextView
                    android:id="@+id/column"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:textSize="15sp"
                    android:textColor="@color/red"/>

                <TextView
                    android:id="@+id/read_count"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:textSize="15sp"
                    android:layout_marginLeft="10dp"/>
            </LinearLayout>

        </LinearLayout>


    </android.support.v7.widget.CardView>
</LinearLayout>
```

3、 自定义 ViewPager 的适配器，继承 BaseStackCardAdapter，示例如下：

需要实现 getFragment() 的逻辑

```java
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
```

4、 为 ViewPager 设置 PageTransFormer，这里使用StackCardPageTransformer，示例如下：


```java
stackCardViewPager.setPageTransformer(true, StackCardPageTransformer.getBuild()
                .setViewType(PageTransformerConfig.LEFT) //层叠方向
                .setTranslationOffset(DensityUtils.dp2px(this, 45f)) //左右位置偏移量
                .setScaleOffset(DensityUtils.dp2px(this, 50f)) //缩放偏移量
                .setAlphaOffset(0.5f) //卡片透明度偏移量
                .setRotationOffset(10) //卡片滑动时的最大旋转角度
                .setMaxShowPage(3) //最大显示的页数
                .create(stackCardViewPager));
```

参数说明：

| 参数 | 类型 | 描述 |
|:---|:---|:---|
| viewType | int | 卡片层叠方向：左、右 |
| translationOffset | float | 卡片向左/右的偏移量，单位用px |
| scaleOffset | float | 卡片的缩放偏移量，单位用px |
| alphaOffset | float | 底下的卡片相对于上一层卡片的透明度 |
| rotationOffset | float | 最大旋转角度 |
| maxShowPage | int | 最多显示的卡片个数 |

5、 加载数据

左层叠效果：需要将数据倒序排列，并将 ViewPager 定位到中间位置

```java
stackCardLeftAdapter.setList(list, true); //数据倒序排列
stackCardViewPager.setCurrentItem(stackCardLeftAdapter.getMiddlePosition(), false) //定位到中间位置
```

右层叠效果：数据顺序排列，并将 ViewPager 定位到中间位置

```java
stackCardRightAdapter.setList(list, false); //数据顺序排列
stackCardViewPager.setCurrentItem(stackCardRightAdapter.getMiddlePosition(), false); //定位到中间位置
```

6、显示滑动到第几张卡片


```java
stackCardLeftAdapter.toRealShowPosition(position); //获取实际浏览到的卡片位置 1~n
stackCardLeftAdapter.getData().size(); //获取卡片总个数

/*
    监听ViewPager页面滑动
    因为改了源码，所以这里请用 StackViewViewPager.OnPageChangeListener
 */
stackCardViewPager.addOnPageChangeListener(new StackViewViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //显示浏览到第几张
        imageShowPosition.setText(String.format("%s/%s",
                stackCardLeftAdapter.toRealShowPosition(position), stackCardLeftAdapter.getData().size()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});
```

7、设置切换页面的最小滑动距离

```java
stackCardViewPager.setMinScrollDistanceOffset(20); //单位dp
```

