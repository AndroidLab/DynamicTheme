package com.alab.dynamictheme

import android.graphics.drawable.Drawable
import com.alab.dynamic_theme.DynamicTheme


abstract class MyAppTheme : DynamicTheme() {

    abstract fun actionBarBackgroundDrawable(): Drawable?

    abstract fun topBarBackgroundColor(): Int

    abstract fun textColor(): Int

    abstract fun drawerHeaderBackgroundColor(): Int

    abstract fun drawerHBodyBackgroundColor(): Int

    abstract fun page1Background(): Int

    abstract fun page1Image(): Drawable?
}