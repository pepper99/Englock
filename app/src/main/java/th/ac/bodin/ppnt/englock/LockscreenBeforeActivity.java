package th.ac.bodin.ppnt.englock;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vansuita.gaussianblur.GaussianBlur;

public class LockscreenBeforeActivity extends Activity{
    private TextView cntdownTxt;
    private TextView unlockTXT;
    private ImageView wallpp;
    private ConstraintLayout exitLayout;
    private WallpaperManager wallpaperManager;
    private Drawable wallpaperDrawable;
    private ProgressBar pBar;
    private ImageView keyImage;
    private Boolean isTouched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();
        setContentView(R.layout.activity_before_lockscreen);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.beforelockLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTouched);
                else {
                    isTouched = true;
                    startClick(v);
                }
            }
        });

        isTouched = false;
        wallpaperManager = WallpaperManager.getInstance(this);
        wallpaperDrawable = wallpaperManager.getDrawable();

        wallpp = (ImageView)findViewById(R.id.wallppb);
        wallpp.setImageDrawable(wallpaperDrawable);

        Bitmap blurredBitmap = GaussianBlur.with(this).render(wallpaperDrawable);
        wallpp.setImageBitmap(blurredBitmap);

        pBar = (ProgressBar)findViewById(R.id.pBarBL);
        cntdownTxt = (TextView)findViewById(R.id.cntdownTxt);

        keyImage = (ImageView)findViewById(R.id.keyImage);
        unlockTXT = (TextView)findViewById(R.id.unlockLabel);

        exitLayout = (ConstraintLayout) findViewById(R.id.exitLayout);
    }

    @Override
    public void onBackPressed() {
        return; //Do nothing!
    }

    public void exitQues(View view) {
        overridePendingTransition(R.anim.mainfadein, R.anim.mainfadeout);
        finish();
    }

    public void startClick(View view) {

        keyImage.setVisibility(View.INVISIBLE);
        unlockTXT.setVisibility(View.INVISIBLE);
        exitLayout.setVisibility(View.GONE);

        SharedPreferences shared = getSharedPreferences("settings", Context.MODE_PRIVATE);
        int delay = shared.getInt("delay",0);
        Log.d("delay",String.valueOf(delay));

        if(delay > 0) {
            pBar.setVisibility(View.VISIBLE);
            cntdownTxt.setVisibility(View.VISIBLE);
            CountDownTimer cdt = new CountDownTimer(delay * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    int temp = (int) millisUntilFinished / 1000;
                    String strTime = String.format("%.0f", (double)millisUntilFinished / 1000);
                    cntdownTxt.setText(String.valueOf(strTime));
                    if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) pBar.setProgress(temp, true);
                    else pBar.setProgress(temp);
                }

                public void onFinish() {
                    cntdownTxt.setText("0");

                    if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) pBar.setProgress(0, true);
                    else pBar.setProgress(0);

                    Intent i = new Intent(LockscreenBeforeActivity.this, LockscreenActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                    overridePendingTransition(R.anim.mainfadein, R.anim.mainfadeout);

                    finish();
                }
            }.start();
        }

        else {
            Intent i = new Intent(LockscreenBeforeActivity.this, LockscreenActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

            overridePendingTransition(R.layout.mainfadein, R.layout.mainfadeout);

            finish();
        }
    }

    /**
     * A simple method that sets the screen to fullscreen.  It removes the Notifications bar,
     *   the Actionbar and the virtual keys (if they are on the phone)
     */
    public void makeFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        if(Build.VERSION.SDK_INT < 19) { //View.SYSTEM_UI_FLAG_IMMERSIVE is only on API 19+
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }
}
