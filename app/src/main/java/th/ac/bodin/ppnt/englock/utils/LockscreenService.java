package th.ac.bodin.ppnt.englock.utils;

import android.app.IntentService;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.CompoundButton;

import th.ac.bodin.ppnt.englock.MainActivity;
import th.ac.bodin.ppnt.englock.R;

public class LockscreenService extends Service {

    BroadcastReceiver receiver;
    IntentFilter filter;
    NotificationManager notificationManager;

    public static final String ACTION_1 = "action_1";

    String toggletxt;
    SharedPreferences shared;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        //handleCommand(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //handleCommand(intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate() {
        KeyguardManager.KeyguardLock key;
        KeyguardManager km = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);

        //This is deprecated, but it is a simple way to disable the lockscreen in code
        key = km.newKeyguardLock("IN");

        key.disableKeyguard();

        //Start listening for the Screen On, Screen Off, and Boot completed actions
        filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);

        //Set up a receiver to listen for the Intents in this Service
        receiver = new LockscreenIntentReceiver();
        registerReceiver(receiver, filter);

        //showNotification();

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        stopSelf();
        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        super.onDestroy();
    }

    public void showNotification() {

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library

            CharSequence channel_name = getString(R.string.channel_name);
            String channel_description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel channel = new NotificationChannel("default",
                    channel_name,
                    importance);
            channel.setDescription(channel_description);
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel);
        }
        shared = getSharedPreferences("Englock Settings", Context.MODE_PRIVATE);
        Boolean isOn = shared.getBoolean("isOn",true);

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this, "default") // this is context
                        .setColor(this.getResources().getColor(R.color.green))
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setOngoing(true)
                        .setContentTitle("Englock")
                        .setContentTitle("Englock is currently running.")
                        .setAutoCancel(false)
                        .setPriority(NotificationCompat.PRIORITY_LOW);

        notification.setDefaults(0);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setContentIntent(pendingIntent);
        notificationManager.notify(0, notification.build()); //show notification
    }
}