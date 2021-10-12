package com.example.onetimerequestusingonworkmanager.Service;

import com.example.onetimerequestusingonworkmanager.Model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("/todos")
    Call<List<UserModel>> getAllUser();

    @GET("/todos/{id}")
    Call<UserModel> getUser(@Path("id") int id);
}
