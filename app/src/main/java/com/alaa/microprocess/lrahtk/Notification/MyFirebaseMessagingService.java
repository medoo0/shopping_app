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
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.ShowProduct;
import com.google.firebase.messaging.RemoteMessage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;

/**
 * Created by microprocess on 2018-07-08.
 */

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "Notification";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {




        //imageUri will contain URL of the image to be displayed with Notification


        String imageUri = ApiRetrofit.API_IMAGE_BASE_URL + remoteMessage.getData().get("thumbnail");
        String name = remoteMessage.getData().get("name");
        String description = remoteMessage.getData().get("description");
        double price = Double.parseDouble(remoteMessage.getData().get("price"));
        int quantity = Integer.parseInt(remoteMessage.getData().get("quantity"));
        float rating = Float.parseFloat(remoteMessage.getData().get("rating"));
        String id = remoteMessage.getData().get("id");
        String Brand = remoteMessage.getData().get("brand");
        String category = remoteMessage.getData().get("category");

        //Calling method to generate notification

        Bitmap Image_Bitmap = getBitmapFromURL(imageUri);

        sendNotification(name,description,price,quantity,id,Brand,rating,category,imageUri,Image_Bitmap);

    }

    //This method is only generating push notification
    private void sendNotification(String name ,String description, double price, int quantity, String id,
                                  String Brand , float rating ,String category ,String imageUri ,Bitmap bitmapImage ) {
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        Intent intent = new Intent(this, ShowProduct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("name",name);
        intent.putExtra("description",description);
        intent.putExtra("price",price);
        intent.putExtra("quantity",quantity);
        intent.putExtra("rating",rating);
//      intent.putExtra("length",products.get(position).getRating().getLength());
        intent.putExtra("brand",Brand);
        intent.putExtra("category",category);
        intent.putExtra("ImageURl", imageUri);
        intent.putExtra("proID",id);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))
                .setContentTitle(getString(R.string.app_name))
                .setContentText(" تم اضافة "+name)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(getBaseContext(), R.color.blue))
                .setSound(defaultSoundUri)
                .setChannelId("default_channel_id")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmapImage).setSummaryText( " تم اضافة "+ name))/*Notification with Image*/
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
            notificationManager.notify(m, notificationBuilder.build());
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

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }
}