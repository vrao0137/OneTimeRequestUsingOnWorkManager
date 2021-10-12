package com.example.onetimerequestusingonworkmanager.Service;

import com.example.onetimerequestusingonworkmanager.Comman.Constants;
import com.example.onetimerequestusingonworkmanager.Model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET(Constants.USERS)
    Call<List<UserModel>> getAllUser();

    @GET(Constants.POSTS)
    Call<UserModel> getPost(@Path(Constants.ID) int id);

    @GET(Constants.TODOS)
    Call<UserModel> getTodos(@Path(Constants.ID) int id);

    @GET(Constants.PHOTOS)
    Call<UserModel> getPhotos(@Path(Constants.ID) int id);

    @GET(Constants.ALBUMS)
    Call<UserModel> getAlbums(@Path(Constants.ID) int id);

    @GET(Constants.COMMENTS)
    Call<UserModel> getComments(@Path(Constants.ID) int id);
}
