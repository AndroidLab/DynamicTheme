package com.alab.dynamic_theme

import android.content.Context
import com.alab.preferences_delegate.PreferencesDelegate

/**
 * Прдставляет предпочтения темы.
 */
class DynamicThemePreferences(
    context: Context
) {
    companion object {
        private const val THEME_TYPE = "theme_type"
    }

    private val preferences by lazy { context.getSharedPreferences(DynamicThemePreferences::class.java.simpleName, Context.MODE_PRIVATE) }

    /**
     * Возвращает или устанавливает тип темы.
     */
    var dynamicThemeType: DynamicThemeType by PreferencesDelegate(
        preferences,
        THEME_TYPE,
        DynamicThemeType.LIGHT, {
            return@PreferencesDelegate it.name
        }, {
            return@PreferencesDelegate DynamicThemeType.valueOf(it)
        }
    )

}