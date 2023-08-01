package com.sadri.mapbox

import android.app.Application
import com.sadri.mapbox.logger.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {

  override fun onCreate() {
    super.onCreate()

    Logger.init()
  }

}