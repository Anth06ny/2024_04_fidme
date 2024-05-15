package com.amonteiro.a2024_04_fidme.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.amonteiro.a2024_04_fidme.databinding.FragmentScreen12Binding

/**
 * A simple [Fragment] subclass.
 * Use the [Screen12Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Screen12Fragment : Fragment(), MenuProvider {

    private var _binding: FragmentScreen12Binding? = null
    private val binding get() = _binding!!

    //val model by lazy { ViewModelProvider(this)[Screen12BindingViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val value = arguments?.let { Screen12FragmentArgs.fromBundle( it).test}

        //Menu
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.STARTED)

        _binding = FragmentScreen12Binding.inflate(inflater, container, false)

        binding.tv.text = "Screen 12\nvalue=$value"

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.add(0,157, 0, "Back to 1.1")
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == 157){
            findNavController().navigateUp()
            return true
        }

        return false
    }

}