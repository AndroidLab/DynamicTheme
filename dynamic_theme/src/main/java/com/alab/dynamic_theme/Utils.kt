package com.alab.dynamic_theme

import android.content.Context
import android.content.ContextWrapper

internal object Utils {
    fun getDynamicThemeActivity(context: Context): DynamicThemeActivity<*>? {
        var localContext = context
        while (localContext is ContextWrapper) {
            if (localContext is DynamicThemeActivity<*>) {
                return localContext
            }
            localContext = localContext.baseContext
        }
        return null
    }
}