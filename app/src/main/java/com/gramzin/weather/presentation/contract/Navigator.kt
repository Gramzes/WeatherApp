package com.gramzin.weather.presentation.contract

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.gramzin.weather.presentation.MainActivity

fun Fragment.navigator(): Navigator{
    return requireActivity() as MainActivity
}

interface Navigator {

    fun showForecastScreen(latitude: Double, longitude: Double)

    fun showFindCityFragment()

    fun goBack()

    fun <T: Parcelable> publishResult(result: T)

    fun <T: Parcelable> listenResult(
        clazz: Class<T>,
        owner: LifecycleOwner,
        resultListener: FragmentResultListener
    )
}

