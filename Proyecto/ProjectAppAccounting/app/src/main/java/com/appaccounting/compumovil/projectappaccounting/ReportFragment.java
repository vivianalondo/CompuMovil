package com.appaccounting.compumovil.projectappaccounting;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appaccounting.compumovil.projectappaccounting.Helpers.DbHelper;
import com.juang.jplot.PlotPastelito;

public class ReportFragment extends Fragment {
    View view;
    private PlotPastelito pastel;
    private LinearLayout pantalla;
    Context context;
    String totalGastos;
    String totalIngresos;
    Double totalSaldo;
    DbHelper dbh;
    float fGastos;
    float fIngresos;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);
        context = getActivity().getBaseContext();
        dbh = new DbHelper(context);

        totalGastos = dbh.countTotalDebits().toString();
        totalIngresos = dbh.countTotalEntries().toString();
        fGastos = Float.parseFloat(totalGastos);
        fIngresos = Float.parseFloat(totalIngresos);

        pantalla= (LinearLayout) (view.findViewById(R.id.pantallaReport));
        pastel=new PlotPastelito(context,"Ganancias Diarias");//puedes usar simplemente "this" en lugar de context
        float[] datapoints = {fGastos,fIngresos};
        String[] etiquetas={"Ingresos", "Gastos"};
        pastel.SetDatos(datapoints,etiquetas);

   /*antes de mostrar el grafico en pantalla(LinearLayout) deben de ir todos los ajustes "Set" del grafico.
       Todos los metodos publicos que ayudan a personalizar el grafico se describen cada uno en la siguiente sección */

        pastel.SetHD(true); //ajustamos la calidad hd que suaviza bordes del grafico. por default esta desactivado
        pastel.SetColorDato(1,100,100,100);
        pastel.SetColorDato(2,200,100,100);
        pantalla.addView(pastel);


        return view;
    }

}
