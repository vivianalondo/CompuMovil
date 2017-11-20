package com.appaccounting.compumovil.projectappaccounting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TransactionFragment extends Fragment {
    private FloatingActionButton fabIngre;
    private FloatingActionButton fabGast;
    private Button btnIngresos;
    private Button btnGastos;
    View view;

    public TransactionFragment() {//activity que enseña información

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_transaction, container, false);

        view=inflater.inflate(R.layout.fragment_transaction, container, false);
        fabIngre=(FloatingActionButton)view.findViewById(R.id.fab_ingresos);
        fabGast=(FloatingActionButton)view.findViewById(R.id.fab_gastos);
        btnIngresos = (Button) view.findViewById(R.id.mas_ingre);
        btnGastos = (Button) view.findViewById(R.id.mas_gastos);

        //hago clic y se abre el 2
        fabIngre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), GastosActivity.class);
                getActivity().startActivity(intent);
            }
        });


        btnGastos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), GastosActivity.class);
                getActivity().startActivity(intent);
            }
        });

        btnIngresos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), IngresosActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;

    }
}
