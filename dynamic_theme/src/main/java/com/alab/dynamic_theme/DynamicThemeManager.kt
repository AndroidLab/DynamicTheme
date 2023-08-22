package com.alab.dynamic_theme

import android.content.Context
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map


/**
 * Represents the dynamic theme manager.
 */
class DynamicThemeManager<T : DynamicTheme> private constructor(
    val lightTheme: T,
    val darkTheme: T,
    private val requireContext: () -> Context
) {

    companion object {
        val ROOT_ID = ViewCompat.generateViewId()

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
            lightTheme: T,
            darkTheme: T,
            requireContext: () -> Context
        ): DynamicThemeManager<T> {
            manager = DynamicThemeManager(
                lightTheme.apply { context = requireContext() },
                darkTheme.apply { context = requireContext() },
                requireContext
            )
            return manager as DynamicThemeManager<T>
        }
    }

    /**
     * Returns theme preferences.
     */
    val themePreferences by lazy { DynamicThemePreferences(requireContext()) }

    private var _isSyncStatusBarIcons = false
    private var _isSyncStatusBarIconsReverse = false

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
            DynamicThemeType.LIGHT -> lightTheme
            DynamicThemeType.DARK -> darkTheme
        }
    }

    /**
     * Returns the current theme.
     */
    fun getCurrentTheme(): T = _themeLiveData.value!!

    fun setCurrentTheme(currentTheme: T) {
        _themeLiveData.value = currentTheme
    }


    /**
     * Reverse change theme.
     */
    fun reverseChangeTheme(view: View) =
        reverseChangeTheme(view, 600)

    /**
     * Reverse change theme.
     */
    fun reverseChangeTheme(view: View, duration: Long) =
        Utils.getDynamicThemeActivity(view.context)
            ?.changeTheme(getViewCoordinates(view), duration, true)

    /**
     * Change theme.
     */
    fun changeTheme(view: View) =
        changeTheme(view, 600)

    /**
     * Change theme.
     */
    fun changeTheme(view: View, duration: Long) {
        Utils.getDynamicThemeActivity(view.context)
            ?.changeTheme(getViewCoordinates(view), duration)
    }

    private fun getViewCoordinates(view: View): Coordinate {
        return Coordinate(
            getRelativeLeft(view) + view.width / 2,
            getRelativeTop(view) + view.height / 2
        )
    }

    private fun getRelativeLeft(myView: View): Int {
        return if ((myView.parent as View).id == ROOT_ID) myView.left else myView.left + getRelativeLeft(
            myView.parent as View
        )
    }

    private fun getRelativeTop(myView: View): Int {
        return if ((myView.parent as View).id == ROOT_ID) myView.top else myView.top + getRelativeTop(
            myView.parent as View
        )
    }

    /**
     * Sets the flag for changing the color of icons in the status bar depending on the selected theme.
     * If a light theme is selected, the icons will be black.
     * If a dark theme is selected, the icons will be white.
     */
    fun setSyncStatusBarIconsColorWithTheme(
        isSync: Boolean,
        isReverse: Boolean = false
    ): DynamicThemeManager<T> {
        _isSyncStatusBarIcons = isSync
        _isSyncStatusBarIconsReverse = isReverse
        return manager as DynamicThemeManager<T>
    }

    /**
     * Returns the flag for changing the color of icons in the status bar.
     */
    fun getSyncStatusBarIconsColorWithTheme() = _isSyncStatusBarIcons

    /**
     * Returns the flag of the reverse color of the icons of the status bar.
     */
    fun getSyncStatusBarIconsColorWithThemeReverse() = _isSyncStatusBarIconsReverse
}