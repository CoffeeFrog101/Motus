package com.example.motus;

import android.app.Service;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService

{

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        getFirebaseMessage(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

    }

    public void getFirebaseMessage(String title, String msgs)

    {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"myFirebaseChannel")
                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
                .setContentTitle(title)
                .setContentText(msgs)
                .setAutoCancel(true);


        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(101,builder.build());

    }
}
