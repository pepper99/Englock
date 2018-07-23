package th.ac.bodin.ppnt.englock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;

public class SettingsPop extends AppCompatActivity {

    SharedPreferences shared;
    Switch lockscreenToggle;
    Button timerbutton, revokebutton, changelangbutton;
    AlertDialog alertDialog1;
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_pop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.settingarray1));

        lockscreenToggle = (Switch) findViewById(R.id.togglelock);
        lockscreenToggle.setOnCheckedChangeListener (null);
        shared = getSharedPreferences("Englock Settings", Context.MODE_PRIVATE);
        Boolean isOn = shared.getBoolean("isOn",true);
        lockscreenToggle.setChecked(isOn);

        lockscreenToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = shared.edit();
                editor.putBoolean("isOn", isChecked);
                editor.commit();
            }
        });

        timerbutton = (Button) findViewById(R.id.quiztimersetter);
        revokebutton = (Button) findViewById(R.id.revokeacnt);
        changelangbutton = (Button) findViewById(R.id.changeLang);

        timerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockscreenTimer();
            }
        });
        revokebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revokeAccount();
            }
        });
        changelangbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLang();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void revokeAccount() {
        shared = getSharedPreferences("Englock Account", Context.MODE_PRIVATE);
        boolean isACsaved = shared.getBoolean("IsACsaved", false);
        if (isACsaved) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            String title = getResources().getString(R.string.confirmation);
            String desc = getResources().getString(R.string.signoutgg);
            String yes = getResources().getString(R.string.yes);
            String no = getResources().getString(R.string.no);

            builder.setTitle(title);
            builder.setMessage(desc);

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch(which){
                        case DialogInterface.BUTTON_POSITIVE:
                            clearAccount(shared);
                            Toast.makeText(SettingsPop.this, "Your Google account has been deleted.", Toast.LENGTH_SHORT).show();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                    alertDialog1.dismiss();
                }
            };
            builder.setPositiveButton(yes, dialogClickListener);
            builder.setNegativeButton(no, dialogClickListener);
            alertDialog1 = builder.create();
            alertDialog1.show();
        }
        else Toast.makeText(this, "You are not signed in.", Toast.LENGTH_LONG).show();
    }

    private void clearAccount(SharedPreferences shared) {
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.commit();
    }

    private void lockscreenTimer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String secs = getResources().getString(R.string.seconds);
        String sec = getResources().getString(R.string.second);
        String ques = getResources().getString(R.string.quesdelay);
        builder.setTitle(ques);

        CharSequence[] timechoices = {"0 " + sec, "3 " + secs, "5 " + secs, "10 " + secs, "20 " + secs};

        SharedPreferences shared = getSharedPreferences("Englock Settings", Context.MODE_PRIVATE);
        int delaycase = shared.getInt("delaycase",0);
        final SharedPreferences.Editor editor = shared.edit();

        builder.setSingleChoiceItems(timechoices, delaycase, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                editor.putInt("delaycase", item);
                switch(item)
                {
                    case 0:
                        editor.putInt("delay", 0);
                        break;
                    case 1:
                        editor.putInt("delay", 3);
                        break;
                    case 2:
                        editor.putInt("delay", 5);
                        break;
                    case 3:
                        editor.putInt("delay", 10);
                        break;
                    case 4:
                        editor.putInt("delay", 20);
                        break;
                }
                editor.commit();
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    private void changeLang() {

        lang = "en";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String ques = getResources().getString(R.string.queslang);
        builder.setTitle(ques);

        SharedPreferences shared = getSharedPreferences("Englock Settings", Context.MODE_PRIVATE);
        int selectlang = shared.getInt("lang",0);
        final SharedPreferences.Editor editor = shared.edit();

        CharSequence[] langchoices = {getResources().getString(R.string.en),getResources().getString(R.string.th)};
        builder.setSingleChoiceItems(langchoices, selectlang, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                editor.putInt("lang", item);
                editor.commit();
                switch(item)
                {
                    case 0:
                        lang = "en";
                        break;
                    case 1:
                        lang = "th";
                        break;
                }
                alertDialog1.dismiss();
                Locale myLocale = new Locale(lang);
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                startMain();

            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    private void startMain() {
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
}
