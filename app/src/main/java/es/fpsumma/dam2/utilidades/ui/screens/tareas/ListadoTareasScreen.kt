package es.fpsumma.dam2.utilidades.ui.screens.tareas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.fpsumma.dam2.utilidades.model.Asignatura
import es.fpsumma.dam2.utilidades.ui.viewmodel.TareasViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoTareasScreen(navController: NavController, vm: TareasViewModel) {

    val asignaturasList by vm.asignaturas.collectAsState(initial = emptyList())


    fun handleDeleteAsignatura(asignatura: Asignatura) {
        vm.deleteAsignatura(asignatura)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ){innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {}


            HorizontalDivider(Modifier.padding(vertical = 8.dp))

            Text("Listado de Notas:", style = MaterialTheme.typography.titleMedium)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = asignaturasList,
                    key = { it.id }
                ) { asignatura ->
                    ListItem(
                        modifier = Modifier.fillMaxWidth(),
                        headlineContent = { Text(asignatura.asignatura) },
                        supportingContent = { Text(asignatura.trimestre) },
                        trailingContent = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${asignatura.nota}",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                IconButton(
                                    onClick = { handleDeleteAsignatura(asignatura) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "Borrar nota",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
}