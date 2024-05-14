package com.amonteiro.a2024_04_fidme

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.amonteiro.a2024_04_fidme.databinding.ActivityOldMainBinding
import com.amonteiro.a2024_04_fidme.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso

class OldMainActivity : AppCompatActivity() {


    val binding by lazy { ActivityOldMainBinding.inflate(layoutInflater) }
    val model by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observe()

        binding.btLoad.setOnClickListener {
            model.loadWeather(binding.editText.text.toString())
        }
    }

    private fun observe() {
        model.runInProgress.observe(this) {
            binding.progressBar.isVisible = it
        }

        model.errorMessage.observe(this) {
            binding.tvError.isVisible = it.isNotBlank()
            binding.tvError.text = it
        }

        model.weather.observe(this) {
            binding.tvData.text = "Il fait ${it?.main?.temp ?: "-"}° à ${it?.name ?: "-"} avec un vent de ${it?.wind?.speed ?: "-"} m/s\n"
            it?.weather?.getOrNull(0)?.icon?.let {
                Picasso.get().load(it).into(binding.imageView)
            } ?: binding.imageView.setImageDrawable(null)
        }

    }
}