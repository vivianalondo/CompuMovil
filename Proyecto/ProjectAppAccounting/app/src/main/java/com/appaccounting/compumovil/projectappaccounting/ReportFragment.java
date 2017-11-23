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
import com.appaccounting.compumovil.projectappaccounting.Pojo.Debit;
import com.appaccounting.compumovil.projectappaccounting.Pojo.Entrie;
import com.juang.jplot.PlotBarritas;
import com.juang.jplot.PlotPastelito;

import java.util.ArrayList;

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

        totalGastos = dbh.countTotalDebits().toString();
        totalIngresos = dbh.countTotalEntries().toString();
        fGastos = Float.parseFloat(totalGastos);
        fIngresos = Float.parseFloat(totalIngresos);

        //Generar reporte general
        generateReportGeneral();

        //Reporte gastos
        generarReporteGastos();

        //generarReporteIngresos
        generarReporteIngresos();
        /*pantalla2= (view.findViewById(R.id.pantallaReportGastos));



        pastel2=new PlotPastelito(context,"Reporte Ingresos");
        float[] datapoints = {fGastos,fIngresos};
        String[] etiquetas={"Ingresos", "Gastos"};

        pastel2.SetDatos(datapoints,etiquetas);


        pastel2.SetHD(true); //ajustamos la calidad hd que suaviza bordes del grafico. por default esta desactivado
        pastel2.SetColorDato(1,300,300,300);
        pastel2.SetColorDato(2,250,0,0);
        pastel2.SetColorFondo(224,224,224);
        pantalla2.addView(pastel2);*/


        //Reporte ingresos
        /*String x[]={"Saldo","Ingresos","Gastos","jueves","viernes"};
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
        pantallaIngresos.addView(Columna);*/


        /*btnReport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                verReporteGeneral(v);
            }
        });*/

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

    private void generateReportGeneral(){
        pantalla= (view.findViewById(R.id.pantallaReportGeneral));
        pastel=new PlotPastelito(context,"Balance General");//puedes usar simplemente "this" en lugar de context

        float[] datapoints = {fIngresos, fGastos};
        String[] etiquetas={"Ingresos", "Gastos"};

        pastel.SetDatos(datapoints,etiquetas);
        pastel.SetHD(true); //ajustamos la calidad hd que suaviza bordes del grafico. por default esta desactivado

        /*antes de mostrar el grafico en pantalla(LinearLayout) deben de ir todos los ajustes "Set" del grafico.
        Todos los metodos publicos que ayudan a personalizar el grafico se describen cada uno en la siguiente sección */
        pastel.SetColorDato(1,0,255,0);
        pastel.SetColorDato(2,255,0,0);
        pastel.SetColorFondo(224,224,224);
        pantalla.addView(pastel);
    }

    private void generarReporteGastos(){
        pantallaIngresos= (view.findViewById(R.id.pantallaReportIngresos));
        ArrayList<Debit> arrayListDebits;
        Debit debit;
        //int i = 0;
        double y[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        String x[]= getActivity().getResources().getStringArray(R.array.categories_debits_array);

        if(dbh.hayDebits()){
            for (int i = 1; i < 15; i++){
                System.out.println("número de gasto "+i);
                debit = dbh.getDebitByCategory(String.valueOf(i));
                System.out.println("EL débito es "+debit);
                if (debit.getAmount()==null){
                    y[i-1]= 0;
                }else{
                    System.out.println("El monto del gasto es "+debit.getAmount());
                    y[i-1]= debit.getAmount();
                }
            }
        }

        Columna=new PlotBarritas(context,"Reporte de Gastos ","Monto");
        //en context puede colocarse simplemente this
        //personalizacion del grafico
        Columna.Columna(x,y);// OJO x e y DEBEN SER DEL MISMO TAMAÑO O CAUSARA QUE SE CIERRE LA APLICACION.
        Columna.SetHD(true);
        //cambiemos el color del dato 3 o sea "44" rojo=255,verde=0,Azul=0 los ultimos tres enteros son los colores en rgb
        Columna.SetColorPila(2 ,200,0,120);//muestra el tercer dato en color rojo
        //mostrando en pantalla
        Columna.SetColorFondo(224,224,224,0);
        //pantallaIngresos.removeAllViews();
        pantallaIngresos.addView(Columna);

    }

    private void generarReporteIngresos(){
        pantalla2= (view.findViewById(R.id.pantallaReportGastos));
        ArrayList<Entrie> arrayListEntries;
        Entrie entrie;
        //int i = 0;
        double y[] = {0,0,0};
        String x[]= getActivity().getResources().getStringArray(R.array.categories_entries_array);

        if(dbh.hayEntries()){
            for (int i = 1; i < 4; i++){
                System.out.println("número de gasto "+i);
                entrie = dbh.getEntrieByCategory(String.valueOf(i));
                System.out.println("EL débito es "+entrie);
                if (entrie.getAmount()==null){
                    y[i-1]= 0;
                }else{
                    y[i-1]= entrie.getAmount();
                }
            }
        }

        Columna=new PlotBarritas(context,"Reporte de Ingresos ","Monto");
        //en context puede colocarse simplemente this
        //personalizacion del grafico
        Columna.Columna(x,y);// OJO x e y DEBEN SER DEL MISMO TAMAÑO O CAUSARA QUE SE CIERRE LA APLICACION.
        Columna.SetHD(true);
        //cambiemos el color del dato 3 o sea "44" rojo=255,verde=0,Azul=0 los ultimos tres enteros son los colores en rgb
        Columna.SetColorPila(2 ,200,0,120);//muestra el tercer dato en color rojo
        //mostrando en pantalla
        Columna.SetColorFondo(224,224,224,0);
        //pantallaIngresos.removeAllViews();
        pantalla2.addView(Columna);

    }


}
