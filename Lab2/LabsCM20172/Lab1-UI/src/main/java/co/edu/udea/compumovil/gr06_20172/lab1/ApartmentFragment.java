package co.edu.udea.compumovil.gr06_20172.lab1;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApartmentFragment extends ListFragment {
    DbHelper dbHelper;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final int REQUEST_CODE=1;
    SQLiteDatabase db;
    Button mapa;
    View view;
    private EditText buscar;
    private Button btnBuscar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        dbHelper =new DbHelper(getActivity().getBaseContext());
        view = inflater.inflate(R.layout.fragment_apartment,container,false);
        buscar = (EditText) view.findViewById(R.id.searchApartment);
        btnBuscar = (Button)view.findViewById(R.id.btnSearch);
        //btnBuscar.setVisibility(View.VISIBLE);
        //buscar.setVisibility(View.VISIBLE);

        ListarApartamentos();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public void ListarApartamentos(){//operacion para listar sitios, muestra en el activity los sitios guardados
        ArrayList<String> name = new ArrayList(),type = new ArrayList(), desc = new ArrayList(),area = new ArrayList(),address= new ArrayList(), value = new ArrayList(), ids = new ArrayList();
        ArrayList picture = new ArrayList();
        boolean control=false;
        db= dbHelper.getReadableDatabase();
        Cursor test=db.rawQuery("select * from "+StatusContract.TABLE_APARTMENT+" order by "+ StatusContract.Column_Apartment.NAME, null);
        if (test.moveToFirst()) {
            do{
                ids.add(test.getString(0));
                name.add(test.getString(1));
                type.add(test.getString(2));
                desc.add(test.getString(3));
                area.add(test.getString(4));
                address.add(test.getString(5));
                value.add(test.getString(6));
                picture.add(test.getBlob(7));
            }while(test.moveToNext());
            control=true;
        } else{
            Toast.makeText(getActivity().getBaseContext(),"Sin Apartamentos",Toast.LENGTH_LONG).show();
        }
        db.close();
        if(control) {
            ArrayList aList=new ArrayList();
            for (int i = 0; i < ids.size(); i++) {
                HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("name", "Site: " + name.get(i));
                hm.put("type","Type: "+type.get(i));
                hm.put("description", "Descriptión : " + desc.get(i));
                hm.put("area", "Area : " + area.get(i));
                hm.put("address", "Address : " + address.get(i));
                hm.put("value","Value: "+value.get(i));
                hm.put("picture", BitmapFactory.decodeByteArray((byte[]) picture.get(i), 0, ((byte[]) picture.get(i)).length));
                aList.add(hm);
            }
            String from[];
            int to[];
            from = new String[]{"name","type","description", "area", "address","value", "picture"};
            to = new int[]{R.id.napa, R.id.tipo, R.id.desc, R.id.area, R.id.address,R.id.valor, R.id.picture};
            ExtendedSimpleAdapter adapter = new ExtendedSimpleAdapter(getActivity().getBaseContext(), aList, R.layout.fragment_apartment, from, to);
            setListAdapter(adapter);
        }
    }
    public void searchApartments(){//operacion para listar sitios, muestra en el activity los sitios guardados
        ArrayList<String> name = new ArrayList(),type = new ArrayList(), desc = new ArrayList(),area = new ArrayList(),address= new ArrayList(), value = new ArrayList(), ids = new ArrayList();
        ArrayList picture = new ArrayList();
        boolean control=false;
        db= dbHelper.getReadableDatabase();
        buscar = (EditText) view.findViewById(R.id.searchApartment);
        String[] buscarApar = new String[]{buscar.toString()};
        Cursor test=db.rawQuery("select * from "+StatusContract.TABLE_APARTMENT+" where "+ StatusContract.Column_Apartment.NAME+"=?", buscarApar);
        if (test.moveToFirst()) {
            do{
                ids.add(test.getString(0));
                name.add(test.getString(1));
                type.add(test.getString(2));
                desc.add(test.getString(3));
                area.add(test.getString(4));
                address.add(test.getString(5));
                value.add(test.getString(6));
                picture.add(test.getBlob(7));
            }while(test.moveToNext());
            control=true;
        } else{
            Toast.makeText(getActivity().getBaseContext(),"Sin Apartamentos",Toast.LENGTH_LONG).show();
        }
        db.close();
        if(control) {
            ArrayList aList=new ArrayList();
            for (int i = 0; i < ids.size(); i++) {
                HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("name", "Site: " + name.get(i));
                hm.put("type","Type: "+type.get(i));
                hm.put("description", "Descriptión : " + desc.get(i));
                hm.put("area", "Area : " + area.get(i));
                hm.put("address", "Address : " + address.get(i));
                hm.put("value","Value: "+value.get(i));
                hm.put("picture", BitmapFactory.decodeByteArray((byte[]) picture.get(i), 0, ((byte[]) picture.get(i)).length));
                aList.add(hm);
            }
            String from[];
            int to[];
            from = new String[]{"name","type","description", "area", "address","value", "picture"};
            to = new int[]{R.id.napa, R.id.tipo, R.id.desc, R.id.area, R.id.address,R.id.valor, R.id.picture};
            ExtendedSimpleAdapter adapter = new ExtendedSimpleAdapter(getActivity().getBaseContext(), aList, R.layout.fragment_apartment, from, to);
            setListAdapter(adapter);
        }
    }
}
