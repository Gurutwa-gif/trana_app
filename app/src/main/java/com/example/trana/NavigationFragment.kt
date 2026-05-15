package com.example.trana

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class NavigationFragment : Fragment() {

    private var mapView: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Configuration.getInstance().load(requireContext(), requireContext().getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.map_navigation)
        mapView?.setTileSource(TileSourceFactory.MAPNIK)
        mapView?.setMultiTouchControls(true)

        val mapController = mapView?.controller
        mapController?.setZoom(14.0)
        
        // Default location (San Francisco)
        val defaultLocation = GeoPoint(37.7749, -122.4194)
        mapController?.setCenter(defaultLocation)

        // Add safety markers
        val safeHaven1 = GeoPoint(37.7749, -122.4294)
        val marker1 = Marker(mapView)
        marker1.position = safeHaven1
        marker1.title = "Safe Haven"
        mapView?.overlays?.add(marker1)

        val safeHaven2 = GeoPoint(37.7819, -122.4094)
        val marker2 = Marker(mapView)
        marker2.position = safeHaven2
        marker2.title = "Safe Haven"
        mapView?.overlays?.add(marker2)

        // Destination input - navigate to route map when user enters destination
        val etDestination = view.findViewById<EditText>(R.id.et_destination)
        etDestination?.setOnEditorActionListener { _, _, _ ->
            if (etDestination.text.isNotEmpty()) {
                findNavController().navigate(R.id.action_navigation_to_routeMap)
            }
            true
        }

        // Profile button
        view.findViewById<View>(R.id.iv_profile_nav)?.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        // Call button
        view.findViewById<View>(R.id.btn_call_nav)?.setOnClickListener {
            Toast.makeText(requireContext(), "Calling emergency services...", Toast.LENGTH_SHORT).show()
        }

        // SOS button
        view.findViewById<View>(R.id.btn_sos_nav)?.setOnClickListener {
            Toast.makeText(requireContext(), "SOS Alert Sent!", Toast.LENGTH_SHORT).show()
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
