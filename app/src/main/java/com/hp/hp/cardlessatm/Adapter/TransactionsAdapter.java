package com.hp.hp.cardlessatm.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hp.hp.cardlessatm.History;
import com.hp.hp.cardlessatm.Model.HistoryModel;
import com.hp.hp.cardlessatm.R;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsVH> {

    HistoryModel historyModel;
    Context context;

    public TransactionsAdapter(HistoryModel historyModel, Context context) {
        this.historyModel = historyModel;
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionsVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v =  LayoutInflater.from(context)
                .inflate(R.layout.sparelistitem, viewGroup, false);

        return new TransactionsVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsVH transactionsVH, int pos) {

        transactionsVH.bankidtxt.setText(historyModel.getDetails().get(pos).getBank_id());
        transactionsVH.datetext.setText(historyModel.getDetails().get(pos).getDate());
        transactionsVH.debittext.setText(historyModel.getDetails().get(pos).getDebit());
        transactionsVH.credittext.setText(historyModel.getDetails().get(pos).getCredit());
        transactionsVH.balancetext.setText(historyModel.getDetails().get(pos).getBalance());


    }

    @Override
    public int getItemCount() {
        return historyModel.getDetails().size();
    }

    class TransactionsVH extends RecyclerView.ViewHolder{

        TextView bankidtxt,datetext,debittext,credittext,balancetext;


        public TransactionsVH(@NonNull View itemView) {
            super(itemView);

            bankidtxt=itemView.findViewById(R.id.bankidtxt);
            datetext=itemView.findViewById(R.id.datetext);
            debittext=itemView.findViewById(R.id.debittext);
            credittext=itemView.findViewById(R.id.credittext);
            balancetext=itemView.findViewById(R.id.balancetext);

        }
    }
}
