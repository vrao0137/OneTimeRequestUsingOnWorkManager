package com.example.onetimerequestusingonworkmanager.Worker;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.onetimerequestusingonworkmanager.Comman.Notifications;

public class MySecondWorker extends Worker {
    Notifications notifications = new Notifications();
    Context mContext;

    public MySecondWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
    }

    @Override
    public Result doWork() {
        String getTitle = getInputData().getString("userTitle");
        notifications.createNotification("This is Second WorkManager", getTitle, mContext);
        return Result.success();
    }

}
