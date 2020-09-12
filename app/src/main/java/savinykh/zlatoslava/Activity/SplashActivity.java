package savinykh.zlatoslava.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import savinykh.zlatoslava.R;
import savinykh.zlatoslava.Utility.ActivityUtilities;

public class SplashActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Animation mAnimation;
    private ProgressBar mProgressBar;
    private ConstraintLayout mConstraintLayout;

    public static final int SPLASH_DURATION = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mProgressBar = findViewById(R.id.progressBarSplashScreen);
        mConstraintLayout = findViewById(R.id.constLayoutSplashScreen);
        mImageView = findViewById(R.id.ivAppIconSplashScreen);
        mAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
    }

    private void initFunctionality() {
        mConstraintLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
                mImageView.startAnimation(mAnimation);
                mAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ActivityUtilities.getInstance().invokeNewActivity(SplashActivity.this, MainActivity.class, true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }, SPLASH_DURATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFunctionality();
    }
}
