package com.example.visitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.visitor.ui.components.VisitorScreen
import com.example.visitor.ui.theme.VisitorTheme
import com.example.visitor.viewmodel.VisitorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VisitorTheme {
                val viewModel: VisitorViewModel = viewModel()
                LaunchedEffect(Unit) { viewModel.loadVisitors() }
                VisitorScreen(viewModel)
            }
        }
    }
}