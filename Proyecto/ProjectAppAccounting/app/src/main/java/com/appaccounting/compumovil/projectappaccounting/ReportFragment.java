package com.appaccounting.compumovil.projectappaccounting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.appaccounting.compumovil.projectappaccounting.Helpers.DbHelper;
import com.juang.jplot.PlotBarritas;
import com.juang.jplot.PlotPastelito;

public class ReportFragment extends Fragment {
    View view;
    private PlotPastelito pastel;
    private PlotPastelito pastel2;
    private LinearLayout pantalla;
    private LinearLayout pantalla2;
    Context context;
    String totalGastos;
    String totalIngresos;
    DbHelper dbh;
    float fGastos;
    float fIngresos;
    Button btnReport;

    PlotBarritas Columna;
    LinearLayout pantallaIngresos;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);
        context = getActivity().getBaseContext();
        dbh = new DbHelper(context);
        btnReport = view.findViewById(R.id.btnReportG);

        totalGastos = dbh.countTotalDebits().toString();
        totalIngresos = dbh.countTotalEntries().toString();
        fGastos = Float.parseFloat(totalGastos);
        fIngresos = Float.parseFloat(totalIngresos);

        pantalla= (view.findViewById(R.id.pantallaReportGeneral));
        pantalla2= (view.findViewById(R.id.pantallaReportGastos));
        pantallaIngresos= (view.findViewById(R.id.pantallaReportIngresos));

        pastel=new PlotPastelito(context,"Balance General");//puedes usar simplemente "this" en lugar de context
        pastel2=new PlotPastelito(context,"Reporte Ingresos");
        float[] datapoints = {fGastos,fIngresos};
        String[] etiquetas={"Ingresos", "Gastos"};
        pastel.SetDatos(datapoints,etiquetas);
        pastel2.SetDatos(datapoints,etiquetas);

   /*antes de mostrar el grafico en pantalla(LinearLayout) deben de ir todos los ajustes "Set" del grafico.
       Todos los metodos publicos que ayudan a personalizar el grafico se describen cada uno en la siguiente sección */

        pastel.SetHD(true); //ajustamos la calidad hd que suaviza bordes del grafico. por default esta desactivado
        pastel.SetColorDato(1,100,100,100);
        pastel.SetColorDato(2,200,100,100);
        pastel.SetColorFondo(0,0,0);

        pastel2.SetHD(true); //ajustamos la calidad hd que suaviza bordes del grafico. por default esta desactivado
        pastel2.SetColorDato(1,300,300,300);
        pastel2.SetColorDato(2,250,0,0);
        pastel2.SetColorFondo(255,255,255);
        pantalla.addView(pastel);
        pantalla2.addView(pastel2);


        //Reporte ingresos
        String x[]={"Saldo","Ingresos","Gastos","jueves","viernes"};
        double y[]={20,30,44,0,-25};
        Columna=new PlotBarritas(context,"Gráfico de Columnas ","Monto");
        //en context puede colocarse simplemente this
        //personalizacion del grafico
        Columna.Columna(x,y);// OJO x e y DEBEN SER DEL MISMO TAMAÑO O CAUSARA QUE SE CIERRE LA APLICACION.
        Columna.SetHD(true);
        //cambiemos el color del dato 3 o sea "44" rojo=255,verde=0,Azul=0 los ultimos tres enteros son los colores en rgb
        Columna.SetColorPila(2 ,200,0,120);//muestra el tercer dato en color rojo
        //mostrando en pantalla
        pantallaIngresos.removeAllViews();
        pantallaIngresos.addView(Columna);


        btnReport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                verReporteGeneral(v);
            }
        });

        return view;
    }

    /**
     * Método para el botón
     * @param v
     */
    private void verReporteGeneral(View v){
        Intent repActivity = new Intent(context, ReportGeneralActivity.class);
        startActivity(repActivity);
    }

}
