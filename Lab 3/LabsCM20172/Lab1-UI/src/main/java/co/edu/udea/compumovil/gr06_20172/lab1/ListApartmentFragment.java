package co.edu.udea.compumovil.gr06_20172.lab1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.Apartment;
import co.edu.udea.compumovil.gr06_20172.lab1.adapter.ApartmentsAdapter;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiClient;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListApartmentFragment extends Fragment implements SearchView.OnQueryTextListener{
    View view;
    Context context;
    private List<Apartment> apartments;
    private ApartmentsAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        context = getActivity().getApplicationContext();

        //Llamado a la api
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Apartment>> call = apiService.getData();
        call.enqueue(new Callback<List<Apartment>>() {
            @Override
            public void onResponse(Call<List<Apartment>> call1, Response<List<Apartment>> response) {
                System.out.println("En el onresponse, la respuesta es: "+response.message());
                apartments = response.body();
                mAdapter = new ApartmentsAdapter(apartments, R.layout.list_item_apartment, getContext());

                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setFilterTouchesWhenObscured(true);
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<List<Apartment>> call1, Throwable t) {

                Log.e("Error", "Hay un error en el call apartment");
            }
        });
        return recyclerView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        menuInflater.inflate(R.menu.search_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        //return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        query = query.toLowerCase();
        Log.d("LOGT", query);

            final List<Apartment> filteredModelList = new ArrayList<>();
            for (Apartment model : apartments) {
                final String text = model.getName().toLowerCase();

                if (text.contains(query)) {
                    filteredModelList.add(model);
                }
            }
            mAdapter.animateTo(filteredModelList);
            recyclerView.scrollToPosition(0);
            return true;

    }


       //Toast.makeText(context, "No se encontraron resultados", Toast.LENGTH_LONG).show();

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        System.out.println("EN el onquerytextsubmit");
        return false;
    }

}
