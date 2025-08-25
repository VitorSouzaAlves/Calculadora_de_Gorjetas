package com.senai.calculador_de_gorjetas

import android.icu.text.NumberFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.senai.calculador_de_gorjetas.ui.theme.Calculador_de_GorjetasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculador_de_GorjetasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   CalculadorApp(
                       modifier = Modifier.padding(innerPadding)
                   )
                }
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun CalculadorApp(modifier: Modifier = Modifier
    .fillMaxSize()
    .wrapContentSize(Alignment.Center),
){
    var valorContaTexto by remember { mutableStateOf("") }
    val valorContaDouble = valorContaTexto.toDoubleOrNull() ?: 0.0
    val gorjeta = calcularGorjeta(valorContaDouble)

    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            stringResource(R.string.calcular_gorjeta),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))

        CampoNumericoEditavel(
            modifier.fillMaxWidth(),
            value = valorContaTexto,
            onValueChange = {valorContaTexto = it}
        )

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            stringResource(R.string.valor_gorjeta, gorjeta),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}
@Composable
fun CampoNumericoEditavel(modifier: Modifier = Modifier, value: String ,onValueChange: (String) -> Unit){

    TextField(value = value,
        onValueChange = onValueChange,
        label = {Text(stringResource(R.string.valor_conta))},
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

private fun calcularGorjeta(quantia: Double, porcentagemGorjeta: Double = 15.0): String {
    val gorjeta = porcentagemGorjeta / 100 * quantia
    return NumberFormat.getCurrencyInstance().format(gorjeta)
}