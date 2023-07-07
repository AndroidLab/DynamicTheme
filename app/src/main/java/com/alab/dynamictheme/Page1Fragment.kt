package com.alab.dynamictheme

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.alab.dynamic_theme.DynamicThemeFragment
import com.alab.dynamic_theme.DynamicThemeManager
import com.alab.dynamic_theme.DynamicThemeType
import com.alab.dynamictheme.databinding.Page1FragmentBinding


class Page1Fragment : DynamicThemeFragment<MyAppTheme>() {

    private val binding: Page1FragmentBinding by lazy {
        DataBindingUtil.inflate(
            layoutInflater,
            R.layout.page_1_fragment,
            null,
            false
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun syncTheme(appTheme: MyAppTheme) {
        binding.apply {
            page1.setBackgroundColor(appTheme.page1Background())
            page1Text.setTextColor(appTheme.textColor())
            page1Image.setImageDrawable(appTheme.page1Image())
        }
    }

}