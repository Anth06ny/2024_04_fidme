package com.amonteiro.a2024_04_fidme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.amonteiro.a2024_04_fidme.databinding.ActivityOldMainBinding
import com.amonteiro.a2024_04_fidme.model.WeatherAPI
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OldMainActivity : AppCompatActivity() {


    val binding by lazy { ActivityOldMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(binding.root)

        binding.btLoad.setOnClickListener{

            val cityName = binding.editText.text.toString()
            binding.tvError.isVisible = false
            binding.progressBar.isVisible = true

            lifecycleScope.launch(Dispatchers.Default) {

                try {
                    val weather = WeatherAPI.loadWeather(cityName)

                    launch(Dispatchers.Main) {
                        binding.tvData.text = "Il fait ${weather.main.temp}° à ${weather.name} avec un vent de ${weather.wind.speed} m/s\n"
                        binding.progressBar.isVisible = false
                        weather.weather.getOrNull(0)?.icon?.let {
                            Picasso.get().load(it).into(binding.imageView)
                        }
                    }
                }
                catch(e:Exception){
                    e.printStackTrace()
                    launch(Dispatchers.Main) {
                        binding.tvError.setText(e.message ?: "Une erreur est survenue")
                        binding.tvError.isVisible = true
                        binding.progressBar.isVisible = false
                    }
                }

            }
        }


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    override fun onStart() {
        super.onStart()

    }

}