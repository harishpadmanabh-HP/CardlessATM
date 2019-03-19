package com.hp.hp.cardlessatm;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Home extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;//test git
    private int storagepermissioncode=1;
    String str =null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userids,phones; String otp;
    String OTPapi="http://sicsglobal.com/WEBTEAM/WebT1/Cardless_ATM/Api/push_notification_new";
    String paramss="userid";
    AsyncHttpClient client;
    JSONArray jarray;
    JSONObject jobject;
    RequestParams params;
    TextView namehead,name,email,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        client = new AsyncHttpClient();
        params = new RequestParams();

namehead=findViewById(R.id.usernameheading);
        name=findViewById(R.id.username);
        email=findViewById(R.id.useremail);
        phone=findViewById(R.id.userphone);

        sharedPreferences=getApplicationContext().getSharedPreferences("pref",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        SharedPreferences sharedlogin = getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);
        String names=sharedlogin.getString("name",null);
        String emails=sharedlogin.getString("email",null);
         phones=sharedlogin.getString("number",null);
 userids=sharedlogin.getString("userid",null);
        String qrcosdes=sharedlogin.getString("qrcode",null);

        namehead.setText(names);
        name.setText(names);

        email.setText(emails);

        phone.setText(phones);


        ActivityCompat.requestPermissions(this,new String[] {android.Manifest.permission.CAMERA,Manifest.permission.SEND_SMS},storagepermissioncode);

        if((ContextCompat.checkSelfPermission(Home.this,
                Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)&&(ContextCompat.checkSelfPermission(Home.this,
                Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)){
            Toast.makeText(Home.this, "ALREADY GRANTED  PERMISSION", Toast.LENGTH_SHORT).show();
        }
        else{
            requeststroagepermission();
        }
    }
    private void requeststroagepermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
            new AlertDialog.Builder(this)
                    .setTitle("PERMISSION NEEDED")
                    .setMessage("This permission is needed for scan QR")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Home.this, new String[] {Manifest.permission.CAMERA},storagepermissioncode);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){
            new AlertDialog.Builder(this)
                    .setTitle("PERMISSION NEEDED")
                    .setMessage("This permission is needed for scan QR")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Home.this, new String[] {Manifest.permission.CAMERA},storagepermissioncode);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA,Manifest.permission.SEND_SMS},storagepermissioncode);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==storagepermissioncode){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Scan QR", Toast.LENGTH_SHORT).show();
        }
    }
    public void ScanQR(View view) {
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(this);

        setContentView(scannerView);
        scannerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {

        //  final String resultcode = result.getText();
        final String resultcode = result.getText();
        Log.e("RESULTCODE",resultcode);
        Toast.makeText(this, ""+resultcode, Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Scan result");
        builder.setMessage(resultcode);
        builder.setPositiveButton("Get OTP", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                editor.putString("qrcode",resultcode);
                //Log.e("ENCODEINNN",msg);
                editor.commit();
                params.put("userid","2");
                client.post(OTPapi,params,new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);

                        try{
                            jobject=new JSONObject(content);
//OTP FOR THE USER
                             otp=jobject.getString("details");
                          //  SmsManager smsManager = SmsManager.getDefault();
                           // smsManager.sendTextMessage(phones, null, "Your otp for cardlessATM is "+otp, null, null);
getnotify(otp);
                            startActivity(new Intent(Home.this,Home.class));
                            Toast.makeText(Home.this, "OTP"+otp, Toast.LENGTH_LONG).show();

                        }catch (Exception e){
                            Toast.makeText(Home.this, "Exeption caught"+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });



        //  builder.setMessage(str.toString());
        AlertDialog alertdialoge=builder.create();
        alertdialoge.show();


//        NotificationManager notificationManager = (NotificationManager)
//                this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent notificationIntent = new Intent(this, Home.class);
//
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        PendingIntent intent =
//                PendingIntent.getActivity(this, 0,
//                        notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ic_gold_medal);
//        //  NotificationManager mnNotificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        String textTitle="CARDLESS ATM";
//        String textContent= "RECIEVED OTP";
//        String body= "YOUR OTP IS";
//        String message="YOUR OTP IS "+ otp;
//        long when = System.currentTimeMillis();
//
//        Notification notification = new NotificationCompat.Builder(this)
//                .setContentTitle(textTitle)
//                .setContentText(message)
//                .setContentIntent(intent)
//                .setSmallIcon(R.drawable.ic_gold_medal)
//                .setWhen(when)
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(bitmap).setSummaryText(message))
//                .build();
////        notify.flags |= Notification.FLAG_AUTO_CANCEL;
////        notif.notify(0, notify);
//
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//// Play default notification sound
//        notification.defaults |= Notification.DEFAULT_SOUND;
//        notification.defaults |= Notification.DEFAULT_VIBRATE;
//        notificationManager.notify(0, notification);

    }

public  void getnotify(String otp){

    NotificationManager notificationManager = (NotificationManager)
            this.getSystemService(Context.NOTIFICATION_SERVICE);

    Intent notificationIntent = new Intent(this, Home.class);

    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
            | Intent.FLAG_ACTIVITY_SINGLE_TOP);

    PendingIntent intent =
            PendingIntent.getActivity(this, 0,
                    notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
            R.drawable.ic_gold_medal);
    //  NotificationManager mnNotificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    String textTitle="CARDLESS ATM";
    String textContent= "RECIEVED OTP";
    String body= "YOUR OTP IS";
    String message="OTP : "+ otp;
    long when = System.currentTimeMillis();

    Notification notification = new NotificationCompat.Builder(this)
            .setContentTitle(textTitle)
            .setContentText(message)
            .setContentIntent(intent)
            .setSmallIcon(R.drawable.ic_gold_medal)
            .setWhen(when)
            .setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap).setSummaryText(message))
            .build();
//        notify.flags |= Notification.FLAG_AUTO_CANCEL;
//        notif.notify(0, notify);

    notification.flags |= Notification.FLAG_AUTO_CANCEL;

// Play default notification sound
    notification.defaults |= Notification.DEFAULT_SOUND;
    notification.defaults |= Notification.DEFAULT_VIBRATE;
    notificationManager.notify(0, notification);


}
    public void History(View view) {
        startActivity(new Intent(Home.this,History.class));
    }
}

