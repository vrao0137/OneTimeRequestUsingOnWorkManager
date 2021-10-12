package com.example.onetimerequestusingonworkmanager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.onetimerequestusingonworkmanager.Service.ApiClient;
import com.example.onetimerequestusingonworkmanager.Service.ApiInterface;
import com.example.onetimerequestusingonworkmanager.Worker.MyFirstWorker;
import com.example.onetimerequestusingonworkmanager.Worker.MySecondWorker;
import com.example.onetimerequestusingonworkmanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private Context context;
    private OneTimeWorkRequest workRequest1, workRequest2;
    private final String USER_TITLE = "userTitle";
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
                int id = Integer.parseInt(binding.edtUserId.getText().toString());
                Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();

                Data.Builder data = new Data.Builder();
                data.putInt(ID, id);
                workRequest1 = new OneTimeWorkRequest.Builder(MyFirstWorker.class).setInputData(data.build()).setConstraints(constraints).build();
                workRequest2 = new OneTimeWorkRequest.Builder(MySecondWorker.class).build();
                WorkManager.getInstance(context).beginWith(workRequest1).then(workRequest2).enqueue();

                WorkManager.getInstance(context).getWorkInfoByIdLiveData(workRequest1.getId())
                        .observe(MainActivity.this, new Observer<WorkInfo>() {
                            @Override
                            public void onChanged(WorkInfo workInfo) {
                                String userTitle = workInfo.getOutputData().getString(USER_TITLE);
                                Log.e(TAG, "userTitle:- " + userTitle);
                                binding.txtGetTitle.setText(userTitle + "\n");
                            }
                        });
            }
        });


    }
}