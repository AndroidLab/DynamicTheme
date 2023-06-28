package com.alab.dynamictheme

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.alab.dynamic_theme.DynamicThemeManager


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        DynamicThemeManager.init(
            Pair(LightTheme(), DarkTheme())
        ) {
            baseContext
        }
    }
}