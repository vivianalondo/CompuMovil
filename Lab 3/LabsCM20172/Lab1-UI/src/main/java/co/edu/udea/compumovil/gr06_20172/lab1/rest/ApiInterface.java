package co.edu.udea.compumovil.gr06_20172.lab1.rest;

import java.util.List;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.Apartment;
import co.edu.udea.compumovil.gr06_20172.lab1.POJO.ApartmentResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Viviana Londoño on 6/10/2017.
 */

public interface ApiInterface {

    @GET("apartments")
    Call<List<ApartmentResponse>> getApartments();

    @GET("apartments")
    Call<List<Apartment>> getData();

    /*@GET("apartments/{id}")
    Call<ApartmentResponse> getApartmentDetails(@Path("id") int id);*/

    @GET("apartments/{id}")
    Call<Apartment> getApartmentDetails(@Path("id") int id);

}
