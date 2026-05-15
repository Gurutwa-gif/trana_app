package com.example.trana

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.Polyline

class RouteMapFragment : Fragment() {

    private var mapView: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Configuration.getInstance().load(requireContext(), requireContext().getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
        return inflater.inflate(R.layout.fragment_route_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.map_route)
        mapView?.setTileSource(TileSourceFactory.MAPNIK)
        mapView?.setMultiTouchControls(true)

        val mapController = mapView?.controller
        mapController?.setZoom(13.0)

        // Default locations
        val startLocation = GeoPoint(37.7749, -122.4194) // San Francisco
        val endLocation = GeoPoint(37.7694, -122.4862)   // Golden Gate Park
        mapController?.setCenter(startLocation)

        // Start marker
        val startMarker = Marker(mapView)
        startMarker.position = startLocation
        startMarker.title = "Current Location"
        mapView?.overlays?.add(startMarker)

        // End marker
        val endMarker = Marker(mapView)
        endMarker.position = endLocation
        endMarker.title = "Golden Gate Park"
        mapView?.overlays?.add(endMarker)

        // Route Points
        val routePoints = listOf(
            startLocation,
            GeoPoint(37.7750, -122.4350),
            GeoPoint(37.7730, -122.4500),
            GeoPoint(37.7710, -122.4650),
            endLocation
        )

        // Route glow effect (wider, semi-transparent)
        val glowLine = Polyline()
        glowLine.setPoints(routePoints)
        glowLine.outlinePaint.color = Color.parseColor("#40B4C5FF")
        glowLine.outlinePaint.strokeWidth = 24f
        mapView?.overlays?.add(glowLine)

        // Draw safe route (blue polyline)
        val routeLine = Polyline()
        routeLine.setPoints(routePoints)
        routeLine.outlinePaint.color = Color.parseColor("#004AC6")
        routeLine.outlinePaint.strokeWidth = 12f
        mapView?.overlays?.add(routeLine)

        // Danger zone circles
        val dangerCircle = Polygon()
        dangerCircle.points = Polygon.pointsAsCircle(GeoPoint(37.7720, -122.4400), 200.0)
        dangerCircle.fillPaint.color = Color.parseColor("#20BA1A1A")
        dangerCircle.outlinePaint.color = Color.parseColor("#40BA1A1A")
        dangerCircle.outlinePaint.strokeWidth = 2f
        mapView?.overlays?.add(dangerCircle)

        // Moderate zone
        val modCircle = Polygon()
        modCircle.points = Polygon.pointsAsCircle(GeoPoint(37.7760, -122.4550), 250.0)
        modCircle.fillPaint.color = Color.parseColor("#15BC4800")
        modCircle.outlinePaint.color = Color.parseColor("#40BC4800")
        modCircle.outlinePaint.strokeWidth = 2f
        mapView?.overlays?.add(modCircle)

        // SOS Button
        view.findViewById<View>(R.id.btn_sos_route)?.setOnClickListener {
            Toast.makeText(requireContext(), "SOS Alert Sent!", Toast.LENGTH_SHORT).show()
        }

        // Call Button
        view.findViewById<View>(R.id.btn_call_route)?.setOnClickListener {
            Toast.makeText(requireContext(), "Calling emergency services...", Toast.LENGTH_SHORT).show()
        }

        // Alternate Routes Button
        view.findViewById<View>(R.id.btn_alternate_routes)?.setOnClickListener {
            findNavController().navigate(R.id.action_routeMap_to_routeSelection)
        }

        // Map controls
        view.findViewById<View>(R.id.btn_zoom_in)?.setOnClickListener {
            mapController?.zoomIn()
        }
        view.findViewById<View>(R.id.btn_zoom_out)?.setOnClickListener {
            mapController?.zoomOut()
        }
        view.findViewById<View>(R.id.btn_my_location)?.setOnClickListener {
            mapController?.animateTo(startLocation)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }
}
