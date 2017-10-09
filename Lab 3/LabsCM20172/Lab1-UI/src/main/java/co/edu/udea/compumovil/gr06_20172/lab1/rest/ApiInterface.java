package co.edu.udea.compumovil.gr06_20172.lab1.rest;

import java.util.List;

import co.edu.udea.compumovil.gr06_20172.lab1.POJO.Apartment;
import co.edu.udea.compumovil.gr06_20172.lab1.POJO.ApartmentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    /*@POST("apartments")
    Call<Apartment> addApartment(@Path("object") JSONObject object);*/

    @POST("apartments")
    @FormUrlEncoded
    Call<Apartment> savePost(@Field("user_id") int user_id,
                        @Field("name") String name,
                             @Field("ap_type") String ap_type,
                             @Field("description") String description,
                             @Field("area") int area,
                             @Field("value") int value,
                             @Field("address") String address);

    @POST("/apartments")
    Call<Apartment> createApartment(@Body Apartment apartment);

}
