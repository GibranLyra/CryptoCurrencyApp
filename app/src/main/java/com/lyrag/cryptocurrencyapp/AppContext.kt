package com.lyrag.cryptocurrencyapp

import android.app.Application
import android.support.multidex.MultiDex

/**
 * Created by lyrag on 12/10/2017.
 */

class AppContext : Application() {

    private var instance: AppContext? = null

    fun getInstance(): AppContext? {
        return instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
    }
}