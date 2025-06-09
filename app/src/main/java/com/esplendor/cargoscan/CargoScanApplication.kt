package com.esplendor.cargoscan

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CargoScanApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
