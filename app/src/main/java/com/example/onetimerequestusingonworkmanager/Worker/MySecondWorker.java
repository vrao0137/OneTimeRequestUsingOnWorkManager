package com.example.onetimerequestusingonworkmanager.Worker;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.onetimerequestusingonworkmanager.Comman.Constants;
import com.example.onetimerequestusingonworkmanager.Comman.Notifications;

public class MySecondWorker extends Worker {
    private final String TAG = MySecondWorker.class.getSimpleName();
    private Context mContext;
    private Notifications notifications = new Notifications();

    public MySecondWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
    }

    @Override
    public Result doWork() {
        String getTitle = getInputData().getString(Constants.USER_TITLE);
        Log.e(TAG,"userTitle:- "+ getTitle);
        notifications.createNotification(Constants.SECOND_WORKMANAGER, getTitle, mContext);
        return Result.success();
    }

}
