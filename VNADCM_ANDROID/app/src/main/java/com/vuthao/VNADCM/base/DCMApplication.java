package com.vuthao.VNADCM.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.vuthao.VNADCM.base.pushnotfication.RegistrationService;

import io.realm.Realm;

public class DCMApplication extends Application implements AppLifeCycleHandler.AppLifeCycleCallback {
    public static Context context;
    private static DCMApplication mInstance;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        AppLifeCycleHandler appLifeCycleHandler = new AppLifeCycleHandler(this);
        registerActivityLifecycleCallbacks(appLifeCycleHandler);
        registerComponentCallbacks(appLifeCycleHandler);

        mInstance = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        context = getApplicationContext();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FirebaseApp.initializeApp(this);
        Realm.init(this);

        Intent i = new Intent(this, RegistrationService.class);
        if (Constants.isInBackground) {
            context.startForegroundService(i);
        } else {
            startForegroundService(i);
        }

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
    }

    public static synchronized DCMApplication getInstance() {
        return mInstance;
    }

    public void trackScreenView(String screenName) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    @Override
    public void onAppBackground() {
        Log.d("LifecycleEvent", "onAppBackground");
    }

    @Override
    public void onAppForeground() {
        Log.d("LifecycleEvent", "onAppForeground");
    }
}
