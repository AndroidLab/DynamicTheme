package com.alab.dynamic_theme

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map


/**
 * Represents the dynamic theme manager.
 */
class DynamicThemeManager<T : DynamicTheme>(
    val lightDarkThemes: Pair<T, T>,
    private val requireContext: () -> Context
) {

    /**
     * Returns theme preferences.
     */
    val themePreferences by lazy { DynamicThemePreferences(requireContext()) }


    private val _themeLiveData: MutableLiveData<T> = MutableLiveData()

    /**
     * Returns the current theme.
     */
    val themeLiveData: LiveData<T> = _themeLiveData

    /**
     * Returns the type of the current theme.
     */
    val themeTypeLiveData: LiveData<DynamicThemeType> =
        _themeLiveData.map {
            it.type
        }

    init {
        _themeLiveData.value = when (themePreferences.dynamicThemeType) {
            DynamicThemeType.LIGHT -> lightDarkThemes.first
            DynamicThemeType.DARK -> lightDarkThemes.second
        }
    }

    /**
     * Returns the current theme.
     */
    fun getCurrentTheme(): T = _themeLiveData.value!!

    fun setCurrentTheme(currentTheme: T) {
        _themeLiveData.value = currentTheme
    }

    companion object {
        /**
         * Returns an instance with a common theme.
         */
        lateinit var manager: DynamicThemeManager<DynamicTheme>

        /**
         * Returns an instance with a specific theme.
         */
        fun <T : DynamicTheme> getInstance(): DynamicThemeManager<T> =
            manager as DynamicThemeManager<T>

        /**
         * Manager initialization
         */
        fun <T : DynamicTheme> init(
            lightDarkThemes: Pair<T, T>,
            requireContext: () -> Context
        ) {
            manager = DynamicThemeManager(
                lightDarkThemes.apply {
                    first.context = requireContext()
                    second.context = requireContext()
                }, requireContext
            )
        }
    }

}