package com.appaccounting.compumovil.projectappaccounting;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appaccounting.compumovil.projectappaccounting.Helpers.DbHelper;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Budget;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Debit;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Entrie;

import java.io.IOException;
import java.util.ArrayList;


public class PrincipalFragment extends Fragment {
    View view;
    private ArrayList debits;
    private ArrayList entries;
    private ArrayList budgets;
    private DbHelper dbh;
    private Context context;
    private Debit gasto;
    private Entrie ingreso;
    private Budget presupuesto;
    Double totalGastos;
    Double totalIngresos;
    Double totalSaldo;
    Double totalPresupuesto;
    private TextView txtSaldoValor;
    private TextView txtSaldoIngresos;
    private TextView txtSaldoGastos;
    private TextView txtSaldoPresupuesto;

    public PrincipalFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_principal, container, false);
        context = getActivity().getBaseContext();
        dbh = new DbHelper(context);


        txtSaldoValor = (TextView) view.findViewById(R.id.saldovalor);
        txtSaldoIngresos = (TextView) view.findViewById(R.id.ingresosresult);
        txtSaldoGastos = (TextView) view.findViewById(R.id.gastosresult);
        txtSaldoPresupuesto = view.findViewById(R.id.ṕresupuestoresult);

        totalGastos = 0.0;
        totalIngresos = 0.0;
        totalSaldo = 0.0;
        totalPresupuesto = 0.0;

        if (dbh.hayDebits()){

            try {
                debits = dbh.getDebitByUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < debits.size(); i++){
                gasto = new Debit();
                gasto = (Debit) debits.get(i);
                System.out.println("Valor del gasto "+ gasto.getAmount());
                totalGastos = totalGastos + gasto.getAmount();
                //itemsCategoryDebit.add(catdebit.getName());
            }
        }else {
            System.out.println("No hay gastos");
        }

        if (dbh.hayEntries()){
            try {
                entries = dbh.getEntrieByUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < entries.size(); i++){
                ingreso = new Entrie();
                ingreso = (Entrie) entries.get(i);
                System.out.println("Valor del ingreso "+ ingreso.getAmount());
                totalIngresos = totalIngresos + ingreso.getAmount();
                //itemsCategoryDebit.add(catdebit.getName());
            }
        }else {
            System.out.println("No hay gastos");
        }

        if (dbh.hayBudgets()){
            try {
                budgets = dbh.getBudgetByUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < budgets.size(); i++){
                presupuesto = new Budget();
                presupuesto = (Budget) budgets.get(i);
                System.out.println("Valor del presupuesto2 "+ presupuesto.getAmount());
                totalPresupuesto = totalPresupuesto + presupuesto.getAmount();
                //itemsCategoryDebit.add(catdebit.getName());
            }
        }else {
            System.out.println("No hay gastos");
        }

        totalSaldo = totalIngresos - totalGastos;
        txtSaldoValor.setText(totalSaldo.toString());
        txtSaldoIngresos.setText(totalIngresos.toString());
        txtSaldoGastos.setText(totalGastos.toString());
        txtSaldoPresupuesto.setText(totalPresupuesto.toString());

        System.out.println("la suma de los gastos es: "+totalGastos);

        return view;
    }



}
