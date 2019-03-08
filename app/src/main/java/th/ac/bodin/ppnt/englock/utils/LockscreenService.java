package th.ac.bodin.ppnt.englock.utils;

import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import th.ac.bodin.ppnt.englock.MainActivity;
import th.ac.bodin.ppnt.englock.R;

public class LockscreenService extends Service {

    BroadcastReceiver receiver;
    IntentFilter filter;
    NotificationManager notificationManager;
    SharedPreferences shared;
    LockscreenService m_service;

    public LockscreenService(Context applicationContext) {
        super();
    }

    public LockscreenService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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

        Intent intent = new Intent(this, LockscreenService.class);
        bindService(intent, m_serviceConnection, BIND_AUTO_CREATE);
        /*KeyguardManager.KeyguardLock key;
        KeyguardManager km = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);

        //This is deprecated, but it is a simple way to disable the lockscreen in code
        key = km.newKeyguardLock("IN");

        key.disableKeyguard();*/

        Log.d("kuy","running");
        //Start listening for the Screen On, Screen Off, and Boot completed actions
        filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        //filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);

        //Set up a receiver to listen for the Intents in this Service
        receiver = new LockscreenIntentReceiver();
        registerReceiver(receiver, filter);

        //showNotification();

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("kuy", "service destroyed");
        unregisterReceiver(receiver);
        //stopSelf();
        /*notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();*/
    }

    public class ServiceBinder extends Binder {
        public LockscreenService getService() {
            return LockscreenService.this;
        }
    }

    private ServiceConnection m_serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            m_service = ((LockscreenService.ServiceBinder)service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            m_service = null;
        }
    };

    /*public void showNotification() {

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
        shared = getSharedPreferences("settings", Context.MODE_PRIVATE);
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
    }*/
}