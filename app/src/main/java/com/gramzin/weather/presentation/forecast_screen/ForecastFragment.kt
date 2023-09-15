package com.gramzin.weather.presentation.forecast_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.gramzin.weather.databinding.FragmentForecastBinding

class ForecastFragment() : Fragment() {

    private lateinit var binding: FragmentForecastBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object{

        fun newInstance(latitude: Double, longitude: Double): ForecastFragment{
            val fragment = ForecastFragment()

            fragment.arguments = Bundle().apply {
                putDouble(LATITUDE_KEY, latitude)
                putDouble(LONGITUDE_KEY, longitude)
            }
            return fragment
        }

        private const val LATITUDE_KEY = "LATITUDE_KEY"
        private const val LONGITUDE_KEY = "LONGITUDE_KEY"
    }
}