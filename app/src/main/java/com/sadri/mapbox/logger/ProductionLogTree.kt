package com.sadri.mapbox.logger

import android.util.Log
import timber.log.Timber

class ProductionLogTree : Timber.Tree() {
  override fun isLoggable(tag: String?, priority: Int): Boolean {
    return priority > Log.DEBUG
  }

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    // no-op
  }
}