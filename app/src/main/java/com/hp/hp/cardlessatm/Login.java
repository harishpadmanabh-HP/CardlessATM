package com.hp.hp.cardlessatm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    AsyncHttpClient client;
    JSONArray jarray;
    JSONObject jobject;
    RequestParams params;
    EditText email,phn;
    Button login;

    String loginapi="http://sicsglobal.com/WEBTEAM/WebT1/Cardless_ATM/Api/login";
    String params1="email";
    String params2="num";
    String methodapi="POST";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       // Toast.makeText(this, ""+refreshedToken, Toast.LENGTH_SHORT).show();

        email=findViewById(R.id.email);
        phn=findViewById(R.id.phn);
        login=findViewById(R.id.login);


        client = new AsyncHttpClient();
        params = new RequestParams();


    }

    public void login(View view) {

        String emails= email.getText().toString();
        String phns=phn.getText().toString();

        params.put("email",emails);
        params.put("num",phns);


        client.post(loginapi,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);

                try{
                    jobject = new JSONObject(content);
                    Log.e(content,"content");

                    JSONObject details =jobject.getJSONObject("details");
                    String userid=details.getString("userid");
                    String name=details.getString("name");
                    String email=details.getString("email");
                    String number=details.getString("number");
                    String qrcode=details.getString("qrcode");



                    SharedPreferences sharedlogin = getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedlogin.edit();

                    editor.putString("userid",userid);
                    editor.putString("name",name);
                    editor.putString("email",email);
                    editor.putString("number",number);
                    editor.putString("qrcode",qrcode);
                    editor.apply();



                    String message=jobject.getString("message");
                    String status=jobject.getString("status");

                    if(message.equalsIgnoreCase("success"))
                    {
                        NotificationManager notificationManager = (NotificationManager)
                                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                     //   Intent notificationIntent = new Intent(this, Home.class);

                      //  notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                               // | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                       // PendingIntent intent =
                                //PendingIntent.getActivity(this, 0,
                                    //    notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_gold_medal);
                        //  NotificationManager mnNotificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                        String textTitle="TESTTTTTTTTTTTTTTTT";
                        String textContent= "NOTIFY!!!!!!!!!!!!!";
                        String body= "NOTIFY!!!!!!!!!!!!!";
                        String messages= "NOTIFY!!!!!!!!!!!!!";
                        long when = System.currentTimeMillis();

                        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                                .setContentTitle(textTitle)
                                .setContentText(messages)
                               // .setContentIntent(intent)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
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
                        Toast.makeText(Login.this, "Login Success Full", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this,Home.class));
                    }
                    else{
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }









                }catch (Exception e)
                {
                    Toast.makeText(Login.this, "Execpetion caught "+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
