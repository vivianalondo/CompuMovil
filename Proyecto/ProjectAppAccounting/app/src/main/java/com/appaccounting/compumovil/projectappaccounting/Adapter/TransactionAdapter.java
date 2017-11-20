package com.appaccounting.compumovil.projectappaccounting.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appaccounting.compumovil.projectappaccounting.Pojo.Debit;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Entrie;
import com.appaccounting.compumovil.projectappaccounting.R;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by viviana on 20/11/17.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{

    private List<Entrie> ingresos;
    private List<Debit> gastos;
    private int rowLayout;
    private Context context;


    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        LinearLayout transactionsLayout;
        TextView nameTransaction;
        TextView descriptionTransaction;
        TextView dateTransaction;
        TextView amountTransaction;
        ImageView avator;


        public TransactionViewHolder(View v) {
            super(v);
            transactionsLayout = (LinearLayout) v.findViewById(R.id.layoutListTransactions);
            nameTransaction = (TextView) v.findViewById(R.id.textNameT);
            descriptionTransaction = (TextView) v.findViewById(R.id.textDescriptionT);
            dateTransaction = (TextView) v.findViewById(R.id.textFechaT);
            avator = (ImageView) itemView.findViewById(R.id.imageTransaction);

            /*v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    //intent.putExtra("variable_string", (String)name.getText());
                    intent.putExtra(DetailActivity.EXTRA_NOMBRE, (String)numberApartment.getText());
                    Log.d("TAGO",  (String)numberApartment.getText());
                    context.startActivity(intent);
                }
            });*/

        }
    }

    public TransactionAdapter(List<Entrie> ingresos, int rowLayout, Context context) {
        this.ingresos = ingresos;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public TransactionAdapter.TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TransactionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TransactionViewHolder holder, final int position) {

        //holder.avator.setImageBitmap(mPlaceAvators[position % mPlaceAvators.length]);
        holder.nameTransaction.setText(ingresos.get(position).getCategoryDebit().toString());
        holder.descriptionTransaction.setText(ingresos.get(position).getDescription());
        holder.dateTransaction.setText(ingresos.get(position).getDate());
        holder.amountTransaction.setText(String.valueOf(ingresos.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        int sizel = 0;
        if (ingresos != null){
            sizel = ingresos.size();
        }
        return sizel;
    }


    public Entrie removeItem(int position) {
        final Entrie model = ingresos.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Entrie model) {
        ingresos.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Entrie model = ingresos.remove(fromPosition);
        ingresos.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Entrie> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Entrie> newModels) {
        for (int i = ingresos.size() - 1; i >= 0; i--) {
            final Entrie model = ingresos.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Entrie> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Entrie model = newModels.get(i);
            if (!ingresos.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Entrie> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Entrie model = newModels.get(toPosition);
            final int fromPosition = ingresos.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
}
