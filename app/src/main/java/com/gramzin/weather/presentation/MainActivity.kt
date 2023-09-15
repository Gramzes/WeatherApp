package com.gramzin.weather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.LifecycleOwner
import com.gramzin.weather.R
import com.gramzin.weather.databinding.ActivityMainBinding
import com.gramzin.weather.presentation.contract.Navigator
import com.gramzin.weather.presentation.find_city_screen.FindCityFragment
import com.gramzin.weather.presentation.forecast_screen.ForecastFragment

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun showForecastScreen(latitude: Double, longitude: Double) {
        launchFragment(ForecastFragment.newInstance(latitude, longitude))
    }

    override fun showFindCityFragment() {
        launchFragment(FindCityFragment.newInstance())
    }

    override fun goBack() {
        supportFragmentManager.popBackStack()
    }

    override fun <T : Parcelable> publishResult(result: T) {
        supportFragmentManager.setFragmentResult(
            result.javaClass.name, bundleOf(RESULT_KEY to result)
        )
    }

    override fun <T : Parcelable> listenResult(
        clazz: Class<T>,
        owner: LifecycleOwner,
        resultListener: FragmentResultListener
    ) {
        supportFragmentManager
            .setFragmentResultListener(clazz.name, owner, resultListener)
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            replace(R.id.fragmentContainerView, fragment)
        }
    }

    companion object {

        const val RESULT_KEY = "RESULT_KEY"
    }
}