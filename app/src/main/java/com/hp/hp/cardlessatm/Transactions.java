package com.hp.hp.cardlessatm;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.hp.hp.cardlessatm.Adapter.TransactionsAdapter;
import com.hp.hp.cardlessatm.Model.HistoryModel;
import com.hp.hp.cardlessatm.Retro.Retro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transactions extends AppCompatActivity {

    private String phones;
    private String userids;
    HistoryModel historyModel;
    RecyclerView lv;
    TextView namehead;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_spare);

        lv=findViewById(R.id.lv);
        namehead=findViewById(R.id.usernameheading);

        SharedPreferences sharedlogin = getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);
        String names=sharedlogin.getString("name",null);
        String emails=sharedlogin.getString("email",null);
        phones = sharedlogin.getString("number", null);
        userids=sharedlogin.getString("userid",null);
        String qrcosdes=sharedlogin.getString("qrcode",null);

        namehead.setText(names);



        new Retro().getApi().HISTORY_MODEL_CALL(userids).enqueue(new Callback<HistoryModel>() {
            @Override
            public void onResponse(Call<HistoryModel> call, Response<HistoryModel> response) {
              historyModel=response.body();

                //lv.setHasFixedSize(true);
                LinearLayoutManager verticalLayoutmanager
                        = new LinearLayoutManager(Transactions.this, RecyclerView.VERTICAL, false);
                lv.setLayoutManager(verticalLayoutmanager);
                lv.setAdapter(new TransactionsAdapter(historyModel,Transactions.this));



            }

            @Override
            public void onFailure(Call<HistoryModel> call, Throwable t) {
                Toast.makeText(Transactions.this, "API FAILURE : "+t, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
