package com.alab.dynamic_theme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * Represents the basic fragment for a dynamic theme.
 */
abstract class DynamicThemeFragment<T: DynamicTheme> : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DynamicThemeManager.getInstance<T>().themeLiveData.observe(viewLifecycleOwner) { theme ->
            syncTheme(theme)
        }
    }

    /**
     * Calling theme synchronization.
     */
    abstract fun syncTheme(appTheme: T)
}