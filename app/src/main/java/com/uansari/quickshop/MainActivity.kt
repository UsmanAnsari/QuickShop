package com.uansari.quickshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.uansari.quickshop.presentation.ShoppingScreen
import com.uansari.quickshop.presentation.theme.QuickShopTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickShopTheme {
                ShoppingScreen()
            }
        }
    }
}
