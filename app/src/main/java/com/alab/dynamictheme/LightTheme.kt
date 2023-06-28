package com.alab.dynamictheme

import android.graphics.drawable.Drawable
import com.alab.dynamic_theme.DynamicThemeType


class LightTheme : MyAppTheme() {

    override val type = DynamicThemeType.LIGHT

    override fun actionBarBackgroundDrawable(): Drawable? = getDrawable(R.color.purple_500)

    override fun topBarBackgroundColor() = getColor(R.color.purple_500)

    override fun textColor(): Int = getColor(R.color.purple_700)

    override fun drawerHeaderBackgroundColor(): Int = getColor(R.color.purple_500)

    override fun drawerHBodyBackgroundColor(): Int = getColor(R.color.white)

    override fun page1Background(): Int = getColor(R.color.white)

    override fun page1Image(): Drawable? = getDrawable(R.drawable.day)
}