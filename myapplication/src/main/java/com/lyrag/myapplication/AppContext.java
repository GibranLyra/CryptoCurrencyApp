package com.lyrag.myapplication;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.jakewharton.threetenabp.AndroidThreeTen;

import gibran.com.br.bitcoinmarketservice.BitCoinMarketModule;
import gibran.com.br.bitcoinmarketservice.LoggingInterceptor;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by lyrag on 12/10/2017.
 */

public class AppContext extends Application {

    private static AppContext instance;

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MultiDex.install(this);
        initializeTimezone();
        initializeTimber();
        initializeApiModules();
        Fabric.with(this, new Crashlytics());
    }

    private void initializeTimezone() {
        AndroidThreeTen.init(this);
    }

    private void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initializeApiModules() {
        BitCoinMarketModule.setRetrofit(LoggingInterceptor.Level.BASIC);
    }

}
