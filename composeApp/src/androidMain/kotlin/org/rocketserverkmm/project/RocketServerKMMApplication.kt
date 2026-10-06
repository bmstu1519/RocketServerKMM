package org.rocketserverkmm.project

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.rocketserverkmm.project.di.initKoin
import org.rocketserverkmm.project.platform.initializeKVault

class RocketServerKMMApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKVault(this)
        initKoin {
            androidContext(this@RocketServerKMMApplication)
        }
    }
}