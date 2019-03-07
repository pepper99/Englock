package th.ac.bodin.ppnt.englock.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import th.ac.bodin.ppnt.englock.LockscreenBeforeActivity;

public class LockscreenIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Boolean isOn;
        SharedPreferences shared = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        isOn = shared.getBoolean("isOn", true);

        //If the screen was just turned on or it just booted up, start your Lock Activity
        if( (action.equals(Intent.ACTION_SCREEN_OFF) || action.equals(Intent.ACTION_BOOT_COMPLETED)) && isOn )
            {
                Intent i = new Intent(context, LockscreenBeforeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);
            }
    }
}
