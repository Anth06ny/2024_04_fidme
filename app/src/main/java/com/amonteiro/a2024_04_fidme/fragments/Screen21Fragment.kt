package com.amonteiro.a2024_04_fidme.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amonteiro.a2024_04_fidme.R
import com.amonteiro.a2024_04_fidme.databinding.FragmentScreen21Binding


class Screen21Fragment : Fragment() {

    private var _binding: FragmentScreen21Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentScreen21Binding.inflate(inflater, container, false)

        binding.tv.setOnClickListener {
            findNavController().navigate(R.id.action_screen21Fragment_to_screen22Fragment)
        }

        return binding.root
    }

}