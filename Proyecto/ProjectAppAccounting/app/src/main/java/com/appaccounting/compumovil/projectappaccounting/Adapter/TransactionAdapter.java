package com.appaccounting.compumovil.projectappaccounting.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appaccounting.compumovil.projectappaccounting.Helpers.DbHelper;
import com.appaccounting.compumovil.projectappaccounting.Pojo.CategoryDebit;
import com.appaccounting.compumovil.projectappaccounting.Pojo.CategoryEntrie;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Debit;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Entrie;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Transaction;
import com.appaccounting.compumovil.projectappaccounting.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by viviana on 20/11/17.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{

    private List<Entrie> ingresos;
    private List<Debit> gastos;
    private List<Transaction> movimientos;
    private int rowLayout;
    Context context;
    private DbHelper dbh;
    private Drawable imgDrawable;
    View view;


    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        LinearLayout transactionsLayout;
        TextView nameTransaction;
        TextView descriptionTransaction;
        TextView dateTransaction;
        TextView amountTransaction;
        ImageView avator;


        public TransactionViewHolder(View v) {
            super(v);
            transactionsLayout = (LinearLayout) v.findViewById(R.id.transaction_layout);
            nameTransaction = (TextView) v.findViewById(R.id.textNameT);
            descriptionTransaction = (TextView) v.findViewById(R.id.textDescriptionT);
            dateTransaction = (TextView) v.findViewById(R.id.textFechaT);
            amountTransaction = (TextView)v.findViewById(R.id.ingresosresult);
            avator = (ImageView) itemView.findViewById(R.id.imageTransaction);


        }
    }

    public TransactionAdapter(List<Transaction> movimientos, int rowLayout, Context context) {
        this.movimientos = movimientos;
        this.rowLayout = rowLayout;
        this.context = context;
        dbh = new DbHelper(context);
        context = context;
    }

    @Override
    public TransactionAdapter.TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TransactionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TransactionViewHolder holder, final int position) {
        CategoryEntrie categoryEntrie = new CategoryEntrie();
        CategoryDebit categoryDebite = new CategoryDebit();
        String nameCategory = "";
        int tipoTransaccion = 0;

        tipoTransaccion = movimientos.get(position).getTypeTransaction();
        if (tipoTransaccion==1){
            try {
                categoryEntrie  = dbh.getCategoryEntriesByID(movimientos.get(position).getCategory().toString());
                nameCategory = categoryEntrie.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (tipoTransaccion==2){
            try {
                categoryDebite  = dbh.getCategoryDebitsByID(movimientos.get(position).getCategory().toString());
                nameCategory = categoryDebite.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        switch (nameCategory){
            case "Salario":
                imgDrawable = view.getResources().getDrawable(R.drawable.ic_salary);
                break;
            case "Comida afuera":
                imgDrawable = view.getResources().getDrawable(R.drawable.ic_restaurant);
                break;
            default:
                imgDrawable = view.getResources().getDrawable(R.drawable.ic_add_circle_black_24dp);
                break;
        }

        //holder.avator.setImageBitmap(mPlaceAvators[position % mPlaceAvators.length]);
        holder.avator.setImageDrawable(imgDrawable);
        holder.nameTransaction.setText(nameCategory);
        holder.descriptionTransaction.setText(movimientos.get(position).getDescription());
        holder.dateTransaction.setText(movimientos.get(position).getDate());
        holder.amountTransaction.setText(String.valueOf(movimientos.get(position).getAmount()));

        if (movimientos.get(position).getTypeTransaction()==1){
            holder.amountTransaction.setTextColor(view.getResources().getColor(R.color.colorGreen));
        }else {
            holder.amountTransaction.setTextColor(view.getResources().getColor(R.color.colorRed));
        }
    }

    @Override
    public int getItemCount() {
        int sizel = 0;
        if (movimientos != null){
            sizel = movimientos.size();
        }
        return sizel;
    }


    public Transaction removeItem(int position) {
        final Transaction model = movimientos.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Transaction model) {
        movimientos.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Transaction model = movimientos.remove(fromPosition);
        movimientos.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Transaction> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Transaction> newModels) {
        for (int i = movimientos.size() - 1; i >= 0; i--) {
            final Transaction model = movimientos.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Transaction> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Transaction model = newModels.get(i);
            if (!movimientos.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Transaction> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Transaction model = newModels.get(toPosition);
            final int fromPosition = movimientos.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
}
