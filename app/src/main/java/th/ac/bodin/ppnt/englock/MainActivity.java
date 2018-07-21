package th.ac.bodin.ppnt.englock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

import th.ac.bodin.ppnt.englock.extra_assets.AboutDialog;
import th.ac.bodin.ppnt.englock.extra_assets.BottomNavigationViewHelper;
import th.ac.bodin.ppnt.englock.fragments.Dashboard_Fragment;
import th.ac.bodin.ppnt.englock.fragments.Home_Fragment;
import th.ac.bodin.ppnt.englock.fragments.More_Fragment;
import th.ac.bodin.ppnt.englock.fragments.Shop_Fragment;
import th.ac.bodin.ppnt.englock.utils.LockscreenService;

public class MainActivity extends AppCompatActivity {

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
                    return true;
            }
            return true;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, LockscreenService.class));

        setContentView(R.layout.activity_main);

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

        SharedPreferences shared = getSharedPreferences("seenIntro", Context.MODE_PRIVATE);
        boolean seenIntro = shared.getBoolean("seenIntro", false);
        if (!seenIntro){
            SharedPreferences.Editor editor = shared.edit();
            editor.putBoolean("seenIntro", true);
            editor.commit();
            Log.d("intro", "seen");
            Intro();
        }

        shared = getSharedPreferences("Englock Settings", Context.MODE_PRIVATE);
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
    }

    public void Intro() {
        Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void Hax(View view) {
        SharedPreferences shared = getSharedPreferences("Englock Points", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        long h = shared.getLong("pointsCount", 0) + 100;
        editor.putLong("pointsCount", h );
        Toast.makeText(this, "+100 = " + String.valueOf(h), Toast.LENGTH_SHORT).show();
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
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
}
