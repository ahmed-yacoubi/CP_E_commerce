package ahmed.yaqoubi.controlPanel.serves;


import android.annotation.SuppressLint;
import android.app.ActivityManager;

import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;


import androidx.annotation.NonNull;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import ahmed.yaqoubi.controlPanel.Activites.MainActivity;
import ahmed.yaqoubi.controlPanel.R;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyMessagingService extends FirebaseMessagingService {// this service to receive Notifications


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Helperr helperr = new Helperr();
        if (helperr.isAppRunning(MyMessagingService.this, "com.example.quranprojectdemo")) {
            // App is running
        } else {
            // App is not running

            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody().toLowerCase());
            // write code to do when you receive Notifications
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Intent serviceIntent = new Intent(getBaseContext(), GetDataService.class);
//                    startService(serviceIntent);
//                }
//            }).start();
        }


    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);
    }

    public void showNotification(String title, String body) {
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();// to get token


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotifications")
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentText(body)
                .setContentIntent(pIntent);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());
    }

}


class Helperr {

    public boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null) {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}