package com.appaccounting.compumovil.projectappaccounting;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appaccounting.compumovil.projectappaccounting.Adapter.BudgetAdapter;
import com.appaccounting.compumovil.projectappaccounting.Helpers.DbHelper;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Budget;
import com.appaccounting.compumovil.projectappaccounting.Pojo.User;

import java.io.IOException;
import java.util.ArrayList;


public class budgetFragment extends Fragment {
    private ArrayList presupuestosArray;
    private BudgetAdapter bAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private Budget budget;
    private User userLogueado;
    DbHelper dbh;
    View view;


    public budgetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_budget, container, false);
        context=getActivity().getBaseContext();
        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view_budget, container, false);

        budget = new Budget();
        userLogueado = new User();
        dbh = new DbHelper(context);
        Integer i = 0;
        presupuestosArray = new ArrayList();
        userLogueado = dbh.getLogin();

        if (!dbh.hayBudgets()) {
            Toast.makeText(context, "No se encontraron presupuestos registrados", Toast.LENGTH_SHORT).show();
        }else {
            try {
                presupuestosArray = dbh.getBudgetByUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (presupuestosArray==null){
            Toast.makeText(context, "No se encontraron presupuestos registrados", Toast.LENGTH_SHORT).show();
        }
            bAdapter = new BudgetAdapter(presupuestosArray, R.layout.fragment_budget, getContext());
            recyclerView.setAdapter(bAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setFilterTouchesWhenObscured(true);
            recyclerView.setHasFixedSize(true);


        return recyclerView;

    }


}
