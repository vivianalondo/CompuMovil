package co.edu.udea.compumovil.gr06_20172.lab1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.Apartment;
import co.edu.udea.compumovil.gr06_20172.lab1.adapter.ApartmentsAdapter;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiClient;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApartmentsActivity extends AppCompatActivity {

    private static final String TAG = ApartmentsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.apartments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<List<Apartment>> call1 = apiService.getData();



        call1.enqueue(new Callback<List<Apartment>>() {
            @Override
            public void onResponse(Call<List<Apartment>> call1, Response<List<Apartment>> response) {
                Log.d("Tago", "En el onresponse de Apartment");
                System.out.println("Respuesta: "+response.body().toString());
                String nombre = response.toString();
                Log.d("Tago", nombre);
                List<Apartment> apartments = response.body();
                recyclerView.setAdapter(new ApartmentsAdapter(apartments, R.layout.list_item_apartment, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Apartment>> call1, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Log.e("Error", "Hay un error en el call apartment");
            }
        });
    }
}
