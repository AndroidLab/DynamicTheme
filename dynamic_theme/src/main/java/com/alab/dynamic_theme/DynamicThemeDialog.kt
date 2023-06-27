package com.alab.dynamic_theme

import androidx.fragment.app.DialogFragment


abstract class DynamicThemeDialog<T : DynamicTheme> : DialogFragment() {
    override fun onResume() {
        if (activity is DynamicThemeActivity<*>) {
            DynamicThemeManager.getInstance<T>().themeLiveData.observe(viewLifecycleOwner) { theme ->
                syncTheme(theme)
            }
        } else {
            throw NotImplementedError()
        }
        super.onResume()
    }

    /**
     * Calling theme synchronization.
     */
    abstract fun syncTheme(appTheme: T)
}