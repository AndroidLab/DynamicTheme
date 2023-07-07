package com.alab.dynamictheme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.alab.dynamic_theme.DynamicThemeFragment
import com.alab.dynamictheme.databinding.DrawerFragmentBinding


class DrawerFragment : DynamicThemeFragment<MyAppTheme>() {

    private val binding: DrawerFragmentBinding by lazy {
        DataBindingUtil.inflate(
            layoutInflater,
            R.layout.drawer_fragment,
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
        binding.drawerRecycler.adapter = DrawerAdapter(viewLifecycleOwner)
    }

    override fun syncTheme(appTheme: MyAppTheme) {
        binding.apply {
            drawerHeader.setBackgroundColor(appTheme.drawerHeaderBackgroundColor())
            drawerBody.setBackgroundColor(appTheme.drawerHBodyBackgroundColor())
        }
    }

}