package com.sadri.mapbox.map

import android.graphics.Bitmap
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.core.graphics.drawable.toBitmap
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CoordinateBounds
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapLongClickListener

@Composable
fun MapBoxMap(
  modifier: Modifier = Modifier,
  point: Point?,
  destinationPoint: Point?,
  onLongClick: (Point) -> Unit
) {
  val context = LocalContext.current
  val marker = remember(context) {
    Bitmap.createScaledBitmap(
      AppCompatResources.getDrawable(context, R.drawable.ic_marker)!!.toBitmap(),
      MARKER_SIZE,
      MARKER_SIZE,
      true
    )
  }
  val destinationMarker = remember(context) {
    Bitmap.createScaledBitmap(
      AppCompatResources.getDrawable(context, R.drawable.ic_marker_destination)!!.toBitmap(),
      MARKER_SIZE,
      MARKER_SIZE,
      true
    )
  }


  var pointAnnotationManager: PointAnnotationManager? by remember {
    mutableStateOf(null)
  }
  var destinationPointAnnotationManager: PointAnnotationManager? by remember {
    mutableStateOf(null)
  }
  AndroidView(
    factory = {
      MapView(it).also { mapView ->
        mapView.getMapboxMap().loadStyleUri(Style.TRAFFIC_DAY)
        val annotationApi = mapView.annotations
        pointAnnotationManager = annotationApi.createPointAnnotationManager()
        destinationPointAnnotationManager = annotationApi.createPointAnnotationManager()

        mapView.getMapboxMap().addOnMapLongClickListener { point ->
          onLongClick.invoke(point)
          true
        }
      }
    },
    update = { mapView ->
      if (point != null) {
        pointAnnotationManager?.let {
          it.deleteAll()
          val pointAnnotationOptions = PointAnnotationOptions()
            .withPoint(point)
            .withIconImage(marker)

          it.create(pointAnnotationOptions)
          mapView.getMapboxMap()
            .flyTo(CameraOptions.Builder().zoom(DEFAULT_ZOOM).center(point).build())
        }
        destinationPointAnnotationManager?.let {
          it.deleteAll()
        }
      }
      if (destinationPoint != null) {
        destinationPointAnnotationManager?.let {
          it.deleteAll()
          val pointAnnotationOptions = PointAnnotationOptions()
            .withPoint(destinationPoint)
            .withIconImage(destinationMarker)

          it.create(pointAnnotationOptions)

          mapView.getMapboxMap().flyTo(
            mapView.getMapboxMap().cameraForCoordinateBounds(
              CoordinateBounds(point!!, destinationPoint),
              EdgeInsets(
                DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING
              )
            )
          )
        }
      }
      NoOpUpdate
    },
    modifier = modifier
  )
}

private const val MARKER_SIZE = 64
private const val DEFAULT_ZOOM = 16.0
private const val DEFAULT_PADDING = 200.0