package th.ac.bodin.ppnt.englock;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import th.ac.bodin.ppnt.englock.extra_assets.AboutDialog;
import th.ac.bodin.ppnt.englock.extra_assets.BottomNavigationViewHelper;
import th.ac.bodin.ppnt.englock.fragments.Dashboard_Fragment;
import th.ac.bodin.ppnt.englock.fragments.Home_Fragment;
import th.ac.bodin.ppnt.englock.fragments.More_Fragment;
import th.ac.bodin.ppnt.englock.fragments.Shop_Fragment;
import th.ac.bodin.ppnt.englock.utils.LockscreenService;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    SharedPreferences.OnSharedPreferenceChangeListener listener;
    SharedPreferences prefs;
    private LockscreenService lockscreenService;
    Intent mServiceIntent;
    LinearLayoutCompat pointsLayout;
    TextView pointsText;
    private final int REQUEST_READ_PHONE_STATE = 1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.mainfadein, R.anim.mainfadeout,
                                    R.anim.mainfadein, R.anim.mainfadeout)
                            .replace(R.id.layout_fragment_container,
                                    Home_Fragment.newInstance(),
                                    "HOME")
                            .commit();
                    if(pointsLayout.getVisibility() == View.VISIBLE) pointsLayout.setVisibility(View.INVISIBLE);
                    return true;

                case R.id.item_dashboard:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.mainfadein, R.anim.mainfadeout,
                                    R.anim.mainfadein, R.anim.mainfadeout)
                            .replace(R.id.layout_fragment_container,
                                    Dashboard_Fragment.newInstance(),
                                    "DASH")
                            .commit();
                    if(pointsLayout.getVisibility() == View.VISIBLE) pointsLayout.setVisibility(View.INVISIBLE);
                    return true;

                case R.id.item_shop:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.mainfadein, R.anim.mainfadeout,
                                    R.anim.mainfadein, R.anim.mainfadeout)
                            .replace(R.id.layout_fragment_container,
                                    Shop_Fragment.newInstance(),
                                    "SHOP")
                            .commit();
                    if(pointsLayout.getVisibility() == View.INVISIBLE) pointsLayout.setVisibility(View.VISIBLE);
                    return true;

                case R.id.item_more:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.mainfadein, R.anim.mainfadeout,
                                    R.anim.mainfadein, R.anim.mainfadeout)
                            .replace(R.id.layout_fragment_container,
                                    More_Fragment.newInstance(),
                                    "MORE")
                            .commit();
                    if(pointsLayout.getVisibility() == View.VISIBLE) pointsLayout.setVisibility(View.INVISIBLE);
                    return true;
            }
            return true;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PHONE_STATE);
        }

        Bundle bundle = getIntent().getExtras();

        SharedPreferences shared = getSharedPreferences("settings", Context.MODE_PRIVATE);
        int selectlang = shared.getInt("lang",0);
        String lang = "en";
        switch(selectlang)
        {
            case 0:
                lang = "en";
                break;
            case 1:
                lang = "th";
                break;
        }

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        setContentView(R.layout.activity_main);

        pointsLayout = findViewById(R.id.pointsLayout);
        pointsText = findViewById(R.id.pointsText);

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.O)
            TextViewCompat.setAutoSizeTextTypeWithDefaults(pointsText, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

        lockscreenService = new LockscreenService(this);
        mServiceIntent = new Intent(this, lockscreenService.getClass());
        if (!isMyServiceRunning(lockscreenService.getClass())){
            startService(mServiceIntent);
        }

        android.support.v7.widget.Toolbar myToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.ic_englock_iconlong);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment_container,
                        Home_Fragment.newInstance(),
                        "HOME")
                .commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        shared = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean seenIntro = shared.getBoolean("seenIntro", false);
        boolean gotFreeItem = shared.getBoolean("gotFreeItem", false);

        if (!gotFreeItem) FreeShop();

        if (!seenIntro){
            SharedPreferences.Editor editor = shared.edit();
            editor.putBoolean("seenIntro", true);
            editor.apply();
            Intro();
        }

        if (bundle != null) {
            boolean refresh = bundle.getBoolean("refresh");
            if(refresh) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_fragment_container,
                                Home_Fragment.newInstance(),
                                "HOME")
                        .commit();
            }
        }

        prefs = getSharedPreferences("userStats", Context.MODE_PRIVATE);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if(key == "points") {
                    Home_Fragment myFragment = (Home_Fragment) getSupportFragmentManager().findFragmentByTag("HOME");
                    if (myFragment != null && myFragment.isVisible()) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.layout_fragment_container,
                                        Home_Fragment.newInstance(),
                                        "HOME")
                                .commit();
                    }
                }
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(listener);


    }

    public void Intro() {
        Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void FreeShop() {
        Intent i = new Intent(MainActivity.this, FreeShopMenu.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onResume() {
        prefs.registerOnSharedPreferenceChangeListener(listener);
        super.onResume();
    }

    public void onBackPressed() {
        stopService(mServiceIntent);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        try {stopService(mServiceIntent);} catch(Exception ex) {}
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == Home_Fragment.RC_SIGN_IN) {
            Home_Fragment fragment = (Home_Fragment) getSupportFragmentManager()
                    .findFragmentById(R.id.layout_fragment_container);
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_settings:
                openSettings();
                return true;
            case R.id.toolbar_about:
                aus();
                return true;
        }
        return true;
    }

    public void aus() {
        String desc = "Developed by\n" +
                "\t• Poptum Charoennaew (Programmer)\n" +
                "\t• Nattapat Panchavinin (Assistant)\n" +
                "\t• Lattapol Dansakul (Advisor)\n" +
                "\tM.6/14 Bodindecha (Sing Singhaseni)\n" +
                "\n" +
                "พัฒนาโดย\n" +
                "\t• นายภพธรรม เจริญแนว (โปรแกรมเมอร์)\n" +
                "\t• นายณัฐภัทร ปัญจวีณิน (ผู้ช่วย)\n" +
                "\t• คุณครูลัทธพล ด่านสกุล (ครูที่ปรึกษา)\n" +
                "\tม.6/14 โรงเรียนบดินทรเดชา (สิงห์ สิงหเสนี)";
        AboutDialog alert = new AboutDialog.Builder()
                .setMessage(desc)
                .setButton(R.string.ok)
                .setLayout(R.layout.about_box)
                .build();
        alert.show(this.getFragmentManager(), "LUL");
    }

    public void openSettings() {
        Intent i = new Intent(this, SettingsPop.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("kuy", "it is running");
                return true;
            }
        }
        Log.i ("kuy", "not running");
        return false;
    }

    public void updatePoints(long points) {
        if(pointsText != null) {
            pointsText.setText(String.valueOf(points));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;

            default:
                break;
        }
    }
}
