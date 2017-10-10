package co.edu.udea.compumovil.gr06_20172.lab1.rest;

import java.util.List;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.Apartment;
import co.edu.udea.compumovil.gr06_20172.lab1.POJO.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Viviana Londoño on 6/10/2017.
 */

public interface ApiInterface {

    //Obtener lista de apartamentos desde la API
    @GET("apartments")
    Call<List<Apartment>> getData();

    //Obtener un apartamendo por id, desde la API
    @GET("apartments/{id}")
    Call<Apartment> getApartmentDetails(@Path("id") int id);

    //Crear un nuevo apartamento
    @POST("/apartments")
    Call<Apartment> createApartment(@Body Apartment apartment);


    //Obtener un apartamendo por id, desde la API
    @GET("users/{id}")
    Call<User> getUserDetails(@Path("id") int id);

    //Crear un nuevo apartamento
    @POST("/users")
    Call<User> createUser(@Body User user);

}
