object Libs {

    object Common {
        // Kotlin
        const val kotlinLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

        // Mapbox
        const val mapBox = "com.mapbox.maps:android:${Versions.mapBox}"
        const val mapBoxNavigation = "com.mapbox.navigation:android:${Versions.mapBoxNavigation}"
        const val gmsLocation = "com.google.android.gms:play-services-location:${Versions.gmsLocation}"

        // Jetpack
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleKtx}"
        const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleKtx}"
        const val annotation = "androidx.annotation:annotation:${Versions.annotation}"
        const val paging = "androidx.paging:paging-compose:${Versions.paging}"
        const val dataStore = "androidx.datastore:datastore-preferences:${Versions.prefrencesDataStore}"
        const val performance = "androidx.metrics:metrics-performance:${Versions.performance}"
        const val tracing = "androidx.tracing:tracing-ktx:${Versions.tracing}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
        const val navigationKtx = "androidx.navigation:navigation-common-ktx:${Versions.navigation}"

        // Material
        const val material = "com.google.android.material:material:${Versions.material}"

        // Compose
        const val composeActivity = "androidx.activity:activity-compose:${Versions.compose}"
        const val composeLifecycle = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycleKtxAlpha}"
        const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
        const val composeCompiler = "androidx.compose.compiler:compiler:${Versions.compose}"
        const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val composeMaterial3 = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
        const val composeMaterial = "androidx.compose.material:material:${Versions.composeMaterial}"
        const val material3WindowSize = "androidx.compose.material3:material3-window-size-class:${Versions.material3WindowSize}"
        const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"

        // Hilt
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val androidxHiltCompiler = "androidx.hilt:hilt-compiler:${Versions.androidxHilt}"
        const val hiltBinder = "com.paulrybitskyi:hilt-binder:${Versions.hiltBinder}"
        const val hiltBinderCompiler = "com.paulrybitskyi:hilt-binder-compiler:${Versions.hiltBinder}"
        const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"

        // Network
        const val chucker = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
        const val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
        const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

        // Common
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
        const val autoValue = "com.google.auto.value:auto-value-annotations:${Versions.autoValue}"
        const val autoValueCompiler = "com.google.auto.value:auto-value:${Versions.autoValue}"
        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"
        const val imageLoader = "com.github.skydoves:landscapist-glide:${Versions.imageLoader}"
        const val imageLoaderPlaceHolder = "com.github.skydoves:landscapist-placeholder:${Versions.imageLoader}"
        const val pager = "com.google.accompanist:accompanist-pager:${Versions.pager}"
        const val pagerIndicator = "com.google.accompanist:accompanist-pager-indicators:${Versions.pager}"
        const val instagramPagerIndicator = "com.github.talhatek:pager_indicator:${Versions.pagerIndicator}"
        const val collectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:${Versions.collectionsImmutable}"
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    }

    object Testing {

        const val junit = "junit:junit:${Versions.junit}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
        const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlin}"
        const val testRunner = "androidx.test:runner:${Versions.testRunner}"
        const val testExtJunit = "androidx.test.ext:junit:${Versions.testExt}"
        const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    }
}
