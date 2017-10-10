package co.edu.udea.compumovil.gr06_20172.lab1;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.Apartment;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiClient;
import co.edu.udea.compumovil.gr06_20172.lab1.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    DbHelper dbHelper;
    SQLiteDatabase db;
    byte[] avat = new byte[0];

    public static final String EXTRA_NOMBRE = "name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page

        String name = getIntent().getStringExtra(EXTRA_NOMBRE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call <Apartment> call = apiService.getApartmentDetails(Integer.parseInt(name));

        call.enqueue(new Callback<Apartment>() {
            @Override
            public void onResponse(Call<Apartment> call, Response<Apartment> response) {
                Apartment apartment = response.body();
                TextView placeType = (TextView) findViewById(R.id.place_type);
                placeType.setText(apartment.getApType());
                TextView placeDetail = (TextView) findViewById(R.id.place_detail);
                placeDetail.setText(apartment.getDescription());

                TextView placeArea = (TextView) findViewById(R.id.place_area);
                placeArea.setText(apartment.getArea().toString());

                TextView placeLocation =  (TextView) findViewById(R.id.place_location);
                placeLocation.setText(apartment.getAddress());

                TextView placeValues =  (TextView) findViewById(R.id.place_value);
                placeValues.setText(apartment.getValue().toString());

                ImageView placePictures = (ImageView) findViewById(R.id.image);
                if (apartment.getPicture()!=null){

                    avat = apartment.getPicture().getBytes();
                    byte[] decodedString = Base64.decode(avat, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    //Bitmap bitmap = BitmapFactory.decodeByteArray(avat, 0, avat.length);
                    placePictures.setImageBitmap(decodedByte);
                }
                else{
                    placePictures.setImageResource(R.drawable.iconoapartamento);
                }
                /*byte[] auxx=search.getBlob(7);
                Bitmap pict= BitmapFactory.decodeByteArray(auxx, 0, (auxx).length);
                placePictures.setImageBitmap(pict);*/

            }

            @Override
            public void onFailure(Call<Apartment> call, Throwable t) {

            }
        });


    }
}
