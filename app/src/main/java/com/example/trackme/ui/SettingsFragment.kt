package com.example.trackme.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.trackme.R
import com.example.trackme.databinding.FragmentHomeBinding
import com.example.trackme.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment() {


    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        auth = FirebaseAuth.getInstance()

        val navController = findNavController()

//        binding.btnEditProfile.setOnClickListener {
//            val action = SettingsFragmentDirections.actionSettingsFragmentToEditFragment()
//            navController.navigate(action)
//        }

        val currentUser = auth.currentUser

        binding.tvName.text = currentUser?.displayName
        binding.tvEmail.text = currentUser?.email
        Glide.with(this)
            .load(currentUser?.photoUrl)
            .into(binding.ivProfile)

        binding.logoutButton.setOnClickListener {
            auth.signOut()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}