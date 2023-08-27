package com.gramzin.weather.domain

sealed interface PartOfDay{

    object Day: PartOfDay
    object Night: PartOfDay
}