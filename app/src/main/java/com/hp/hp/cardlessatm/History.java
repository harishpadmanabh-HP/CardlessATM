package com.hp.hp.cardlessatm;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    AsyncHttpClient client;
    JSONArray jarray;
    JSONObject jobject;
    RequestParams params;
    ListView lv;
    ArrayList<String> bank_id;
    ArrayList<String>user_id;
    ArrayList<String> credit;
    ArrayList<String>debit;
    ArrayList<String>balance;
    ArrayList<String>date;

    TextView namehead;

    String historyAPI="http://sicsglobal.com/WEBTEAM/WebT1/Cardless_ATM/Api/transaction_history";
    String paramskey="userid";

    String userids,phones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        namehead=findViewById(R.id.usernameheading);

        client = new AsyncHttpClient();
        params = new RequestParams();
        //	submit=(Button)findViewById(R.id.submit);
        lv = (ListView) findViewById(R.id.lv);
        bank_id = new ArrayList<String>();
        user_id = new ArrayList<String>();
        credit = new ArrayList<String>();
        debit = new ArrayList<String>();
        balance = new ArrayList<String>();
        date = new ArrayList<String>();


        SharedPreferences sharedlogin = getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);
        String names=sharedlogin.getString("name",null);
        String emails=sharedlogin.getString("email",null);
        phones=sharedlogin.getString("number",null);
        userids=sharedlogin.getString("userid",null);
        String qrcosdes=sharedlogin.getString("qrcode",null);

        namehead.setText(names);

        params.put(paramskey,userids);

        client.post(historyAPI,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);

                try{

                    jobject=new JSONObject(content);

                    String s = jobject.getString("message");
                    Log.e(s,"RRRRRRRR");

                    if(s.equalsIgnoreCase("success")){
                        jarray=jobject.getJSONArray("details");

                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject obj = jarray.getJSONObject(i);


                       String bankid=obj.getString("bank_id");
                       bank_id.add(bankid);

                       String useridd=obj.getString("user_id");
                       user_id.add(useridd);

                            String scredit=obj.getString("credit");
                            credit.add(scredit);

                            String sdebit=obj.getString("debit");
                            debit.add(sdebit);

                            String sbalance=obj.getString("balance");
                            balance.add(sbalance);

                            String sdate=obj.getString("date");
                            date.add(sdate);




                        }


                        }

                    adapter adpt = new adapter();
                    lv.setAdapter(adpt);


                }catch (Exception e)
                {
                    Toast.makeText(History.this, "Exception caught"+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    class adapter extends BaseAdapter {
        LayoutInflater Inflater;
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return bank_id.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=Inflater.inflate(R.layout.listitem,null);
            Viewholder holder=new Viewholder();

            holder.bankidtv=(TextView)convertView.findViewById(R.id.bankidtxt);
            holder.bankidtv.setText(bank_id.get(position));

            holder.datetv=(TextView)convertView.findViewById(R.id.datetext);
            holder.datetv.setText(date.get(position));

            holder.debittv=(TextView)convertView.findViewById(R.id.debittext);
            holder.debittv.setText(debit.get(position));

            holder.credittv=(TextView)convertView.findViewById(R.id.credittext);
            holder.credittv.setText(credit.get(position));

            holder.balancetv=(TextView)convertView.findViewById(R.id.balancetext);
            holder.balancetv.setText(balance.get(position));



            return convertView;
        }
        class Viewholder{
            TextView bankidtv;
            TextView datetv;
            TextView credittv;
            TextView debittv;
            TextView balancetv;



        }
    }

}

