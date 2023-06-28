package com.alab.dynamictheme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alab.dynamic_theme.DynamicThemeFragment
import com.alab.dynamictheme.databinding.Page2FragmentBinding


class Page2Fragment : Fragment() {

    private val binding: Page2FragmentBinding by lazy {
        DataBindingUtil.inflate(
            layoutInflater,
            R.layout.page_2_fragment,
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
        binding.lifecycleOwner = viewLifecycleOwner
    }

}