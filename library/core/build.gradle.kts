plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinParcelize)
  id(Plugins.kotlinKapt)
  id(Plugins.hilt)
  id(Plugins.composeCompilerHtmlReport) version Versions.composeCompilerHtmlReport
}

android {
  namespace = "com.sadri.mapbox.core"
  compileSdk = Android.compileSdkVersion

  defaultConfig {
    minSdk = Android.minSdkVersion
    targetSdk = Android.targetSdkVersion
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_11.toString()
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion
  }
}

dependencies {
  implementation(Libs.Common.kotlinLib)
  implementation(Libs.Common.coroutines)
  implementation(Libs.Common.coroutinesAndroid)

  implementation(Libs.Common.coreKtx)
  implementation(Libs.Common.viewModelKtx)
  implementation(Libs.Common.liveDataKtx)
  implementation(Libs.Common.composeActivity)
  implementation(Libs.Common.composeUi)
  implementation(Libs.Common.composeCompiler)
  implementation(Libs.Common.composeRuntime)

  implementation(Libs.Common.hilt)
  kapt(Libs.Common.hiltCompiler)
}