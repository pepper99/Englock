package th.ac.bodin.ppnt.englock;


import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vansuita.gaussianblur.GaussianBlur;

import java.util.Random;

import th.ac.bodin.ppnt.englock.extra_assets.ViewDialog;
import th.ac.bodin.ppnt.englock.sql.DatabaseOpenHelper;
import th.ac.bodin.ppnt.englock.stats.FirebaseHelper;

/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;*/

public class LockscreenActivity extends Activity {

    public static final String TAG = "OhHaiMARK";

    //private AdView mAdView;

    //public HomeKeyLocker homeKeyLocker = new HomeKeyLocker();

    Cursor mCursor;

    Random rand = new Random();
    public boolean crct = false;
    Button[] clickAns = new Button[4];
    TextView quesTXT;
    WallpaperManager wallpaperManager;
    Drawable wallpaperDrawable;
    ImageView wallpp;
    Vibrator v;
    FirebaseHelper firebaseHelper;

    int remainingTime;
    int missCount;

    private String thword, enword, pop;

    IconRoundCornerProgressBar pbTimer;

    CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_lockscreen);

        clickAns[0] = (Button)findViewById(R.id.choiceA);
        clickAns[1] = (Button)findViewById(R.id.choiceB);
        clickAns[2] = (Button)findViewById(R.id.choiceC);
        clickAns[3] = (Button)findViewById(R.id.choiceD);

        pbTimer = (IconRoundCornerProgressBar) findViewById(R.id.progressR);

        int CountDownAmnt = 10;
        pbTimer.setMax(CountDownAmnt);
        //pbTimer.setProgress(CountDownAmnt);

        wallpaperManager = WallpaperManager.getInstance(this);
        wallpaperDrawable = wallpaperManager.getDrawable();

        /*mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        wallpp = (ImageView)findViewById(R.id.wallpp);
        wallpp.setImageDrawable(wallpaperDrawable);

        Bitmap blurredBitmap = GaussianBlur.with(this).render(wallpaperDrawable);
        wallpp.setImageBitmap(blurredBitmap);

        quesTXT = (TextView)findViewById(R.id.question_txtview);

        missCount = 0;

        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        //Set up our Lockscreen
        makeFullScreen();

        //homeKeyLocker.lock(this); //lock keys

        String pop;
        final boolean isQuestionEng = rand.nextBoolean();
        int i, randomTemp;
        final int ans;
        ans = rand.nextInt(4);//random correct choice

        crct = false;
        String ques;

        //read the user's selected vocab set
        SharedPreferences settings = getSharedPreferences("shopStats", Context.MODE_PRIVATE);
        int VocabSelection = settings.getInt("selected", 0);

        Log.d("kuy", "selected = " + String.valueOf(VocabSelection));

        DatabaseOpenHelper db = new DatabaseOpenHelper(this);
        db.setTable(VocabSelection);
        int datanum = db.getNumberOfData(VocabSelection);
        mCursor = db.getVocab();

        int[] getWords = new int[4];
        for(i = 0; i < 4; i++) getWords[i] = -1;

                String btntxt;
        randomTemp = -1;
        for(i = 0; i < 4; i++) {
            while(randomTemp == getWords[0] || randomTemp == getWords[1]
                    || randomTemp == getWords[2] || randomTemp == getWords[3]) randomTemp = rand.nextInt(datanum);
            getWords[i] = randomTemp;
            mCursor.moveToPosition(getWords[i]);
            if(isQuestionEng) {
                if(ans == i){
                    pop = mCursor.getString(mCursor.getColumnIndex("pop"));
                    ques = mCursor.getString(mCursor.getColumnIndex("en"));
                    quesTXT.setText( "\"" + ques + " (" + pop + ")\"\n" + getResources().getString(R.string.question_vocab) );
                    btntxt = mCursor.getString(mCursor.getColumnIndex("th"));
                    this.thword = btntxt;
                    this.pop = pop;
                    this.enword = ques;
                }
                else btntxt = mCursor.getString(mCursor.getColumnIndex("th"));
                clickAns[i].setText(btntxt);
            }
            else {
                if(ans == i){
                    pop = mCursor.getString(mCursor.getColumnIndex("pop"));
                    ques = mCursor.getString(mCursor.getColumnIndex("th"));
                    String en = mCursor.getString(mCursor.getColumnIndex("en"));
                    quesTXT.setText( "\"" + ques + "\"\n" + getResources().getString(R.string.question_vocab) );
                    btntxt = en + " (" + pop + ")";
                    this.thword = ques;
                    this.pop = pop;
                    this.enword = en;
                }
                else btntxt = mCursor.getString(mCursor.getColumnIndex("en")) + " ("
                        + mCursor.getString(mCursor.getColumnIndex("pop")) + ")";
                clickAns[i].setText(btntxt);
            }
        }

        mCursor.close();
        db.close();

        //countdown
        this.firebaseHelper = new FirebaseHelper(this);
        cdt = new CountDownTimer((CountDownAmnt) * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if(!crct){
                    remainingTime = (int) millisUntilFinished / 1000;
                    pbTimer.setProgress(remainingTime+1);
                }
            }

            public void onFinish() {
                crct = true;
                for(int i = 0; i < 4; i++) clickAns[i].setEnabled(false);
                pbTimer.setProgress(0);

                TimeoutAlert();
            }
        }.start();

        clickAns[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAns(view, 0, ans);
            }
        });
        clickAns[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAns(view, 1, ans);
            }
        });
        clickAns[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAns(view, 2, ans);
            }
        });
        clickAns[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAns(view, 3, ans);
            }
        });
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

    @Override
    public void onBackPressed() {
        return; //Do nothing!
    }

    void clickAns(View view, int choices, int ans) {
        if(!crct){
            if(ans == choices) {
                long newPts = scoreCal(remainingTime, missCount);
                crct = true;

                if (newPts > 0) {
                    SharedPreferences shared = getSharedPreferences("userStats", Context.MODE_PRIVATE);
                    long currentPts = shared.getLong("points", 0);

                    SharedPreferences.Editor editor = shared.edit();
                    editor.putLong("points", currentPts + newPts );
                    if(missCount == 0) {
                        long correct = shared.getLong("quesCorrect", 0);
                        editor.putLong("quesCorrect", correct+1);
                        firebaseHelper.updateToCloud("quesCorrect", 0, correct+1);
                    }
                    editor.apply();
                    firebaseHelper.updateToCloud("points", 0, currentPts + newPts);
                }

                for(int i = 0; i < 4; i++){
                    if(i == choices) {
                        clickAns[choices].setClickable(false);
                        clickAns[choices].setBackgroundColor(0xCCFFD3);
                    }
                    else clickAns[i].setEnabled(false);
                }

                cdt.cancel();
                saveDash(enword,thword,pop);

                String AlertTxt = getResources().getString(R.string.niceone) + "\n" + enword + " : " + thword;
                ViewDialog alert = new ViewDialog.Builder()
                        .setMessage(AlertTxt)
                        .setButton(R.string.ok)
                        .setLayout(R.layout.dialog_box)
                        .build();
                alert.setActivity(this);
                alert.show(this.getFragmentManager(), "LUL");
            }
            else {
                missCount++;

                v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
                }else{
                    //deprecated in API 26
                    v.vibrate(500);
                }
            }
        }
    }

    private long scoreCal(int rTime, int missCtn) {
        if(missCtn > 2) return 0;
        else {
            long Score = 2*rTime / (missCtn + 1);
            Log.d(TAG, "scoreCal: " + rTime + " " + missCtn + " " + Score);
            return Score;
        }
    }

    private void saveDash(String EN, String TH, String POP) {
        SharedPreferences shared = getSharedPreferences("userStats", Context.MODE_PRIVATE);
        String[] temp = new String[3];
        long found = shared.getLong("quesFound", 0);
        temp[0] = EN + " (" + POP + ") : " + TH;
        temp[1] = shared.getString("recent0", "-");
        temp[2] = shared.getString("recent1", "-");

        SharedPreferences.Editor editor = shared.edit();

        editor.putLong("quesFound", found+1);
        editor.putString("recent0", temp[0]);
        editor.putString("recent1", temp[1]);
        editor.putString("recent2", temp[2]);
        editor.apply();

        firebaseHelper.updateToCloud("quesFound", 0, found+1);
        firebaseHelper.updateToCloud("recent0", 0, temp[0]);
        firebaseHelper.updateToCloud("recent1", 0, temp[1]);
        firebaseHelper.updateToCloud("recent2", 0, temp[2]);
    }

    private void TimeoutAlert() {
        saveDash(enword,thword,pop);
        String AlertTxt = enword + " : " + thword + "\n" + getResources().getString(R.string.toobad);
        ViewDialog alert = new ViewDialog.Builder()
                .setMessage(AlertTxt)
                .setButton(R.string.ok)
                .setLayout(R.layout.dialog_box_timeout)
                .build();
        alert.setActivity(this);
        alert.show(this.getFragmentManager(), "LUL");
    }
}