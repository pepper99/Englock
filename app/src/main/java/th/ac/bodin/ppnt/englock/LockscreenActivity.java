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
import com.vansuita.gaussianblur.GaussianBlur;

import java.util.Random;

import th.ac.bodin.ppnt.englock.extra_assets.ViewDialog;
import th.ac.bodin.ppnt.englock.sql.DatabaseAccess;
import th.ac.bodin.ppnt.englock.sql.DatabaseOpenHelper;

/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;*/

public class LockscreenActivity extends Activity {

    public static final String TAG = "OhHaiMARK";

    //private AdView mAdView;

    //public HomeKeyLocker homeKeyLocker = new HomeKeyLocker();
    //Context context;

    Cursor mCursor;

    Random rand = new Random();
    public boolean crct = false;
    Button[] clickAns = new Button[4];
    TextView txvw;
    WallpaperManager wallpaperManager;
    Drawable wallpaperDrawable;
    ImageView wallpp;
    Vibrator v;

    int remainingTime;
    int missCount;

    private String quesword, answord;
    private boolean isQuestionEng;

    IconRoundCornerProgressBar pbTimer;

    CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        missCount = 0;

        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        //Set up our Lockscreen
        makeFullScreen();

        //homeKeyLocker.lock(this); //lock keys

        final boolean isQuestionEng = rand.nextBoolean();
        this.isQuestionEng = isQuestionEng;

        String pop;
        int i, x, randomTemp;
        final int ans;
        ans = rand.nextInt(4);//random correct choice
        x = ans;
        boolean[] chk = new boolean[4];
        for(i = 0; i < 4; i++) chk[i] = false;
        int[] ButtonNoSet = new int[4];
        ButtonNoSet[0] = ans;
        chk[ans] = true;

        for(i = 1; i < 4; i++){
            while(chk[x]) x = rand.nextInt(4);
            chk[x] = true;
            ButtonNoSet[i] = x;
        }

        crct = false;

        String[] choices = new String[4];
        String ques;

        //read the user's selected vocab set
        SharedPreferences settings = getSharedPreferences("Englock Shop Stats", Context.MODE_PRIVATE);
        int VocabSelection = settings.getInt("selected", 0);

        Log.d("kuy", String.valueOf(VocabSelection));

        DatabaseOpenHelper db = new DatabaseOpenHelper(this);
        db.setTable(VocabSelection);
        int datanum = db.getNumberOfData(VocabSelection);
        mCursor = db.getVocab();

        int[] getWords = new int[4];
        for(i = 0; i < 4; i++) getWords[i] = -1;

        //random positions of words
        randomTemp = -1;
        for(i = 0; i < 4; i++) {
            while(randomTemp == getWords[0] || randomTemp == getWords[1]
                    || randomTemp == getWords[2] || randomTemp == getWords[3]) randomTemp = rand.nextInt(datanum);
            getWords[i] = randomTemp;
        }

        mCursor.moveToPosition(getWords[0]);
        pop = mCursor.getString(mCursor.getColumnIndex("pop"));

        //check whether question is eng or thai and pull vocabs from the database
        if(isQuestionEng) {
            ques = mCursor.getString(mCursor.getColumnIndex("en"));
            choices[0] = mCursor.getString(mCursor.getColumnIndex("th"));
            for(i=1; i<4; i++) {
                mCursor.moveToPosition(getWords[i]);
                choices[i] = mCursor.getString(mCursor.getColumnIndex("th"));
            }
            saveDash(ques, choices[0], pop);
        }
        else {
            ques = mCursor.getString(mCursor.getColumnIndex("th"));
            choices[0] = mCursor.getString(mCursor.getColumnIndex("en"));
            for(i=1; i<4; i++) {
                mCursor.moveToPosition(getWords[i]);
                choices[i] = mCursor.getString(mCursor.getColumnIndex("en"));
            }
            saveDash(choices[0], ques, pop);
        }

        this.quesword = ques;
        this.answord = choices[0];

        mCursor.close();
        db.close();

        setContentView(R.layout.activity_lockscreen);

        int CountDownAmnt = 10;

        pbTimer = (IconRoundCornerProgressBar) findViewById(R.id.progressR);
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

        clickAns[0] = (Button)findViewById(R.id.choiceA);
        clickAns[1] = (Button)findViewById(R.id.choiceB);
        clickAns[2] = (Button)findViewById(R.id.choiceC);
        clickAns[3] = (Button)findViewById(R.id.choiceD);
        //clickLock = (Button)findViewById(R.id.unlockbutton);

        txvw = (TextView)findViewById(R.id.question_txtview);
        txvw.setText( "\"" + ques + "\"\n" + getResources().getString(R.string.question_vocab) );

        //countdown

        cdt = new CountDownTimer((CountDownAmnt) * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if(crct == false){
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

        for(i = 0; i < 4; i++) clickAns[ButtonNoSet[i]].setText(choices[i]);

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
        /*clickLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unlockScreen(view);
            }
        });*/
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
        if(crct == false){
            if(ans == choices) {
                crct = true;

                SharedPreferences shared = getSharedPreferences("Englock Points", Context.MODE_PRIVATE);

                long score = scoreCal(remainingTime, missCount);
                long temp = shared.getLong("pointsCount", -1);
                if(temp != -1 && score >= 0){
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putLong("pointsCount", temp + score );
                    editor.commit();
                }

                if (missCount < 2) {
                    int t;
                    Log.d(TAG, "miss " + missCount);
                    shared = getSharedPreferences("Englock Database", Context.MODE_PRIVATE);
                    t = shared.getInt("correct" + String.valueOf(missCount), 0);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putInt("correct0", t + 1);

                    editor.commit();

                }

                for(int i = 0; i < 4; i++) clickAns[i].setEnabled(false);

                clickAns[choices].setEnabled(true);
                clickAns[choices].setClickable(false);
                clickAns[choices].setBackgroundColor(0xCCFFD3);

                cdt.cancel();

                String AlertTxt;
                if(isQuestionEng) AlertTxt = getResources().getString(R.string.niceone) + "\n" + quesword + " : " + answord;
                else AlertTxt = getResources().getString(R.string.niceone) + "\n" + answord + " : " + quesword;
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
    /*public void unlockScreen(View view) {
        //Instead of using finish(), this totally destroys the process
        if (crct == true){
            //homeKeyLocker.unlock();
            //android.os.Process.killProcess(android.os.Process.myPid());
            overridePendingTransition(R.layout.mainfadein,
                    R.layout.mainfadeout);
            finish();
        }
        else return;
    }*/

    private long scoreCal(int rTime, int missCtn) {
        long Score = 0;
        if(missCtn >= 3) return 0;
        else {
            Score = rTime / (missCtn + 1);
            Log.d(TAG, "scoreCal: " + rTime + " " + missCtn + " " + Score);
            return Score;
        }
    }

    private void saveDash(String EN, String TH, String POP) {
        SharedPreferences shared = getSharedPreferences("Englock Database", Context.MODE_PRIVATE);
        String[] temp = new String[3];
        int count = shared.getInt("count", 0);
        temp[0] = EN + " (" + POP + ") : " + TH;
        temp[1] = shared.getString("Recent0", "null");
        temp[2] = shared.getString("Recent1", "null");

        SharedPreferences.Editor editor = shared.edit();

        editor.putInt("count", count+1);
        editor.putString("Recent0", temp[0]);
        editor.putString("Recent1", temp[1]);
        editor.putString("Recent2", temp[2]);
        editor.commit();
    }

    private void TimeoutAlert() {
        String AlertTxt;
        if(isQuestionEng) AlertTxt = quesword + " : " + answord + "\n";
        else AlertTxt = answord + " : " + quesword + "\n" + getResources().getString(R.string.toobad);
        ViewDialog alert = new ViewDialog.Builder()
                .setMessage(AlertTxt)
                .setButton(R.string.ok)
                .setLayout(R.layout.dialog_box_timeout)
                .build();
        alert.setActivity(this);
        alert.show(this.getFragmentManager(), "LUL");
    }
}