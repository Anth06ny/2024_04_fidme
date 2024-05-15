package com.amonteiro.a2024_04_fidme

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import com.amonteiro.a2024_04_fidme.databinding.ActivityOldMainBinding
import com.amonteiro.a2024_04_fidme.model.CarBean
import com.amonteiro.a2024_04_fidme.utils.LocationUtils
import com.amonteiro.a2024_04_fidme.utils.NotificationUtlis
import com.amonteiro.a2024_04_fidme.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso

class OldMainActivity : AppCompatActivity(), MenuProvider {


    val binding by lazy { ActivityOldMainBinding.inflate(layoutInflater) }
    val model by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        println("OldMainActivity.onCreate")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.lifecycleOwner = this
        binding.viewModel = model

        addMenuProvider(this)

        observe()
//
//        binding.btLoad.setOnClickListener {
//            model.loadWeather()
//        }
    }

    private fun observe() {
//        model.runInProgress.observe(this) {
//            binding.progressBar.isVisible = it
//        }
//
//        model.errorMessage.observe(this) {
//            binding.tvError.isVisible = it.isNotBlank()
//            binding.tvError.text = it
//        }

        model.weather.observe(this) {
            binding.tvData.text = "Il fait ${it?.main?.temp ?: "-"}° à ${it?.name ?: "-"} avec un vent de ${it?.wind?.speed ?: "-"} m/s\n"
            it?.weather?.getOrNull(0)?.icon?.let {
                Picasso.get().load(it).into(binding.imageView)
            } ?: binding.imageView.setImageDrawable(null)
        }

        model.myLocation.observe(this) {
            binding.tvData.text = it?.let { "${it.latitude}, ${it.longitude}" } ?: "-"
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.add(0, 1, 0, "Compose")
        menu.add(0, 2, 0, "Fragment")
        menu.add(0, 3, 0, "MyLocalisation")
        menu.add(0, 4, 0, "Now")
        menu.add(0, 5, 0, "5s")
    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == 1) {
            startActivity(Intent(this, MainActivity::class.java))
            return true
        }
        else if (menuItem.itemId == 2) {
            val intent = Intent(this, ExoNavGraphActivity::class.java)
            intent.putExtra("key", CarBean())
            startActivity(intent)
            return true
        }
        if (menuItem.itemId == 3) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0)
            }
            return true
        }
        if (menuItem.itemId == 4) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
                NotificationUtlis.createInstantNotification(this, "Test immédiat")
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }

            return true
        }
        if (menuItem.itemId == 5) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
                NotificationUtlis.scheduleNotification(this, "test à retardement", 5000)
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 2)
            }

            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1 ){

        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            updateLocalisation()
        }
        else {
            model.errorMessage.postValue("Il faut la permission")
        }

    }

    fun updateLocalisation(){
        LocationUtils.getLastKnowLocationEconomyMode(this)?.addOnSuccessListener {
            model.myLocation.postValue(it)
        }?.addOnFailureListener {
            it.printStackTrace()
            model.errorMessage.postValue(it.message ?: "Une erreur est survenue")
        } ?:  model.errorMessage.postValue( "Pas de localisation")
    }





}