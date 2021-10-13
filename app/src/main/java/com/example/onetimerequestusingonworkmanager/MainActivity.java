package com.example.onetimerequestusingonworkmanager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.onetimerequestusingonworkmanager.Comman.Constants;
import com.example.onetimerequestusingonworkmanager.Model.UserModel;
import com.example.onetimerequestusingonworkmanager.Service.ApiClient;
import com.example.onetimerequestusingonworkmanager.Service.ApiInterface;
import com.example.onetimerequestusingonworkmanager.Worker.MyFirstWorker;
import com.example.onetimerequestusingonworkmanager.Worker.MySecondWorker;
import com.example.onetimerequestusingonworkmanager.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private Context context;
    private OneTimeWorkRequest workRequest1, workRequest2;
    private final String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialize();
    }


    private void initialize() {
        context = this;

        binding.btnGetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  getUser();

                int id = Integer.parseInt(binding.edtUserId.getText().toString());
                Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();

                Data.Builder data = new Data.Builder();
                data.putInt(ID, id);

                workRequest1 = new OneTimeWorkRequest.Builder(MyFirstWorker.class).setInputData(data.build())
                        .addTag(TAG)
                        .setConstraints(constraints).build();
                workRequest2 = new OneTimeWorkRequest.Builder(MySecondWorker.class)
                        .setConstraints(constraints).build();
                WorkManager workManager = WorkManager.getInstance(context);
                workManager.beginWith(workRequest1).then(workRequest2).enqueue();

                //If there is one pending, you can choose to let it run or replace it with your new work.
                workManager.beginUniqueWork(
                        TAG,
                        ExistingWorkPolicy.REPLACE,
                        workRequest1
                ).enqueue();


                /*WorkManager.getInstance(context).getWorkInfoByIdLiveData(workRequest1.getId())
                        .observe(MainActivity.this, new Observer<WorkInfo>() {
                            @Override
                            public void onChanged(WorkInfo workInfo) {
                                String userTitle = workInfo.getOutputData().getString(USER_TITLE);
                                Log.e(TAG, "userTitle:- " + userTitle);
                                binding.txtGetTitle.setText(userTitle + "\n");
                            }
                        });*/
            }
        });

    }

    /*private void getUser(){
        Call<UserModel> getUser = apiInterface.getPost(7);
        getUser.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.code() == 200) {
                    Log.e(TAG,"1onResponse:- "+response.body().getTitle());
                }else {
                    Log.e(TAG,"2onResponse:- ");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }*/
}