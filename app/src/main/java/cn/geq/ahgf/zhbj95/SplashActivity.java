package cn.geq.ahgf.zhbj95;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import java.lang.reflect.AnnotatedElement;

import cn.geq.ahgf.zhbj95.utils.Constants;
import cn.geq.ahgf.zhbj95.utils.SharedPreferencesUtils;

public class SplashActivity extends Activity {

    private RelativeLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }
    //初始化控件
    private void initView() {
        mRoot = findViewById(R.id.splash_rel_root);
        //设置动画
        setAnimation();
    }

    private void setAnimation() {
        //旋转
        RotateAnimation rotateAnimation = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1500);
        rotateAnimation.setFillAfter(true);
        //缩放
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1500);
        rotateAnimation.setFillAfter(true);
        //透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);//透明 -  不透明
        alphaAnimation.setDuration(1500);
        alphaAnimation.setFillAfter(true);
        //组合动画
        AnimationSet animationSet = new AnimationSet(false);//是否使用补间动画
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        mRoot.setAnimation(animationSet);
        animationSet.setAnimationListener(new MyAnimationListener());

    }

    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        //动画结束时跳转 - 欢迎页面 -首页
        @Override
        public void onAnimationEnd(Animation animation) {
            boolean b = SharedPreferencesUtils.getBoolean(getApplicationContext(), Constants.ISFIRSTENTER, false);
            if (!b){
                Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                startActivity(intent);
            }
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
