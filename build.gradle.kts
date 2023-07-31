plugins {
    id(Plugins.androidApplication) version Versions.gradle apply false
    id(Plugins.androidLibrary) version Versions.gradle apply false
    id(Plugins.kotlinAndroidAGP) version Versions.kotlin apply false
    id(Plugins.hiltAGP) version Versions.hilt apply false
    id(Plugins.leakcanary) version Versions.leakCanary apply false
    id(Plugins.gradleVersions) version Versions.gradleVersions
}