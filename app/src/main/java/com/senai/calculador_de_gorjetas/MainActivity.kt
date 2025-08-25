package com.senai.calculador_de_gorjetas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.senai.calculador_de_gorjetas.ui.theme.Calculador_de_GorjetasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculador_de_GorjetasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   CalculadoraPreview()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculadoraPreview() {

}