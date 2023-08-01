package com.sadri.mapbox.logger

import com.sadri.mapbox.BuildConfig
import timber.log.Timber

object Logger {
  fun init() {
    Timber.plant(
      if (BuildConfig.DEBUG) {
        Timber.DebugTree()
      } else {
        ProductionLogTree()
      }
    )
  }
}