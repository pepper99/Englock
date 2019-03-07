package th.ac.bodin.ppnt.englock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

public class SettingsPop extends AppCompatActivity {

    SharedPreferences shared;
    SharedPreferences.Editor editor;
    SwitchCompat lockscreenToggle;
    Button timerbutton, revokebutton, changelangbutton, haxbtn;
    AlertDialog alertDialog1;
    String lang;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_pop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.settingarray1));

        lockscreenToggle = (SwitchCompat) findViewById(R.id.togglelock);
        lockscreenToggle.setOnCheckedChangeListener (null);
        shared = getSharedPreferences("settings", Context.MODE_PRIVATE);
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
        haxbtn = (Button) findViewById(R.id.hax);

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
        haxbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hax();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void revokeAccount() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
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
                            clearAccount();
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

    private void clearAccount() {
        initInstance();
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                SharedPreferences sharedPreferences = getSharedPreferences("userStats", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                sharedPreferences = getSharedPreferences("shopStats", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void lockscreenTimer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String secs = getResources().getString(R.string.seconds);
        String sec = getResources().getString(R.string.second);
        String ques = getResources().getString(R.string.quesdelay);
        builder.setTitle(ques);

        CharSequence[] timechoices = {"0 " + sec, "3 " + secs, "5 " + secs, "10 " + secs, "20 " + secs};

        shared = getSharedPreferences("settings", Context.MODE_PRIVATE);
        int delaycase = shared.getInt("delaycase",0);
        editor = shared.edit();

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

        SharedPreferences shared = getSharedPreferences("settings", Context.MODE_PRIVATE);
        int selectlang = shared.getInt("lang",0);
        editor = shared.edit();

        CharSequence[] langchoices = {getResources().getString(R.string.en),getResources().getString(R.string.th)};
        builder.setSingleChoiceItems(langchoices, selectlang, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                editor.putInt("lang", item);
                editor.apply();
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

                Intent refresh = new Intent(getApplication(), MainActivity.class);
                refresh.putExtra("refresh", true);
                startActivity(refresh);
                finish();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    private void Hax() {
        SharedPreferences shared = getSharedPreferences("userStats", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        long h = shared.getLong("points", 0) + 100;
        editor.putLong("points", h );
        Toast.makeText(this, "+100 = " + String.valueOf(h), Toast.LENGTH_SHORT).show();
        editor.commit();
    }

    private void initInstance() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
}
