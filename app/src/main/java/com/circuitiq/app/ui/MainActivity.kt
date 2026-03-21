package com.circuitiq.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.circuitiq.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding
    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        val navHost = supportFragmentManager.findFragmentById(com.circuitiq.app.R.id.navHost) as NavHostFragment
        b.bottomNav.setupWithNavController(navHost.navController)
    }
}
