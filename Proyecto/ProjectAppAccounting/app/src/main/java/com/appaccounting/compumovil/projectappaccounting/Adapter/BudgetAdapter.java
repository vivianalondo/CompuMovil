package com.appaccounting.compumovil.projectappaccounting.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appaccounting.compumovil.projectappaccounting.Helpers.DbHelper;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Budget;
import com.appaccounting.compumovil.projectappaccounting.Pojo.CategoryDebit;
import com.appaccounting.compumovil.projectappaccounting.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by viviana on 22/11/17.
 */

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>{

    private List<Budget> presupuestos;
    private int rowLayout;
    Context context;
    private DbHelper dbh;
    private Drawable imgDrawable;
    View view;


    public static class BudgetViewHolder extends RecyclerView.ViewHolder {
        LinearLayout transactionsLayout;
        TextView nameTransaction;
        TextView descriptionTransaction;
        TextView dateIniTransaction;
        TextView dateFinTransaction;
        TextView amountTransaction;
        ImageView avator;


        public BudgetViewHolder(View v) {
            super(v);
            transactionsLayout = (LinearLayout) v.findViewById(R.id.budget_layout);
            nameTransaction = (TextView) v.findViewById(R.id.textNameB);
            descriptionTransaction = (TextView) v.findViewById(R.id.textDescriptionB);
            dateIniTransaction = (TextView) v.findViewById(R.id.textFechaInicioB);
            dateFinTransaction = (TextView) v.findViewById(R.id.textFechaFinB);
            amountTransaction = (TextView)v.findViewById(R.id.valorBudget);
            avator = (ImageView) itemView.findViewById(R.id.imageBudget);


        }
    }

    public BudgetAdapter(List<Budget> presupuestos, int rowLayout, Context context) {
        this.presupuestos = presupuestos;
        this.rowLayout = rowLayout;
        this.context = context;
        dbh = new DbHelper(context);
        context = context;
    }

    @Override
    public BudgetAdapter.BudgetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new BudgetAdapter.BudgetViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BudgetAdapter.BudgetViewHolder holder, final int position) {
        CategoryDebit categoryDebite = new CategoryDebit();
        String nameCategory = "";

        try {
            categoryDebite  = dbh.getCategoryDebitsByID(presupuestos.get(position).getCategoryDebit().toString());
            nameCategory = categoryDebite.getName();
        } catch (IOException e) {
            e.printStackTrace();
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
        holder.descriptionTransaction.setText(presupuestos.get(position).getDescription());
        holder.dateIniTransaction.setText(presupuestos.get(position).getStartDate());
        holder.dateFinTransaction.setText(presupuestos.get(position).getEndDate());

        holder.amountTransaction.setText(String.valueOf(presupuestos.get(position).getAmount()));
        holder.amountTransaction.setTextColor(view.getResources().getColor(R.color.colorBlue));

    }

    @Override
    public int getItemCount() {
        int sizel = 0;
        if (presupuestos != null){
            sizel = presupuestos.size();
        }
        return sizel;
    }


    public Budget removeItem(int position) {
        final Budget model = presupuestos.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Budget model) {
        presupuestos.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Budget model = presupuestos.remove(fromPosition);
        presupuestos.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Budget> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Budget> newModels) {
        for (int i = presupuestos.size() - 1; i >= 0; i--) {
            final Budget model = presupuestos.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Budget> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Budget model = newModels.get(i);
            if (!presupuestos.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Budget> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Budget model = newModels.get(toPosition);
            final int fromPosition = presupuestos.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
}
