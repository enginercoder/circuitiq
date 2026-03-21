package com.circuitiq.app.ui

import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.circuitiq.app.R
import com.circuitiq.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding

    override fun onCreate(s: Bundle?) {
        val prefs = getSharedPreferences("circuitiq_prefs", Context.MODE_PRIVATE)
        val isDark = prefs.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        super.onCreate(s)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        val navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        b.bottomNav.setupWithNavController(navHost.navController)
        b.btnDarkMode.setOnClickListener {
            val isDarkNow = prefs.getBoolean("dark_mode", false)
            prefs.edit().putBoolean("dark_mode", !isDarkNow).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (!isDarkNow) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            recreate()
        }
    }

    companion object {
        fun isDarkMode(context: Context) =
            context.getSharedPreferences("circuitiq_prefs", Context.MODE_PRIVATE)
                .getBoolean("dark_mode", false)
    }
}
