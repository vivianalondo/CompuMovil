package co.edu.udea.compumovil.gr06_20172.lab1;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.Apartment;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Viviana Londoño on 6/10/2017.
 */

public interface RestClient {
    @GET("apartments")
    Call<Apartment> getData();
}
