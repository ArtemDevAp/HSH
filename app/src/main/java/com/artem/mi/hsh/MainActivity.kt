package com.artem.mi.hsh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.artem.mi.hsh.features.hospital.HospitalRoute
import com.artem.mi.hsh.features.hospital.HospitalViewModel
import com.artem.mi.hsh.ui.theme.HSHTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HSHTheme {
                HospitalRoute(viewModel(factory = HospitalViewModel.Factory))
            }
        }
    }
}