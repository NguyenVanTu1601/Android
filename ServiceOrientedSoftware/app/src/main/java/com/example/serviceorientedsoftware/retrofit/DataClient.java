package com.example.serviceorientedsoftware.retrofit;

import com.example.serviceorientedsoftware.model.Order;
import com.example.serviceorientedsoftware.model.Pet;
import com.example.serviceorientedsoftware.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataClient {

    // get pet by type
//    @Headers({
//            "Content-TypePet: application/json"
//    })
//    @GET("pets/{type}")
//    Call<List<Pet>> getListPetByType(@Path("type") String type);

    @Headers({
            "Content-TypePet: application/json"
    })
    @GET("products")
    Call<List<Pet>> getListPetByType(@Query("type") String type);

    // get all pet
    @Headers({
            "Content-TypePet: application/json"
    })
    @GET("products/")
    Call<List<Pet>> getListPet();

//    // get all pet
//    @Headers({
//            "Content-TypePet: application/json"
//    })
//    @GET("pets/")
//    Call<List<Pet>> getListPet();

    // Post pet by user
    @Headers({
            "Content-TypePet: application/json"
    })
    @POST("pets")
    Call<Pet> savePet(@Body Pet pet);

    // get pet by id
    @Headers({
            "Content-TypePet: application/json"
    })
    @GET("pets/id/{id}")
    Call<Pet> getPetById(@Path("id") String id);


    // create order
    @Headers({
            "Content-TypePet: application/json"
    })
    @POST("orders")
    Call<Order> createOrder(@Body Order order);

    // get Order by id
    @GET("orders/{id}")
    Call<Order> getOrderByUserId(@Path("id") String userId);


    // create user
    @POST("users")
    Call<User> createUser(@Body User user);

    // get user by id
    @GET("users/{id}")
    Call<User> getUserById(@Path("id") String userId);
}
