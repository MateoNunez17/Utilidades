package es.fpsumma.dam2.utilidades.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.fpsumma.dam2.utilidades.model.Asignatura
import es.fpsumma.dam2.utilidades.ui.navigation.Routes
import es.fpsumma.dam2.utilidades.ui.viewmodel.TareasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, vm: TareasViewModel) {

    var nombreAsignatura by rememberSaveable { mutableStateOf("") }
    var notaText by rememberSaveable { mutableStateOf("") }
    var selectedTrimestre by rememberSaveable { mutableStateOf("1º Trimestre") }

    val trimestres = listOf("1º Trimestre", "2º Trimestre", "3º Trimestre")

    fun handleAddAsignatura() {
        val nota = notaText.toIntOrNull()
        if (nombreAsignatura.isNotBlank() && nota != null && nota in 0..10) {
            vm.addAsignatura(nombreAsignatura, nota, selectedTrimestre)
            nombreAsignatura = ""
            notaText = ""
            selectedTrimestre = "1º Trimestre"
        }
    }

    fun handleDeleteAsignatura(asignatura: Asignatura) {
        vm.deleteAsignatura(asignatura)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Notas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nombreAsignatura,
                onValueChange = { nombreAsignatura = it },
                label = { Text("Asignatura") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                trimestres.forEach { trimestre ->
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clickable { selectedTrimestre = trimestre },
                        shape = RoundedCornerShape(8.dp),
                        border = if (selectedTrimestre == trimestre) {
                            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                        } else {
                            null
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedTrimestre == trimestre)
                                MaterialTheme.colorScheme.primaryContainer
                            else
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = trimestre.split(" ")[0])
                        }
                    }
                }
            }

            OutlinedTextField(
                value = notaText,
                onValueChange = {
                    val filteredText = it.filter { char -> char.isDigit() }
                    if (filteredText.length <= 2) {
                        notaText = filteredText
                    }
                },
                label = { Text("Nota (0-10)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = ::handleAddAsignatura,
                enabled = nombreAsignatura.isNotBlank() &&
                        notaText.toIntOrNull() in 0..10,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Guardar Nota") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { navController.navigate(Routes.LISTADO_TAREAS) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestión de Notas")
        }
    }
}
    }
}