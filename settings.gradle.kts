import java.net.URI

pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven {
      url = URI("https://api.mapbox.com/downloads/v2/releases/maven")
      authentication {
        create<BasicAuthentication>("basic")
      }
      credentials {
        // Do not change the username below.
        // This should always be `mapbox` (not your username).
        username = "mapbox"
        // Use the secret token you stored in gradle.properties as the password
        password = System.getenv("MAPBOX_DOWNLOADS_TOKEN")
      }
    }
  }
}
rootProject.name = "mapbox"

// App
include(":app")

// Feature
include(
  ":feature:map"
)

// Library
include(
  ":library:designsystem",
  ":library:core",
  ":library:location",
  ":library:navigation"
)