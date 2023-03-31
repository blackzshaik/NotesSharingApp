package com.notessharingapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = Color.BLACK

        val host = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController  = host.navController
        val appBarConfig = AppBarConfiguration(navController.graph)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.materialToolbar)

        toolbar.setupWithNavController(navController,appBarConfig)
        setSupportActionBar(toolbar)

    }

}