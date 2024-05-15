package com.amonteiro.a2024_04_fidme.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.amonteiro.a2024_04_fidme.R
import com.amonteiro.a2024_04_fidme.adapter.CarListAdapter
import com.amonteiro.a2024_04_fidme.databinding.FragmentScreen11Binding
import com.amonteiro.a2024_04_fidme.model.CarBean


class Screen11Fragment : Fragment(), MenuProvider {

    private var _binding: FragmentScreen11Binding? = null
    private val binding get() = _binding!!

    private val list = arrayListOf(CarBean("Seat" , "Leon"), CarBean("Seat" , "Ibiza"))

    private val adapter = CarListAdapter().apply {
        submitList(list)
    }

//    val model by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
//    val model by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Screen11Fragment.onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentScreen11Binding.inflate(inflater, container, false)

        //Menu
        //requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.tv.setOnClickListener {
            val action = Screen11FragmentDirections.actionScreen11FragmentToScreen12Fragment("myvalue")
            //findNavController().navigate(R.id.action_screen11Fragment_to_screen12Fragment)
            findNavController().navigate(action)
        }

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(requireActivity(), 2)


        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.STARTED)

        (requireActivity() as? AppCompatActivity)?.supportActionBar?.subtitle = "Nouveau sous titre"

        binding.btAdd.setOnClickListener {
            list.add(0, CarBean("Marque_${list.size}", "Model"))
            adapter.submitList(list.toList())
        }

        binding.btDelete.setOnClickListener {
            list.removeFirstOrNull()
            adapter.submitList(list.toList())
        }

        return binding.root
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.add(0,156, 0, "Goto 1.2")
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == 156){
            findNavController().navigate(R.id.action_screen11Fragment_to_screen12Fragment)
            return true
        }

        return false
    }
}