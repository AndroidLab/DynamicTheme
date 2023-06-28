package com.alab.dynamictheme

import android.os.Bundle
import android.view.LayoutInflater
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.alab.dynamic_theme.DynamicThemeActivity
import com.alab.dynamictheme.databinding.ActivityMainBinding


class MainActivity : DynamicThemeActivity<MyAppTheme>() {

    companion object {
        /**
         * Возвращает или устанавливает drawer.
         */
        lateinit var drawer: DrawerLayout
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(
                this
            )
        )
    }

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(R.id.page1Fragment, R.id.page2Fragment),
            binding.drawerLayout
        )
    }
    private val navController: NavController by lazy { findNavController(R.id.nav_host) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        drawer = binding.drawerLayout

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.page1tv.setOnClickListener {
            navController.navigate(R.id.page1Fragment)
        }
        binding.page2tv.setOnClickListener {
            navController.navigate(R.id.page2Fragment)
        }
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    override fun syncTheme(appTheme: MyAppTheme) {
        binding.apply {
            window.statusBarColor = appTheme.topBarBackgroundColor()
            supportActionBar?.setBackgroundDrawable(appTheme.actionBarBackgroundDrawable())
            topBar.setBackgroundColor(appTheme.topBarBackgroundColor())
        }
    }

}
