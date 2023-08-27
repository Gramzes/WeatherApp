package com.gramzin.weather.presentation.current_weather_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gramzin.weather.databinding.HourlyForecastHolderBinding
import com.gramzin.weather.domain.PartOfDay
import com.gramzin.weather.domain.model.HourlyWeather
import com.gramzin.weather.domain.model.Weather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class HourlyForecastAdapter(
    private val forecast: List<HourlyWeather>
): RecyclerView.Adapter<HourlyForecastAdapter.HourlyHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyHolder {
        val binding = HourlyForecastHolderBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyHolder, position: Int) {
        val weather = forecast[position]
        holder.bind(weather)
    }

    override fun getItemCount() = forecast.size

    class HourlyHolder(
        private val binding: HourlyForecastHolderBinding
        ): RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: HourlyWeather){
            val iconResId = when(weather.partOfDay){
                PartOfDay.Day -> weather.weatherType.dayIconResId
                PartOfDay.Night -> weather.weatherType.nightIconResId
            }
            binding.imageView.setImageResource(iconResId)

            val dateTimeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            binding.timeValueText.text = dateTimeFormatter.format(Date(weather.time))
            binding.tempValueText.text = "${weather.temp}°C"
        }
    }
}