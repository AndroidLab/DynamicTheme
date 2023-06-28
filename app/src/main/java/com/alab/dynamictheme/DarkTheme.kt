package com.alab.dynamictheme

import android.graphics.drawable.Drawable
import com.alab.dynamic_theme.DynamicThemeType


class DarkTheme : MyAppTheme() {

    override val type = DynamicThemeType.DARK

    override fun actionBarBackgroundDrawable(): Drawable? = getDrawable(R.color.purple_700)

    override fun topBarBackgroundColor(): Int = getColor(R.color.purple_700)

    override fun textColor(): Int = getColor(R.color.white)

    override fun drawerHeaderBackgroundColor(): Int = getColor(R.color.purple_700)

    override fun drawerHBodyBackgroundColor(): Int = getColor(R.color.purple_500)

    override fun page1Background(): Int = getColor(R.color.purple_500)

    override fun page1Image(): Drawable? = getDrawable(R.drawable.night)
}