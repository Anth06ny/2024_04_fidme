package com.amonteiro.a2024_04_fidme.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amonteiro.a2024_04_fidme.databinding.FragmentScreen22Binding

/**
 * A simple [Fragment] subclass.
 * Use the [Screen22Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Screen22Fragment : Fragment() {

    private var _binding: FragmentScreen22Binding? = null
    private val binding get() = _binding!!

    //val model by lazy { ViewModelProvider(this)[Screen12BindingViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentScreen22Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}