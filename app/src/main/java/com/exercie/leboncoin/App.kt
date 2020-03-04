package com.exercie.leboncoin

import android.app.Application
import com.exercie.data.initDataDagger

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initAppDagger(this)
        initDataDagger(this)
    }
}
