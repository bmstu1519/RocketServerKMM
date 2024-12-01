package org.rocketserverkmm.project

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.rocketserverkmm.project.di.initKoin

class RocketServerKMMApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@RocketServerKMMApplication)
        }
    }
}