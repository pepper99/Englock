package th.ac.bodin.ppnt.englock.utils;

import android.app.KeyguardManager;
import android.app.Notification;
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
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import th.ac.bodin.ppnt.englock.MainActivity;
import th.ac.bodin.ppnt.englock.R;

public class LockscreenService extends Service {

    BroadcastReceiver receiver;
    IntentFilter filter;
    LockscreenService m_service;
    int NOTIFICATION_ID = 1150;

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
        startForeground();

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
        //filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        receiver = new LockscreenIntentReceiver();
        registerReceiver(receiver, filter);

        //showNotification();

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d("kuy", "service destroyed");
        unregisterReceiver(receiver);

        super.onDestroy();
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

    private void startForeground() {
        String channelId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChannel("elChannel", "Englock Background Service");
        } else {
            // If earlier version channel ID is not used
            // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
            channelId = "elChannel";
        }
        // create the notification
        NotificationCompat.Builder m_notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(getText(R.string.app_name))
                .setContentText("Englock is running in the background.")
                .setSmallIcon(R.drawable.englock_icon_pink);

        // create the pending intent and add to the notification
        Intent intent = new Intent(this, LockscreenService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        m_notificationBuilder.setContentIntent(pendingIntent);

        startForeground(NOTIFICATION_ID, m_notificationBuilder.build());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName){
        NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManagerCompat.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);
        return channelId;
    }
}