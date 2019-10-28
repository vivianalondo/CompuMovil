package co.edu.udea.compumovil.gr06_20172.lab1.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Viviana Londoño on 6/10/2017.
 */

public class ApiClient {

    public static final String BASE_URL = "http://my-rails-api-compumovil.herokuapp.com";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
