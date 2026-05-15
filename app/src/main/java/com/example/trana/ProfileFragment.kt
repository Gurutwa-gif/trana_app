package com.example.trana

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Emergency Contacts
        view.findViewById<View>(R.id.btn_emergency_contacts)?.setOnClickListener {
            Toast.makeText(requireContext(), "Emergency Contacts", Toast.LENGTH_SHORT).show()
        }

        // Notifications
        view.findViewById<View>(R.id.btn_notifications)?.setOnClickListener {
            Toast.makeText(requireContext(), "Notifications Settings", Toast.LENGTH_SHORT).show()
        }

        // Log Out
        view.findViewById<View>(R.id.btn_logout)?.setOnClickListener {
            Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()
        }

        // Profile image - load with Glide in future
        // val profileImage = view.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.iv_profile)
        // Glide.with(this).load(profileUrl).into(profileImage)
    }
}
