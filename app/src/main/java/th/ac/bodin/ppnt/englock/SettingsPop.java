package th.ac.bodin.ppnt.englock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsPop extends AppCompatActivity {

    SharedPreferences shared;
    Switch lockscreenToggle;
    Button timerbutton, revokebutton;
    AlertDialog alertDialog1;
    CharSequence[] timechoices = {"0 Second ","3 Seconds","5 Seconds","10 Seconds","20 Seconds"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_pop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Settings");

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

            builder.setTitle("Confirmation");
            builder.setMessage("Do you want to delete your Google account?\n(Your records will not be deleted.)");

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
            builder.setPositiveButton("YES", dialogClickListener);
            builder.setNegativeButton("NO", dialogClickListener);
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

        builder.setTitle("Select Your Choice");

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
}
