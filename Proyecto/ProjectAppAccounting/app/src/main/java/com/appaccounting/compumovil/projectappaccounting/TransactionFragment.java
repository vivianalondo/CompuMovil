package com.appaccounting.compumovil.projectappaccounting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.appaccounting.compumovil.projectappaccounting.Adapter.TransactionAdapter;
import com.appaccounting.compumovil.projectappaccounting.Helpers.DbHelper;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Debit;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Entrie;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Transaction;
import com.appaccounting.compumovil.projectappaccounting.Pojo.User;

import java.io.IOException;
import java.util.ArrayList;


public class TransactionFragment extends Fragment {

    private ArrayList gastosArray;
    private ArrayList ingresosArray;
    private ArrayList movimientosArray;
    private TransactionAdapter tAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private Transaction transaction;
    private Transaction transactionIn;
    private Entrie ingreso;
    private Debit gasto;
    private User userLogueado;
    DbHelper dbh;
    View view;

    public TransactionFragment() {//activity que enseña información

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_transaction, container, false);
        view=inflater.inflate(R.layout.fragment_transaction, container, false);
        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view_transaction, container, false);
        context = getActivity().getApplicationContext();
        transaction = new Transaction();
        transactionIn = new Transaction();
        gasto = new Debit();
        ingreso = new Entrie();
        userLogueado = new User();
        dbh = new DbHelper(context);
        Integer i = 0;
        movimientosArray = new ArrayList();

        userLogueado = dbh.getLogin();

        if (!dbh.hayEntries()) {
            if (!dbh.hayDebits()) {
                Toast.makeText(context, "No se encontraron transacciones", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    gastosArray = dbh.getDebitByUser();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if (dbh.hayDebits()) {
            try {
                gastosArray = dbh.getDebitByUser();
                ingresosArray = dbh.getEntrieByUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {;
                 ingresosArray = dbh.getEntrieByUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(ingresosArray==null){
            if (gastosArray==null){
                Toast.makeText(context, "No se encontraron transacciones", Toast.LENGTH_SHORT).show();
            }
        }else if (gastosArray.size()>=1){
            for (i=0;i<gastosArray.size();i++){
                gasto = (Debit) gastosArray.get(i);
                transaction = new Transaction(userLogueado.getId(), gasto.getAmount(), gasto.getDescription(),
                        gasto.getDate(), gasto.getCategoryDebit(),2);
                movimientosArray.add(transaction);
            }
            for (i=0;i<ingresosArray.size();i++){
                ingreso = (Entrie)ingresosArray.get(i);
                transaction = new Transaction(userLogueado.getId(), ingreso.getAmount(), ingreso.getDescription(),
                        ingreso.getDate(), ingreso.getCategoryEntrie(),1);
                movimientosArray.add(transaction);
            }
        }else {
            for (i=0;i<ingresosArray.size();i++){
                ingreso = (Entrie)ingresosArray.get(i);
                transaction = new Transaction(userLogueado.getId(), ingreso.getAmount(), ingreso.getDescription(),
                        ingreso.getDate(), ingreso.getCategoryEntrie(),1);
                movimientosArray.add(transaction);
            }
        }

        if (movimientosArray.size()<1){
            Toast.makeText(context, "No se encontraron transacciones", Toast.LENGTH_SHORT).show();

        }

            tAdapter = new TransactionAdapter(movimientosArray, R.layout.fragment_transaction, getContext());
            recyclerView.setAdapter(tAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setFilterTouchesWhenObscured(true);
            recyclerView.setHasFixedSize(true);

        return recyclerView;


    }
}
