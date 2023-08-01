package com.sadri.mapbox.core.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
  val ui: CoroutineDispatcher
  val io: CoroutineDispatcher
  val default: CoroutineDispatcher
}