package com.appaccounting.compumovil.projectappaccounting;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.appaccounting.compumovil.projectappaccounting.Helpers.DbHelper;
import com.juang.jplot.PlotPastelito;

public class ReportGeneralActivity extends AppCompatActivity {

    private PlotPastelito pastel;
    private LinearLayout pantalla;
    Context context;
    float fGastos;
    float fIngresos;
    DbHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_general);
        context=this;
        dbh = new DbHelper(context);
        fGastos = Float.parseFloat(dbh.countTotalDebits().toString());
        fIngresos = Float.parseFloat(dbh.countTotalEntries().toString());

        pantalla= (LinearLayout) (findViewById(R.id.pantallaReportGeneral));
        pastel=new PlotPastelito(context,"Balance General");//puedes usar simplemente "this" en lugar de context
        float[] datapoints = {fGastos,fIngresos};
        String[] etiquetas={"Ingresos", "Gastos"};
        pastel.SetDatos(datapoints,etiquetas);

   /*antes de mostrar el grafico en pantalla(LinearLayout) deben de ir todos los ajustes "Set" del grafico.
       Todos los metodos publicos que ayudan a personalizar el grafico se describen cada uno en la siguiente sección */

        pastel.SetHD(true); //ajustamos la calidad hd que suaviza bordes del grafico. por default esta desactivado
        pastel.SetColorDato(1,100,100,100);
        pastel.SetColorDato(2,200,100,100);
        pantalla.addView(pastel);
    }
}
