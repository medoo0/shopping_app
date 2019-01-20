package com.alaa.microprocess.lrahtk.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.ShowProduct;
import com.google.firebase.messaging.RemoteMessage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by microprocess on 2018-07-08.
 */

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "Android News App";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {




        //imageUri will contain URL of the image to be displayed with Notification


        String imageUri = remoteMessage.getData().get("image");
        String message = remoteMessage.getData().get("message");
        //Calling method to generate notification

        Bitmap Image_Bitmap = getBitmapFromURL(imageUri);

        sendNotification(message,Image_Bitmap);

    }

    //This method is only generating push notification
    private void sendNotification(String messageBody ,Bitmap imageUri ) {
        Intent intent = new Intent(this, ShowProduct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("name",products.get(position).getName());
//        intent.putExtra("description",products.get(position).getDescription());
//        intent.putExtra("price",products.get(position).getPrice());
//        intent.putExtra("quantity",products.get(position).getQuantity());
//        intent.putExtra("rating",products.get(position).getRating().getRate());
//        intent.putExtra("length",products.get(position).getRating().getLength());
//        intent.putExtra("brand",products.get(position).getBrand().getName());
//        intent.putExtra("category",products.get(position).getCategory().getName());
//        intent.putExtra("ImageURl", ApiRetrofit.API_IMAGE_BASE_URL + products.get(position).getThumbnail());
//        intent.putExtra("proID",products.get(position).getId());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setChannelId("default_channel_id")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(imageUri).setSummaryText(messageBody))/*Notification with Image*/
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = null;
            if (notificationManager != null) {
                mChannel = notificationManager.getNotificationChannel("default_channel_id");
            }
            if (mChannel == null) {
                mChannel = new NotificationChannel("default_channel_id", getString(R.string.app_name), importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(mChannel);
                }
            }
        }
        if (notificationManager != null) {
            notificationManager.notify(0, notificationBuilder.build());
        }
    }
    public static Bitmap getBitmapFromURL(String url) {
        try {
            URL Url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) Url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmapFrmUrl = BitmapFactory.decodeStream(input);
            return bitmapFrmUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}