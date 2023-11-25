package com.artem.mi.hsh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.artem.mi.hsh.ui.navigation.HshNavigation
import com.artem.mi.hsh.ui.theme.HSHTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HSHTheme {
                HshNavigation()
            }
        }
    }
}