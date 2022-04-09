package com.example.trackme.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.trackme.R
import com.example.trackme.databinding.FragmentFindFriendsBinding
import com.example.trackme.databinding.FragmentHomeBinding


class FindFriendsFragment : Fragment() {


    private var _binding: FragmentFindFriendsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFindFriendsBinding.bind(view)

        val navController = findNavController()

        binding.btnViewUser.setOnClickListener {
            val action = FindFriendsFragmentDirections.actionFindFriendsFragmentToViewUserFragment()
            navController.navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}