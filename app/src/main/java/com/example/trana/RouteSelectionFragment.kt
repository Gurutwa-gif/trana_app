package com.example.trana

import android.content.Context
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

class RouteSelectionFragment : Fragment() {

    private var mapView: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Configuration.getInstance().load(requireContext(), requireContext().getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
        return inflater.inflate(R.layout.fragment_route_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.map_route_selection)
        mapView?.setTileSource(TileSourceFactory.MAPNIK)
        mapView?.setMultiTouchControls(true)

        val mapController = mapView?.controller
        mapController?.setZoom(13.0)
        
        val defaultLocation = GeoPoint(37.7749, -122.4194)
        mapController?.setCenter(defaultLocation)

        // Route option clicks
        view.findViewById<View>(R.id.route_option_safest)?.setOnClickListener {
            Toast.makeText(requireContext(), "Safest route selected", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<View>(R.id.route_option_fastest)?.setOnClickListener {
            Toast.makeText(requireContext(), "Fastest route selected", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<View>(R.id.route_option_alt)?.setOnClickListener {
            Toast.makeText(requireContext(), "Alternative route selected", Toast.LENGTH_SHORT).show()
        }

        // Start Navigation button
        view.findViewById<View>(R.id.btn_start_navigation)?.setOnClickListener {
            Toast.makeText(requireContext(), "Starting navigation...", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
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
