package com.alab.dynamic_theme

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


abstract class DynamicTheme(
    private val context: Context
) {
    abstract val type: DynamicThemeType

    /**
     * Returns color.
     * @param colorRes Color res.
     */
    fun getColor(
        @ColorRes colorRes: Int
    ) = ContextCompat.getColor(context, colorRes)

    /**
     * Returns drawable.
     * @param drawableRes Drawable res.
     */
    fun getDrawable(
        @DrawableRes drawableRes: Int
    ) = ContextCompat.getDrawable(context, drawableRes)
}