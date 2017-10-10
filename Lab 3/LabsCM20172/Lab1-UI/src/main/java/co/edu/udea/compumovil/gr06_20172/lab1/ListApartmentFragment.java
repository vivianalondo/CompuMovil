package co.edu.udea.compumovil.gr06_20172.lab1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.Apartment;
import co.edu.udea.compumovil.gr06_20172.lab1.adapter.ApartmentsAdapter;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiClient;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListApartmentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        //Llamado a la api
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Apartment>> call = apiService.getData();
        call.enqueue(new Callback<List<Apartment>>() {
            @Override
            public void onResponse(Call<List<Apartment>> call1, Response<List<Apartment>> response) {

                List<Apartment> apartments = response.body();
                recyclerView.setAdapter(new ApartmentsAdapter(apartments, R.layout.list_item_apartment, getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<Apartment>> call1, Throwable t) {

                Log.e("Error", "Hay un error en el call apartment");
            }
        });
        return recyclerView;
    }

}
