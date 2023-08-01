package com.sadri.mapbox.map.presentation

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
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.LineString
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
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapLongClickListener
import com.sadri.mapbox.map.R


@Composable
fun MapboxMap(
  modifier: Modifier = Modifier,
  state: MapboxMapState,
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
  val colorLine = remember(context) {
    ContextCompat.getColor(context, R.color.line_color)
  }


  var pointAnnotationManager: PointAnnotationManager? by remember {
    mutableStateOf(null)
  }
  var destinationPointAnnotationManager: PointAnnotationManager? by remember {
    mutableStateOf(null)
  }
  var polylineAnnotationManager: PolylineAnnotationManager? by remember {
    mutableStateOf(null)
  }
  AndroidView(
    factory = {
      MapView(it).also { mapView ->
        mapView.getMapboxMap().loadStyleUri(Style.TRAFFIC_DAY)
        val annotationApi = mapView.annotations
        pointAnnotationManager = annotationApi.createPointAnnotationManager()
        destinationPointAnnotationManager = annotationApi.createPointAnnotationManager()
        polylineAnnotationManager = annotationApi.createPolylineAnnotationManager()

        mapView.getMapboxMap().addOnMapLongClickListener { point ->
          onLongClick.invoke(point)
          true
        }
      }
    },
    update = { mapView ->
      if (state.originPoint != null) {
        pointAnnotationManager?.let {
          it.deleteAll()
          val pointAnnotationOptions = PointAnnotationOptions()
            .withPoint(state.originPoint)
            .withIconImage(marker)

          it.create(pointAnnotationOptions)
          mapView.getMapboxMap()
            .flyTo(CameraOptions.Builder().zoom(DEFAULT_ZOOM).center(state.originPoint).build())
        }
        destinationPointAnnotationManager?.deleteAll()
        polylineAnnotationManager?.deleteAll()
      }
      if (
        state.destinationPoint != null &&
        state.originPoint != null
      ) {
        destinationPointAnnotationManager?.let {
          it.deleteAll()
          val pointAnnotationOptions = PointAnnotationOptions()
            .withPoint(state.destinationPoint)
            .withIconImage(destinationMarker)

          it.create(pointAnnotationOptions)

          mapView.getMapboxMap().flyTo(
            mapView.getMapboxMap().cameraForCoordinateBounds(
              CoordinateBounds(state.originPoint, state.destinationPoint),
              EdgeInsets(
                DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING
              )
            )
          )
        }
      }
      if (
        state.navigationRoute != null &&
        state.originPoint != null &&
        state.destinationPoint != null
      ) {
        val directionRoute = state.navigationRoute.directionsRoute
        polylineAnnotationManager?.let {

          val lineString =
            LineString.fromPolyline(directionRoute.geometry()!!, Constants.PRECISION_6)
          it.deleteAll()

          val polylineAnnotationOptions = PolylineAnnotationOptions()
            .withGeometry(lineString)
            .withLineWidth(LINE_WIDTH)
            .withLineColor(colorLine)

          it.create(polylineAnnotationOptions)

          mapView.getMapboxMap().flyTo(
            mapView.getMapboxMap().cameraForCoordinateBounds(
              CoordinateBounds(state.originPoint, state.destinationPoint),
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
private const val LINE_WIDTH = 5.0