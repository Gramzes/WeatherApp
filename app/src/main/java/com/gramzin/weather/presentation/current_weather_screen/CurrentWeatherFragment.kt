package com.gramzin.weather.presentation.current_weather_screen

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.gramzin.weather.R
import com.gramzin.weather.databinding.FragmentCurrentWeatherBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class CurrentWeatherFragment : Fragment() {

    private lateinit var binding: FragmentCurrentWeatherBinding
    val viewModel: CurrentWeatherViewModel by viewModel()

    private val locationCancellationToken = CancellationTokenSource()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getLocation()
            } else -> {

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(PermissionDialog.RESULT_KEY) { requestKey, bundle ->
            val showPermission = bundle.getBoolean(PermissionDialog.RESULT_VALUE_KEY)

            if (showPermission){
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewModel.state.observe(viewLifecycleOwner){
            when (it){
                CurrentWeatherScreenState.Initial -> {
                    binding.weatherIcon.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                    getLocation()
                }
                CurrentWeatherScreenState.Loading -> {
                    binding.weatherIcon.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is CurrentWeatherScreenState.WeatherLoaded -> {
                    initDataLoadedView(it)
                }
            }
        }
    }

    private fun initView() = with(binding){
        ViewCompat.setOnApplyWindowInsetsListener(binding.constraintLayout){ view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(insets.left, insets.top, insets.right, insets.bottom)
            return@setOnApplyWindowInsetsListener WindowInsetsCompat.CONSUMED
        }

        val buttonPaint = binding.forecastButton.paint
        val textShader: Shader = LinearGradient(
            0f,
            0f,
            buttonPaint.measureText(getString(R.string.next_days_button_title)),
            buttonPaint.textSize,
            intArrayOf(Color.parseColor("#a976da"), Color.parseColor("#ebd289")),
            floatArrayOf(0f, 1f),
            TileMode.CLAMP
        )
        binding.forecastButton.paint.shader = textShader

        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        binding.greetingText.text = when {
            currentHour < 5 -> getString(R.string.good_night_greeting)
            currentHour < 12 -> getString(R.string.good_morning_greeting)
            currentHour < 18 -> getString(R.string.good_afternoon_greeting)
            else -> getString(R.string.good_evening_greeting)
        }
    }

    private fun initDataLoadedView(state: CurrentWeatherScreenState.WeatherLoaded) = with(binding){

        weatherIcon.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE

        val dateTimeFormatter = SimpleDateFormat("EEEE dd · HH:mm", Locale.getDefault())
        dateTimeFormatter.timeZone = TimeZone.getTimeZone("UTC")

        cityName.text = state.city
        tempText.text = "${state.currentWeather.temp}°C"

        if (state.currentWeather.time < state.currentWeather.sunriseTime ||
            state.currentWeather.time > state.currentWeather.sunsetTime
        ) {
            weatherIcon
                .setImageResource(state.currentWeather.weatherType.nightIconResId)
        } else {
            weatherIcon
                .setImageResource(state.currentWeather.weatherType.dayIconResId)
        }

        dateTimeText.text = dateTimeFormatter.format(Date(state.currentWeather.time))
        weatherTypeText.text = requireContext()
            .getString(state.currentWeather.weatherType.titleResId)

        dateTimeFormatter.applyPattern("HH:mm")
        sunriseTimeText.text = dateTimeFormatter.format(Date(state.currentWeather.sunriseTime))
        sunsetTimeText.text = dateTimeFormatter.format(Date(state.currentWeather.sunsetTime))

        humidityValueText.text = "${state.currentWeather.humidity}%"
        cloudinessValueText.text = "${state.currentWeather.cloudiness}%"

        binding.forecastRC.adapter = HourlyForecastAdapter(state.forecast)
    }

    private fun getLocation(){
        val permissionGranted = checkLocationPermission()
        when{
            permissionGranted -> {
                val fusedLocationClient = LocationServices
                    .getFusedLocationProviderClient(requireActivity())
                fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY, locationCancellationToken.token
                ).addOnSuccessListener {
                    viewModel.getData(it.latitude, it.longitude)
                }
            }
            else ->{
                PermissionDialog().show(parentFragmentManager, "permission_dialog")
            }
        }
    }

    private fun checkLocationPermission(): Boolean{
        val accessFine = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val accessCoarse = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return accessFine || accessCoarse
    }
}