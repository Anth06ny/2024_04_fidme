package com.amonteiro.a2024_04_fidme

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.amonteiro.a2024_04_fidme.databinding.ActivityOldMainBinding
import com.amonteiro.a2024_04_fidme.model.CarBean
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

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.add(0, 1, 0, "Compose")
        menu.add(0, 2, 0, "Fragment")
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
        return false
    }





}