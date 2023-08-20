package com.gramzin.weather.presentation.find_city_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gramzin.weather.R
import com.gramzin.weather.databinding.FragmentFindCityBinding

class FindCityFragment : Fragment() {

    private lateinit var binding: FragmentFindCityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindCityBinding.inflate(inflater, container, false)
        return binding.root
    }

}