package com.amonteiro.a2024_04_fidme

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amonteiro.a2024_04_fidme.databinding.ActivityExoNavGraphBinding

class ExoNavGraphActivity : AppCompatActivity(), MenuProvider {

    val binding by lazy { ActivityExoNavGraphBinding.inflate(layoutInflater) }
    //val model by lazy { ViewModelProvider(this)[ExoNavGraphBindingViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addMenuProvider(this)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val navController = binding.navHostFragment.findNavController()

        //On le relie à l'ActionBar
        binding.bottomNavigation.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.screen11Fragment, R.id.screen21Fragment))

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return binding.navHostFragment.findNavController().navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.add(0, 154, 0, "Finish")
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
       if(menuItem.itemId == 154){
           finish()
           return true
       }
        return false
    }

}