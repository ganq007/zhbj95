package cn.geq.ahgf.zhbj95;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.geq.ahgf.zhbj95.utils.Constants;
import cn.geq.ahgf.zhbj95.utils.SharedPreferencesUtils;

public class GuideActivity extends Activity {

    private ViewPager mViewPage;
    private Button mStart;
    private  int mImageId [] = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    private List<ImageView> list;
    private LinearLayout mDots;
    private ImageView mReddot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //初始化控件
        initView();
    }

    private void initView() {
        mViewPage = findViewById(R.id.guide_vp_viewpage);
        mStart = findViewById(R.id.guide_btu_start);
        mDots = findViewById(R.id.guide_ll_dots);
        mReddot = findViewById(R.id.guide_iv_reddot);
        //创建图片和点
        creataImageViewAndDot();
        //设置adpater ,显示image
        mViewPage.setAdapter(new MyAdpater());
        //界面监听
        mViewPage.setOnPageChangeListener(listener);
        //进入按钮监听
        mStart.setOnClickListener(clickListener);
    }


    //创建图片和点
    private void creataImageViewAndDot() {
        list = new ArrayList<ImageView>();
        list.clear();//严谨性操作

        //根据图片张数创建相应的imageview和点
        for (int i =0;i<mImageId.length;i++){
            //创建imageview
            createImage(i);
            //创建点的操作
            createDot();
        }
    }

    //创建imageview
    private void createImage(int i) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(mImageId[i]);
        //将创建的点存进集合
        list.add(imageView);
    }


    //创建点的操作
    private void createDot() {
        ImageView point = new ImageView(this);
        point.setBackgroundResource(R.drawable.shape_gui_got_bg);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin = 10;
        point.setLayoutParams(params);
        mDots.addView(point);
    }

    private class MyAdpater extends PagerAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = list.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
           container.removeView((View) object);//抛出一个异常
           // super.destroyItem(container, position, object);
        }
    }




    //页面切换监听
    private  OnPageChangeListener listener = new OnPageChangeListener() {
        //当界面切换调用的方法
        //positionOffset  界面移动距离百分比
        @Override
        public void onPageScrolled(int position, float positionOffset, int i1) {
            // 20 * positionOffset 移动的距离
           // position * 20 点的距离
            int lefemargin = (int)(2 * positionOffset) + position * 20+2;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mReddot.getLayoutParams();
            params.leftMargin = lefemargin;
            mReddot.setLayoutParams(params);//将更改属性设置给红色点
        }


        //当界面切换完成时调用
        @Override
        public void onPageSelected(int i) {
            //当切到第三个界面时，显示按钮
            if (i==list.size()-1){
                mStart.setVisibility(View.VISIBLE);
            }else{
                mStart.setVisibility(View.GONE);
            }
        }

        //当界面切换状态改变
        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

   //进入按钮监听
    private View.OnClickListener clickListener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           SharedPreferencesUtils.saveBoolean(getApplicationContext(),Constants.ISFIRSTENTER,false);
           Intent intent = new Intent(GuideActivity.this,HomeActivity.class);
           startActivity(intent);
           finish();
       }
   };



}
