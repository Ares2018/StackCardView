package cn.daily.stack.card.transformer;

import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import cn.daily.stack.card.config.PageTransformerConfig;
import cn.daily.stack.card.view.custom.ViewPager;

/**
 * 层叠卡片Transformer
 *
 * 支持左层叠、右层叠
 *
 * Created by yyp on 2019/2/20
 */
public class StackCardPageTransformer implements ViewPager.PageTransformer {

    private final String TAG = "StackCardPageTransformer";

    private Build mBuild;

    private StackCardPageTransformer(Build build) {
        mBuild = build;
    }

    public static Build getBuild() {
        return new Build();
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        transform(page, position);
    }

    /**
     * 处理卡片切换效果
     *
     * @param page 卡片视图
     * @param position 对应position变化
     */
    @TargetApi(android.os.Build.VERSION_CODES.LOLLIPOP)
    private void transform(View page, float position) {
        if(page == null){
            return;
        }

        //根据类型处理层叠情况
        switch (mBuild.getViewType()){
            case PageTransformerConfig.LEFT:
                leftTransform(page, position);
                break;
            case PageTransformerConfig.RIGHT:
                rightTransform(page, position);
                break;
        }
    }

    /**
     * 左层叠 从左往右
     *
     * @param page
     * @param position
     */
    @TargetApi(android.os.Build.VERSION_CODES.LOLLIPOP)
    private void leftTransform(View page, float position){
//        String tag = (String) page.getTag();
//        Log.e("lujialei",tag+"===11111===="+position);
        if (position <= 0f) {
//            Log.e("lujialei",tag+"===22222===="+position);
            page.setRotation(0);
            if(-position <= (mBuild.getMaxShowPage() - 1)){ //处理可见的卡片
                /*
                    移动X轴坐标
                    将卡片重叠，再做偏移
                 */
                page.setTranslationX((page.getWidth() * -position) + (mBuild.getTranslationOffset() * position));
                /*
                    移动Z轴坐标
                    position绝对值越大，越往下移动
                 */
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                    page.setTranslationZ(position);
                }
                /*
                    卡片缩放
                    position绝对值越大，卡片越小
                 */
                float scale;
                if(page.getWidth() <= 0f){ //处理页面宽度，即分母为0的情况
                    scale = 1f;
                }else{
                    scale = (page.getWidth() + mBuild.getScaleOffset() * position) / page.getWidth();
                }
                page.setScaleX(scale);
                page.setScaleY(scale);
                /*
                    卡片透明度渐变
                    1 ~ mBuild.getAlphaOffset() 的 -position 次方
                 */
                float alpha = (float) Math.pow(mBuild.getAlphaOffset(), -position);
                page.setAlpha(alpha);

            }else if(-position <= mBuild.getMaxShowPage()) { //处理过渡的卡片
                /*
                    移动X轴坐标
                    与第 mBuild.getMaxShowPage()-1 张卡片偏移距离一致
                 */
                page.setTranslationX((page.getWidth() * -position) + mBuild.getTranslationOffset()
                        * (-mBuild.getMaxShowPage() + 1));
                /*
                    移动Z轴坐标
                    position绝对值越大，越往下移动
                 */
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                    page.setTranslationZ(position);
                }

                /*
                    卡片缩放
                    position绝对值越大，卡片越小
                 */
                float scale;
                if(page.getWidth() <= 0f){ //处理页面宽度，即分母为0的情况
                    scale = 1f;
                }else{
                    scale = (page.getWidth() + mBuild.getScaleOffset() * position) / page.getWidth();
                }
                page.setScaleX(scale);
                page.setScaleY(scale);
                /*
                    卡片透明度渐变
                    0 ~ mBuild.getAlphaOffset() 的 mBuild.getMaxShowPage()-1 次方
                 */
                float alpha = (float) Math.pow(mBuild.getAlphaOffset(), mBuild.getMaxShowPage()-1)
                        * (mBuild.getMaxShowPage() + position);
                page.setAlpha(alpha);

            }else{ //处理不可见的卡片
//                page.setAlpha(0);
            }
        }else if(position <= 1f){
//            Log.e("lujialei",tag+"===33333===="+position+"==visiable=="+page.getVisibility());
            /*
                旋转角度
                往右划 0 ~ mBuild.getRotationOffset()，往左划 mBuild.getRotationOffset() ~ 0
             */
            page.setRotation((mBuild.getRotationOffset() * position));
        }

        if(position == 0f){
            page.setClickable(true); //允许最上面的卡片进行点击
        }else{
            page.setClickable(false);
        }
    }

    /**
     * 右层叠 从右往左
     *
     * @param page
     * @param position
     */
    private void rightTransform(View page, float position){
        if (position >= 0f) {
            if(position <= (mBuild.getMaxShowPage() - 1)){ //处理可见的卡片
                 /*
                    移动X轴坐标
                    将卡片重叠，再做偏移
                 */
                page.setTranslationX((page.getWidth() * -position) + (mBuild.getTranslationOffset() * position));
                /*
                    卡片缩放
                    position越大，卡片越小
                 */
                float scale;
                if(page.getWidth() <= 0f){ //处理页面宽度，即分母为0的情况
                    scale = 0;
                }else{
                    scale = (page.getWidth() - mBuild.getScaleOffset() * position) / page.getWidth();
                }
                page.setScaleX(scale);
                page.setScaleY(scale);
                /*
                    卡片透明度渐变
                    1 ~ mBuild.getAlphaOffset() 的 position 次方
                 */
                float alpha = (float) Math.pow(mBuild.getAlphaOffset(), position);
                page.setAlpha(alpha);

            }else if(position <= mBuild.getMaxShowPage()) { //处理过渡的卡片
                /*
                    移动X轴坐标
                    与第 mBuild.getMaxShowPage()-1 张卡片偏移距离一致
                 */
                page.setTranslationX((page.getWidth() * -position) + mBuild.getTranslationOffset()
                        * (mBuild.getMaxShowPage() - 1));
                /*
                    卡片缩放
                    position越大，卡片越小
                 */
                float scale;
                if(page.getWidth() <= 0f){ //处理页面宽度，即分母为0的情况
                    scale = 0;
                }else{
                    scale = (page.getWidth() - mBuild.getScaleOffset() * position) / page.getWidth();
                }
                page.setScaleX(scale);
                page.setScaleY(scale);
                /*
                    卡片透明度渐变
                    0 ~ mBuild.getAlphaOffset() 的 mBuild.getMaxShowPage()-1 次方
                 */
                float alpha = (float) Math.pow(mBuild.getAlphaOffset(), mBuild.getMaxShowPage() - 1)
                        * (mBuild.getMaxShowPage() - position);
                page.setAlpha(alpha);

            }else{ //处理不可见的卡片
                page.setAlpha(0);
            }
        }else if(position >= -1f){
            /*
                旋转角度
                往右划 -mBuild.getRotationOffset() ~ 0，往左划 0 ~ -mBuild.getRotationOffset()
             */
            page.setRotation((mBuild.getRotationOffset() * position));
        }

        if(position == 0f){
            page.setClickable(true); //允许最上面的卡片进行点击
        }else{
            page.setClickable(false);
        }
    }

    /**
     * 建造者模式
     */
    public static class Build {

        /**
         * 缩放偏移量
         */
        private float mScaleOffset = 40;
        /**
         * 位移偏移量
         */
        private float mTranslationOffset = 40;
        /**
         * 透明度偏移量
         */
        private float mAlphaOffset = 0.5f;

        /**
         * 最上层卡片滑动时的最大旋转角度
         */
        private float mRotationOffset = 10;

        /**
         * 视图类型
         */
        private int mViewType = PageTransformerConfig.RIGHT;

        /**
         * 默认显示的最大页数
         */
        private int mMaxShowPage = 3;
        /**
         * ViewPager
         */
        private ViewPager mViewPager;

        public float getScaleOffset() {
            return mScaleOffset;
        }

        public Build setScaleOffset(float mScaleOffset) {
            this.mScaleOffset = mScaleOffset;
            return this;
        }

        public float getTranslationOffset() {
            return mTranslationOffset;
        }

        public Build setTranslationOffset(float mTranslationOffset) {
            this.mTranslationOffset = mTranslationOffset;
            return this;
        }

        public float getAlphaOffset() {
            return mAlphaOffset;
        }

        public Build setAlphaOffset(float alphaOffset) {
            this.mAlphaOffset = alphaOffset;
            return this;
        }

        public float getRotationOffset() {
            return mRotationOffset;
        }

        public Build setRotationOffset(float rotationOffset) {
            this.mRotationOffset = rotationOffset;
            return this;
        }

        public int getMaxShowPage() {
            return mMaxShowPage;
        }

        public Build setMaxShowPage(int maxShowPage) {
            this.mMaxShowPage = maxShowPage;
            return this;
        }

        public ViewPager getViewPager() {
            return mViewPager;
        }

        public int getViewType() {
            return mViewType;
        }

        /**
         * 设置View的类型
         *
         * @param mViewType
         * @return
         */
        public Build setViewType(@PageTransformerConfig.ViewType int mViewType) {
            this.mViewType = mViewType;
            return this;
        }

        /**
         * 完成创建
         *
         * @return
         */
        public ViewPager.PageTransformer create(ViewPager viewPager) {
            this.mViewPager = viewPager;
            if(null != this.mViewPager){
                this.mViewPager.setOffscreenPageLimit(getMaxShowPage()); //缓存页数和最大显示卡片数一致
            }
            return new StackCardPageTransformer(this);
        }
    }
}