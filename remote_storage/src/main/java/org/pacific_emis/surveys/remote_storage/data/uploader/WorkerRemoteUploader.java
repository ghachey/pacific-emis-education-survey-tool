package org.pacific_emis.surveys.remote_storage.data.uploader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.LongSparseArray;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class WorkerRemoteUploader implements RemoteUploader {

    private static final String TAG = WorkerRemoteUploader.class.getName();

    private static final long ANSWER_UPDATE_TIMEOUT_MINUTES = 1;

    private final Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build();

    private final LongSparseArray<UUID> currentWorkers = new LongSparseArray<>();

    private final PublishSubject<Long> publishSubject = PublishSubject.create();

    private final Context appContext;

    public WorkerRemoteUploader(Context appContext) {
        this.appContext = appContext;
        setupPublisher();
    }

    @SuppressLint("CheckResult")
    private void setupPublisher() {
        publishSubject
                .throttleLast(ANSWER_UPDATE_TIMEOUT_MINUTES, TimeUnit.MINUTES)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(this::enqueueWorker, throwable -> Log.e(TAG, "Publisher error : ", throwable));
    }

    private void enqueueWorker(long passingId) {
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setConstraints(constraints)
                .setInputData(new Data.Builder()
                        .putLong(UploadWorker.DATA_PASSING_ID, passingId)
                        .build())
                .build();
        WorkManager.getInstance(appContext).enqueue(workRequest);
        currentWorkers.put(passingId, workRequest.getId());
    }


    @Override
    public void scheduleUploading(long passingId) {
        UUID foundedUuid = currentWorkers.get(passingId);
        if (foundedUuid != null) {
            WorkManager.getInstance(appContext).cancelWorkById(foundedUuid);
        }

        publishSubject.onNext(passingId);
    }
}
