package com.example.onetimerequestusingonworkmanager.Worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.onetimerequestusingonworkmanager.Comman.Constants;
import com.example.onetimerequestusingonworkmanager.Comman.Notifications;
import com.example.onetimerequestusingonworkmanager.Model.UserModel;
import com.example.onetimerequestusingonworkmanager.R;
import com.example.onetimerequestusingonworkmanager.Service.ApiClient;
import com.example.onetimerequestusingonworkmanager.Service.ApiInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class MyFirstWorker extends Worker {
    private final String TAG = MyFirstWorker.class.getSimpleName();
    private Context mContext;
    private ApiInterface apiInterface;
    private Notifications notifications = new Notifications();

    private boolean onResponse = false;

    public MyFirstWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
    }

    @Override
    public Result doWork() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        int userId = getInputData().getInt(Constants.ID, 0);
        Log.e(TAG, "UserID:- " + userId);

        Data.Builder outputDataBuilder = new Data.Builder();

        Call<UserModel> getUser = apiInterface.getPost(userId);
        try {
            Response<UserModel> response = getUser.execute();
            if (response.code() == 404) {
                outputDataBuilder.putString(Constants.USER_TITLE, Constants.DATA_NOT_FOUND);
                onResponse = false;
                notifications.createNotification(Constants.FIRST_WORKMANAGER, Constants.DATA_NOT_FOUND, mContext);
            }else if (response.code() == 200){
                UserModel data = response.body();
                onResponse = true;
                outputDataBuilder.putString(Constants.USER_TITLE, data.getTitle());
                Log.e(TAG,"getTitle:- "+data.getTitle());
            } else {
                return Result.retry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Data outputData = outputDataBuilder.build();
        Log.e(TAG, "outputData:- " + outputData);

        if (onResponse){
            return Result.success(outputData);
        }else {
            return Result.failure(outputData);
        }

    }

   /* private void getUser(){
        Call<UserModel> getUser = apiInterface.getUser(7);
        getUser.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                outputDataBuilder.putString("userTitle", response.body().getTitle());
                Log.e(TAG,"outputDataBuilder:- "+outputDataBuilder);
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e(TAG, "onFailure:- " + t.getLocalizedMessage());
                createNotificationChannel();
            }
        });
    }*/

}
