package com.senai.calculador_de_gorjetas

import android.graphics.drawable.Icon
import android.icu.text.NumberFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
                       modifier = Modifier
                           .padding(innerPadding)
                           .fillMaxSize()
                   )
                }
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun CalculadorApp(
    modifier: Modifier = Modifier
    .fillMaxSize()
    .wrapContentSize(Alignment.Center),
){
    var valorContaTexto by remember { mutableStateOf("") }
    var porcentagemGorjeta by remember { mutableStateOf("") }
    var estadoBotao by remember { mutableStateOf(false) }

    val gorjeta = calcularGorjeta(
        valorContaTexto.toDoubleOrNull() ?: 0.0,
        porcentagemGorjeta.toDoubleOrNull() ?: 15.0,
        estadoBotao
    )

    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            stringResource(R.string.calcular_gorjeta),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))

        CampoNumericoEditavel(
            modifier.fillMaxWidth(),
            value = valorContaTexto,
            onValueChange = {valorContaTexto = it},
            label = stringResource(R.string.valor_conta),
            leadingIcon = R.drawable.baseline_attach_money_24
        )
        CampoNumericoEditavel(
            modifier.fillMaxWidth(),
            value = porcentagemGorjeta,
            onValueChange = {porcentagemGorjeta = it},
            label = stringResource(R.string.porcentagem_da_gorjeta),
            leadingIcon = R.drawable.baseline_percent_24
        )
        ChaveArrendondarGorjeta(
            checked = estadoBotao,
            onCheckedChange = {estadoBotao = it}
        )

        Spacer(modifier = Modifier.height(25.dp))
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
fun CampoNumericoEditavel(
    modifier: Modifier = Modifier,
    value: String, onValueChange: (String) -> Unit,
    label: String = "",
    leadingIcon: Int = 0,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    ){

    TextField(value = value,
        onValueChange = onValueChange,
        label = {Text(label)},
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        singleLine = true,
        leadingIcon = { Icon(painterResource(id = leadingIcon), "")},
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun ChaveArrendondarGorjeta(
    modifier: Modifier = Modifier,
    checked: (Boolean) = false,
    onCheckedChange: (Boolean) -> Unit = {}
){
    Row (
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            stringResource(R.string.arrendondar_gorjeta)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

private fun calcularGorjeta(quantia: Double, porcentagemGorjeta: Double, estadoBotao: Boolean): String {
    var gorjeta = porcentagemGorjeta / 100 * quantia
    if (estadoBotao){
        gorjeta = kotlin.math.ceil(gorjeta)
    }
    return NumberFormat.getCurrencyInstance().format(gorjeta)
}